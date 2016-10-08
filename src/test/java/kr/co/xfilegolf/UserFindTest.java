package kr.co.xfilegolf;

import kr.co.xfilegolf.user.User;
import kr.co.xfilegolf.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @author jason, Moon
 * @since 2016-10-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserFindTest {

    @Autowired
    private UserService userService;

    @Test
    public void userFind() throws Exception {

        Optional<User> user = userService.findUserByLoginId("admin");

        if (user.isPresent()) {
            System.out.println("user = " + user.get());
        } else {
            System.out.println("user not found!");
        }

    }
}
