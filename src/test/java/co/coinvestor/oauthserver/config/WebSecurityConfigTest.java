package co.coinvestor.oauthserver.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class WebSecurityConfigTest {

    @Test
    void bCryptPasswordEncoderTest(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        boolean pass = bCryptPasswordEncoder.matches("pass", "$2a$10$mBSRw0kSY36P2Ts1Zq0EZOBxKow4lmfQGyEbH7t1BLvyyPCOly/Le");

        System.out.println("pass = " + pass);

        boolean pass2 = bCryptPasswordEncoder.matches("eoghslarj1!A", "$2a$10$MXepCXX4LDt.WoFABqfzq.LbSBzN1YXJ0eg1uVc7SQEVHQAqADSJi");

        System.out.println("pass2 = " + pass2);
    }

}