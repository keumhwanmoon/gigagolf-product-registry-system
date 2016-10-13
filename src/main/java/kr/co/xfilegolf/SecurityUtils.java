package kr.co.xfilegolf;

import kr.co.xfilegolf.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * @author jason, Moon
 * @since 2016-10-10
 */
public class SecurityUtils {

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static User currentUser() {
        return (User) getAuthentication().getPrincipal();
    }

    public static boolean hasAdminRole() {

        Collection<? extends GrantedAuthority> authorities = getAuthentication().getAuthorities();

        return authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent();
    }

    public static String currentUserLoginId() {

        return currentUser().getLoginId();
    }

    public static String currentPersonInCharge() {

        return currentUser().getPersonInCharge();
    }
}
