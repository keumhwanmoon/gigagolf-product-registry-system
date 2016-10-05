package kr.co.xfilegolf.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
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

    @Column(name = "ACTIVATION")
    private boolean activation;

    @ElementCollection
    @CollectionTable(name = "ROLE", joinColumns = @JoinColumn(name = "loginId"))
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
        return !this.activation;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
