package org.example.teaappbackend;

import org.example.teaappbackend.gateway.configuration.GatewayCodeProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeaAppBackEndApplicationTests {

    @Autowired
    public GatewayCodeProperties get;
    @Test
    void contextLoads() {
        System.out.println(get.ttlAuthCodeInSec());
    }

}
