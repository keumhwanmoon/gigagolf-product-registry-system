package kr.co.xfilegolf.user;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author jason, Moon
 * @since 2016-10-04
 */
@Embeddable
public class Role implements GrantedAuthority {

    @Column(name = "LOGIN_ID", nullable = false)
    private String loginId;

    @Column
    private String role;

    @Override
    public String getAuthority() {
        return this.role;
    }
}







