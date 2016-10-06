package kr.co.xfilegolf;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author jason, Moon
 * @since 2016-10-05
 */
public class UserPasswordEncoderTest {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void setUp() throws Exception {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void passWordHashTest() throws Exception {

        String password = "pwd";

        String passwordHash = bCryptPasswordEncoder.encode(password);

        System.out.println("passwordHash = " + passwordHash);
    }
}
