package org.example.teaappbackend.gateway.confirmation;

import lombok.RequiredArgsConstructor;
import org.example.teaappbackend.gateway.repositories.AuthCodeRepository;
import org.example.teaappbackend.gateway.users.AuthCode;
import org.example.teaappbackend.mail.sender.MailSender;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfirmationProcessorImpl implements ConfirmationProcessor {
    private final MailSender mailSender;
    private final AuthCodeRepository authCodeRepository;

    @Override
    @Transactional
    public void process() {
        List<AuthCode> authCodes = authCodeRepository.findNotSentCodes(
                PageRequest.of(0, 10)
        );

        for (AuthCode authCode: authCodes){
            mailSender.sendMessage(
                    authCode.getUser().getEmail(),
                    "Код подтверждения",
                    authCode.getCode()
            );
            authCode.setIsSent(true);
        }
        authCodeRepository.saveAll(authCodes);
    }
}
