package org.example.teaappbackend.gateway.controllers;

public class RestTestConstants {

    public static final String BAD_REQUEST_SING_UP_BODY = "{\"error\":{\"code\":\"400\",\"message\":\"Ошибка валидации\",\"additionalInfo\":{\"errors\":[{\"code\":\"Size\",\"message\":\"Длина пароля должна быть не менее 8 символов, и не более 255\"},{\"code\":\"Email\",\"message\":\"Невалидный формать почты\"}]}}}";
}
