package org.example.teaappbackend;

import org.example.teaappbackend.gateway.configuration.GatewayCodeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {
        GatewayCodeProperties.class
})
public class TeaAppBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeaAppBackEndApplication.class, args);
    }

}
