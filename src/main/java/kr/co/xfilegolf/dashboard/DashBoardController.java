package kr.co.xfilegolf.dashboard;

import kr.co.xfilegolf.SecurityUtils;
import kr.co.xfilegolf.sale.Sale;
import kr.co.xfilegolf.sale.SaleDTO;
import kr.co.xfilegolf.sale.SaleRepository;
import kr.co.xfilegolf.sale.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author jason, Moon
 * @since 2016. 9. 29.
 */
@Controller
@RequestMapping
public class DashBoardController {

    private final SaleService saleService;
    private final SaleRepository saleRepository;

    @Autowired
    public DashBoardController(SaleRepository saleRepository, SaleService saleService) {
        this.saleRepository = saleRepository;
        this.saleService = saleService;
    }

    @RequestMapping("/")
    public ModelAndView index() {

        ModelAndView mv = new ModelAndView("index");

        List<Sale> sales;
        long count;
        long effective;
        long expired;

        LocalDate today = LocalDate.now();
        LocalDate yearAgo = today.minusYears(1);

        if (SecurityUtils.hasAdminRole()) {

            sales = saleRepository.findTop10ByOrderByCreatedOnDesc();
            count = saleRepository.count();
            effective = saleRepository.countBySalesOnBetween(yearAgo, today);
            expired = saleRepository.countBySalesOnLessThan(yearAgo);
        } else {

            String username = SecurityUtils.currentUserName();

            sales = saleRepository.findTop10ByCreatedByOrderByCreatedOnDesc(username);
            count = saleRepository.countByCreatedBy(username);
            effective = saleRepository.countByCreatedByAndSalesOnBetween(username, yearAgo, today);
            expired = saleRepository.countByCreatedByAndSalesOnLessThan(username, yearAgo);
        }

        List<SaleDTO> saleDTOList = saleService.mapToDto(sales);

        BigDecimal effectivePercent = BigDecimal.ZERO;
        BigDecimal expiredPercent = BigDecimal.ZERO;

        if (0 != count && 0 != effective) {
            effectivePercent = new BigDecimal(effective).divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }
        if (0 != count && 0 != expired) {
            expiredPercent = new BigDecimal(expired).divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }

        mv.addObject("saleDTOList", saleDTOList);
        mv.addObject("count", count);
        mv.addObject("effective", effective);
        mv.addObject("effectivePercent", effectivePercent);
        mv.addObject("expired", expired);
        mv.addObject("expiredPercent", expiredPercent);

        return mv;
    }
}
