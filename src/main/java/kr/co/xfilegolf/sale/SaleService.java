package kr.co.xfilegolf.sale;

import kr.co.xfilegolf.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jason, Moon
 * @since 2016-10-09
 */
@Service
public class SaleService {

    private final SaleRepository saleRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository) {

        this.saleRepository = saleRepository;
    }

    public Sale findOne(Long id) {

        return saleRepository.findOne(id);
    }

    public List<Sale> findAll() {

        return saleRepository.findAll();
    }

    public void save(SaleForm saleForm) {

        Sale sale = null;

        if (null == saleForm.getId()) {

            sale = new Sale();
        } else {

            sale = saleRepository.findOne(saleForm.getId());

            sale.setLastModifiedOn(LocalDateTime.now());

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            sale.setLastModifiedBy(user.getUsername());
        }

        mapProduct(saleForm, sale);

        saleRepository.save(sale);
    }

    private void mapProduct(SaleForm saleForm, Sale sale) {

        sale.setProductCode(saleForm.getProductCode());
        sale.setSerialNumber(saleForm.getSerialNumber());

        LocalDate salesOn = LocalDate.parse(saleForm.getSalesOn());

        sale.setSalesOn(salesOn);
    }

    public void remove(Long id) {

        saleRepository.delete(id);
    }
}
