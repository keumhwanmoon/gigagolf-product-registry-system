package kr.co.xfilegolf.product;

import kr.co.xfilegolf.user.User;
import lombok.Data;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author jason, Moon
 * @since 2016. 9. 29.
 */
@Entity
@Table(name = "PRODUCT")
@Data
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_CODE")
    private String code;

    @Column(name = "PRODUCT_NAME")
    private String name;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

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
