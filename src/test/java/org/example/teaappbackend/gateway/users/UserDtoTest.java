package org.example.teaappbackend.gateway.users;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class UserDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void validUserTest(){
        UserDto userDto = UserDto.builder()
                .email("fedor.mor@mail.ru")
                .password("soGoodPassword")
                .build();
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void nonValidUserTest(){
        UserDto userDto = UserDto.builder()
                .email("fedor.mor")
                .password("bad")
                .build();
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        System.out.println(violations);
        System.out.println(violations.size());
        //Assertions.assertTrue(violations.isEmpty());
    }

}