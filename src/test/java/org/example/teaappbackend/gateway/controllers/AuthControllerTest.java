package org.example.teaappbackend.gateway.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import org.example.teaappbackend.exceptions.dtos.ComplexExceptionResponseDto;
import org.example.teaappbackend.gateway.users.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Map;

import static org.example.teaappbackend.gateway.controllers.RestTestConstants.BAD_REQUEST_SING_UP_BODY;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @SneakyThrows
    @Test
    void signUp(){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        ResponseEntity<?> responseEntity = restTemplate
                .exchange(
                        "/auth/sing-up",
                        HttpMethod.POST,
                        new HttpEntity<>(
                                UserDto.builder()
                                        .email("fedor.mor")
                                        .password("bad")
                                        .build(),
                                httpHeaders

                        ),
                        new ParameterizedTypeReference<>() {}
                );

        Assertions.assertEquals(
                HttpStatus.BAD_REQUEST, responseEntity.getStatusCode()
        );
        /*Assertions.assertEquals(
                BAD_REQUEST_SING_UP_BODY,
                objectMapper.writeValueAsString(responseEntity.getBody())
        );*/
    }

}