package org.example.teaappbackend.gateway.repositories;

import org.example.teaappbackend.gateway.users.Role;
import org.example.teaappbackend.gateway.users.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private static final User user = User.builder()
            .username("email@mail.ru")
            .email("email@mail.ru")
            .password("qvsgadbsabs")
            .roles(Set.of(Role.ROLE_USER))
            .build();
    @Test
    void insert(){
        User saved =  userRepository.save(user);
        System.out.println(saved);
    }

}