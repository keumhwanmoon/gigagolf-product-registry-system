package kr.co.xfilegolf.config;

import kr.co.xfilegolf.user.LoginUser;
import kr.co.xfilegolf.user.User;
import kr.co.xfilegolf.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author jason, Moon
 * @since 2016-09-30
 */
@Service
class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public LoginUser loadUserByUsername(String loginId) throws UsernameNotFoundException {

        User user = userRepository.findByLoginId(loginId);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
        }

        if (Objects.equals(user.getLoginId(), loginId)) {
            throw new UsernameNotFoundException("승인되지 않은 사용자입니다.");
        }

        LoginUser loginUser = new LoginUser();

        loginUser.setAgencyName(user.getAgencyName());

        return loginUser;
    }
}
