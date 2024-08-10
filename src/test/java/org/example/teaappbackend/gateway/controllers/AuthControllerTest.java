package org.example.teaappbackend.gateway.controllers;

import lombok.SneakyThrows;
import org.example.teaappbackend.gateway.dtos.UserDto;
import org.example.teaappbackend.mail.sender.MailSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    private MailSender mailSender;
    private static final UserDto REQUEST_VALID_USER = UserDto.builder()
            .email("vlad.fox2002@mail.ru")
            .password("qsagnAsaflbe!asfb!#FA")
            .build();

    @SneakyThrows
    @Test
    void signUpValid(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<?> responseEntity = restTemplate
                .exchange(
                        "/auth/sing-up",
                        HttpMethod.POST,
                        new HttpEntity<>(
                                REQUEST_VALID_USER,
                                httpHeaders

                        ),
                        new ParameterizedTypeReference<>() {}
                );

        Assertions.assertEquals(
                HttpStatus.OK, responseEntity.getStatusCode()
        );
        System.out.println(responseEntity.getBody());
        /*Assertions.assertEquals(
                BAD_REQUEST_SING_UP_BODY,
                objectMapper.writeValueAsString(responseEntity.getBody())
        );*/
    }

}