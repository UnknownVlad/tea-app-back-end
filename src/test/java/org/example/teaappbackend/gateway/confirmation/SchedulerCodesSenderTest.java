package org.example.teaappbackend.gateway.confirmation;

import org.example.teaappbackend.gateway.repositories.AuthCodeRepository;
import org.example.teaappbackend.gateway.repositories.UserRepository;
import org.example.teaappbackend.gateway.users.AuthCode;
import org.example.teaappbackend.gateway.users.Role;
import org.example.teaappbackend.gateway.users.User;
import org.example.teaappbackend.mail.sender.MailSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SchedulerCodesSenderTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthCodeRepository authCodeRepository;
    @MockBean
    private MailSender mailSender;

    @Test
    void sendCodes() {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            User user = User.builder()
                    .isEnabled(true)
                    .email("user%d@mail.ru".formatted(i))
                    .roles(Set.of(Role.ROLE_USER))
                    .password("asfasfasafsasf")
                    .username("user%d@mail.ru".formatted(i))
                    .build();
            AuthCode authCode =
                    AuthCode.builder()
                            .isSent(false)
                            .code(UUID.randomUUID().toString())
                            .user(user)
                            .build();
            user.setAuthCode(authCode);
            users.add(user);
        }

        userRepository.saveAll(users);

        await().atMost(10, TimeUnit.SECONDS)
                .untilAsserted(
                        () -> {
                            int count = authCodeRepository.countAllByIsSentFalse();
                            System.out.println("++++++++ "+count);
                            Assertions.assertEquals(0, count);


                        }
                );
    }
}