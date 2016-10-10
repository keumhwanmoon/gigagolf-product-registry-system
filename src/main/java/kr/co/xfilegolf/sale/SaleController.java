package kr.co.xfilegolf.sale;

import kr.co.xfilegolf.SecurityUtils;
import kr.co.xfilegolf.product.Product;
import kr.co.xfilegolf.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jason, Moon
 * @since 2016-10-09
 */
@Controller
public class SaleController {

    private final SaleService saleService;
    private final ProductService productService;

    @Autowired
    public SaleController(SaleService saleService, ProductService productService) {

        this.saleService = saleService;
        this.productService = productService;
    }

    @GetMapping(value = "/sale")
    public ModelAndView product() {

        ModelAndView mv = new ModelAndView("/sale/sale");

        List<Sale> sales;

        if (SecurityUtils.hasAdminRole()) {

            sales = saleService.findAll();
        } else {

            String username = SecurityUtils.currentUserName();

            sales = saleService.findByLoginId(username);
        }

        List<SaleDTO> saleDTOList = saleService.mapToDto(sales);

        mv.addObject("saleDTOList", saleDTOList);

        return mv;
    }

    @DeleteMapping(value = "/sale")
    public String saleDelete(@RequestParam(name = "id") Long id) {

        saleService.remove(id);

        return "/sale/sale";
    }

    @GetMapping(value = "/sale-register")
    public String saleRegister(@RequestParam(name = "id", required = false, defaultValue = "0") Long id, SaleForm saleForm) {

        if (0L != id) {

            Sale sale = saleService.findOne(id);

            saleForm.setId(sale.getId());
            saleForm.setProductCode(sale.getProductCode());
            saleForm.setSerialNumber(sale.getSerialNumber());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            saleForm.setSalesOn(sale.getSalesOn().format(formatter));
        }

        setProducts(saleForm);

        return "/sale/sale-register";
    }

    @PostMapping(value = "/sale-register")
    public String saleRegister(@Valid SaleForm saleForm, BindingResult result) {

        if (result.hasErrors()) {

            setProducts(saleForm);

            return "/sale/sale-register";
        }

        saleService.save(saleForm);

        return "redirect:/sale";
    }

    private void setProducts(SaleForm saleForm) {

        List<Product> products = productService.findAll();

        saleForm.setProducts(products);
    }
}
