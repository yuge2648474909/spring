package com.example.back_end;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.crypto.Data;
import java.util.Date;

@SpringBootTest
class BackEndApplicationTests {

    @Test
    void contextLoads() {
        Date now =  new Date();
        Date expiration = new Date(500);
        System.out.println(now);
        System.out.println(expiration);
    }

}
