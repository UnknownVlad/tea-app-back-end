package org.example.teaappbackend.gateway.services.confirmation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teaappbackend.exceptions.CodeNotExistsException;
import org.example.teaappbackend.exceptions.CodeTimeOutException;
import org.example.teaappbackend.gateway.dtos.ConfirmationAccountDto;
import org.example.teaappbackend.gateway.repositories.AuthCodeRepository;
import org.example.teaappbackend.gateway.repositories.UserRepository;
import org.example.teaappbackend.gateway.users.AuthCode;
import org.example.teaappbackend.gateway.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static org.example.teaappbackend.exceptions.constants.ExceptionConstants.CODE_NOT_EXISTS_EXCEPTION_MESSAGE;
import static org.example.teaappbackend.exceptions.constants.ExceptionConstants.CODE_TIME_OUT_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmationService {
    private final UserRepository userRepository;
    private final AuthCodeRepository authCodeRepository;

    @Value("${application.constants.ttl-auth-code-in-sec}")
    private Long ttlCode;

    @Transactional
    public ConfirmationAccountDto confirmAccount(String code) {
        AuthCode authCode = authCodeRepository.findByCode(code).orElseThrow(() -> new CodeNotExistsException(CODE_NOT_EXISTS_EXCEPTION_MESSAGE));

        //todo: мб сравнивать с фактической датой отправки
        if (OffsetDateTime.now().toEpochSecond() - authCode.getSavingTime().toEpochSecond() > ttlCode)
            throw new CodeTimeOutException(CODE_TIME_OUT_EXCEPTION_MESSAGE);

        User user = authCode.getUser();
        user.setIsEnabled(true);
        user.setAuthCode(null);

        userRepository.save(user);
        return ConfirmationAccountDto.builder()
                .isActivated(true)
                .message("Аккаунт успешно подтвержден")
                .build();
    }

}
