package kr.co.xfilegolf.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author jason, Moon
 * @since 2016-10-04
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Column(name = "ROLE", nullable = false)
    private String role;

    @Override
    public String getAuthority() {
        return this.role;
    }
}







