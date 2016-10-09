package kr.co.xfilegolf.sale;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author jason, Moon
 * @since 2016-10-09
 */
@Data
public class SaleDTO {

    private Long id;
    private String serialNumber;
    private LocalDate salesOn;
    private LocalDateTime createdOn;
    private String createdBy;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedOn;
}
