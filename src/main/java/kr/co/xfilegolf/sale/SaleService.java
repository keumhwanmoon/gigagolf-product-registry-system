package kr.co.xfilegolf.sale;

import kr.co.xfilegolf.SecurityUtils;
import kr.co.xfilegolf.user.UserRepository;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author jason, Moon
 * @since 2016-10-09
 */
@Service
public class SaleService {

    private final SaleRepository saleRepository;

    private final UserRepository userRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository, UserRepository userRepository) {

        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
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

            sale.setLastModifiedBy(SecurityUtils.currentUserLoginId());
        }

        mapProduct(saleForm, sale);

        saleRepository.save(sale);
    }

    private void mapProduct(SaleForm saleForm, Sale sale) {

        sale.setProductId(saleForm.getProductId());
        sale.setProductCode(saleForm.getProductCode());
        sale.setSerialNumber(saleForm.getSerialNumber());

        LocalDate salesOn = LocalDate.parse(saleForm.getSalesOn());

        sale.setSalesOn(salesOn);
    }

    public void remove(Long id) {

        saleRepository.delete(id);
    }

    public List<Sale> findByLoginId(String username) {
        return saleRepository.findByCreatedBy(username);
    }

    public List<Sale> findByProductId(Long id) {
        return saleRepository.findByProductId(id);
    }

    public List<SaleDTO> mapToDto(List<Sale> sales) {

        return sales.stream().map(
                sale -> {
                    SaleDTO saleDTO = new SaleDTO();

                    String agencyName = userRepository.findByLoginId(sale.getCreatedBy()).getAgencyName();

                    saleDTO.setId(sale.getId());
                    saleDTO.setAgencyName(StringUtils.isBlank(agencyName) ? "개인" : agencyName);

                    String serialNumber = sale.getProductCode().equals("NONE") ? sale.getSerialNumber() : sale.getProductCode() + sale.getSerialNumber();

                    saleDTO.setSerialNumber(serialNumber);
                    saleDTO.setSalesOn(sale.getSalesOn());
                    saleDTO.setCreatedBy(sale.getCreatedBy());
                    saleDTO.setCreatedOn(sale.getCreatedOn());
                    saleDTO.setLastModifiedBy(sale.getLastModifiedBy());
                    saleDTO.setLastModifiedOn(sale.getLastModifiedOn());

                    return saleDTO;
                }
        ).collect(Collectors.toList());
    }

    public boolean isExists(Long id, String productCode, String serialNumber) {
        return Optional.ofNullable(saleRepository.findByIdNotAndProductCodeAndSerialNumber(id, productCode, serialNumber)).isPresent();
    }

    public boolean isExists(String productCode, String serialNumber) {

        return Optional.ofNullable(saleRepository.findByProductCodeAndSerialNumber(productCode, serialNumber)).isPresent();
    }
}
