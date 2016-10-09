package kr.co.xfilegolf.sale;

import kr.co.xfilegolf.user.User;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author jason, Moon
 * @since 2016-10-09
 */
@Entity
@Table(name = "SALE")
@Data
public class Sale {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;

    @Column(name = "SALES_ON")
    private LocalDate salesOn;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_ON")
    private LocalDateTime lastModifiedOn;

    @PostPersist
    public void postPersist() {

        this.createdOn = LocalDateTime.now();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        this.createdBy = user.getUsername();
    }

    @PostUpdate
    public void postUpdate() {

        this.lastModifiedOn = LocalDateTime.now();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        this.lastModifiedBy = user.getUsername();
    }
}
