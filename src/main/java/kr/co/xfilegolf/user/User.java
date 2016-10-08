package kr.co.xfilegolf.user;

import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author jason, Moon
 * @since 2016. 9. 29.
 */
@Entity
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "AGENCY_NAME")
    private String agencyName;

    @Column(name = "PRESIDENT_NAME")
    private String presidentName;

    @Column(name = "AGENCY_ADDRESS")
    private String agencyAddress;

    @Column(name = "BUSINESS_NUMBER")
    private String businessNumber;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "MOBILE_PHONE_NUMBER")
    private String mobilePhoneNumber;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @Column(name = "LAST_MODIFIED_ON")
    private LocalDateTime lastModifiedOn;

    @Column(name = "ACTIVATION")
    private boolean activation;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ROLE", joinColumns = @JoinColumn(name = "LOGIN_ID"))
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
