package kr.co.xfilegolf.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author jason, Moon
 * @since 2016-09-30
 */
@Service
public class CurrentUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public CurrentUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    public User loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Optional<User> user = userService.findUserByLoginId(loginId);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
        }

        if (!user.get().isActivation()) {
            throw new UsernameNotFoundException("승인되지 않은 사용자입니다.");
        }

        return user.get();
    }
}
