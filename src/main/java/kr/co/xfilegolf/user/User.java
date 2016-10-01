package kr.co.xfilegolf.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author jason, Moon
 * @since 2016. 9. 29.
 */
@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "AGENCY_NAME")
    private String agencyName;

    @Column(name = "STATUS_CODE")
    private String statusCode;
}
