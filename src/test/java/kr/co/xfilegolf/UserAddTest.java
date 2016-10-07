package kr.co.xfilegolf;

import kr.co.xfilegolf.user.Role;
import kr.co.xfilegolf.user.User;
import kr.co.xfilegolf.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;

import java.util.Arrays;

/**
 * @author jason, Moon
 * @since 2016-10-06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAddTest {

    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder encoder;

    @Before
    public void before() {
        encoder = new BCryptPasswordEncoder();
    }

    @Test
    public void userAddTest() {

        User user = new User();
        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");

        String passwordHash = encoder.encode("pwd");

        user.setId(1L);
        user.setLoginId("admin");
        user.setAgencyName("관리자");
        user.setPassword(passwordHash);
        user.setRoles(Arrays.asList(roleUser, roleAdmin));
        user.setActivation(true);

        userRepository.save(user);
    }
}
