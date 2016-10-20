package kr.co.xfilegolf;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author jason, Moon
 * @since 2016-10-05
 */
public class UserPasswordEncoderTest {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ShaPasswordEncoder shaPasswordEncoder;
    @Before
    public void setUp() throws Exception {

        bCryptPasswordEncoder = new BCryptPasswordEncoder();

        shaPasswordEncoder = new ShaPasswordEncoder(512);

        shaPasswordEncoder.setEncodeHashAsBase64(false);
    }

    @Test
    public void passWordHashTest() throws Exception {

        String password = "pwd";

        String passwordHash = bCryptPasswordEncoder.encode(password);

        System.out.println("passwordHash = " + passwordHash);
    }

    @Test
    public void shaPasswordEncoder() throws Exception {

        String hashPassword = "c321cd5d751a06fc04602df2622eef8a6d62344b876a61688f9690309f442ca13f037057c4c00443f8704df1a2c8004248e142aa9ccc5ba1c5fbc13b69b5e533";

        Object salt = new Object();

        boolean isValid = shaPasswordEncoder.isPasswordValid(hashPassword, "admin", salt);

        System.out.println("isValid = " + isValid);
    }

    @Test
    public void shaPasswordEncoderEncode() throws Exception {

        String rawPassword = "admin";

        String hashPassword = shaPasswordEncoder.encodePassword(rawPassword, null);

        System.out.println("hashPassword = " + hashPassword);
    }
}
