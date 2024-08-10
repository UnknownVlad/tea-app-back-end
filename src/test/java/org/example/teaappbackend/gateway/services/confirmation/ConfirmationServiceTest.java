package org.example.teaappbackend.gateway.services.confirmation;

import org.example.teaappbackend.gateway.dtos.ConfirmationAccountDto;
import org.example.teaappbackend.gateway.dtos.JwtAuthenticationDto;
import org.example.teaappbackend.gateway.dtos.UserDto;
import org.example.teaappbackend.gateway.repositories.AuthCodeRepository;
import org.example.teaappbackend.gateway.repositories.UserRepository;
import org.example.teaappbackend.gateway.services.verification.VerificationCodeGeneratorService;
import org.example.teaappbackend.gateway.users.AuthCode;
import org.example.teaappbackend.gateway.users.User;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class ConfirmationServiceTest {
    @Autowired
    private ConfirmationService confirmationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthCodeRepository authCodeRepository;

    @MockBean
    private VerificationCodeGeneratorService verificationCodeGeneratorService;
    @MockBean
    private MailSender mailSender;

    private static final UserDto REQUEST_VALID_USER = UserDto.builder()
            .email("vlad.fox2002@mail.ru")
            .password("qsagnAsaflbe!asfb!#FA")
            .build();

    private static final String CODE = "418e5d5c-e391-4fb7-80e7-fd33c905a1eb";

    @Test
    void confirmAccountSuccess(){
        when(verificationCodeGeneratorService.generate()).thenReturn(CODE);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<JwtAuthenticationDto> responseSingUp = restTemplate
                .exchange(
                        "/auth/sing-up",
                        HttpMethod.POST,
                        new HttpEntity<>(
                                REQUEST_VALID_USER,
                                httpHeaders
                        ),
                        new ParameterizedTypeReference<>() {}
                );
        Assertions.assertEquals(HttpStatus.OK, responseSingUp.getStatusCode());
        Optional<User> userOptional = userRepository.findByEmail(REQUEST_VALID_USER.getEmail());

        Assertions.assertTrue(userOptional.isPresent());

        User user = userOptional.get();
        Assertions.assertEquals(REQUEST_VALID_USER.getEmail(), user.getEmail());
        Assertions.assertEquals(REQUEST_VALID_USER.getEmail(), user.getUsername());
        //Assertions.assertEquals(passwordEncoder.encode(REQUEST_VALID_USER.getPassword()), user.getPassword());
        Assertions.assertEquals(Boolean.FALSE, user.getIsEnabled());
        Assertions.assertEquals(CODE, user.getAuthCode().getCode());

        ResponseEntity<ConfirmationAccountDto> responseConfirm = restTemplate
                .exchange(
                        "/auth/confirm/%s".formatted(CODE),
                        HttpMethod.POST,
                        new HttpEntity<>(
                                httpHeaders
                        ),
                        new ParameterizedTypeReference<>() {}
                );

        Assertions.assertEquals(HttpStatus.OK, responseConfirm.getStatusCode());

        Optional<User> confirmedUserOptional = userRepository.findByEmail(REQUEST_VALID_USER.getEmail());
        Assertions.assertTrue(confirmedUserOptional.isPresent());
        User confirmedUser = confirmedUserOptional.get();
        Assertions.assertEquals(Boolean.TRUE, confirmedUser.getIsEnabled());
        Assertions.assertNull(confirmedUser.getAuthCode());

    }

}