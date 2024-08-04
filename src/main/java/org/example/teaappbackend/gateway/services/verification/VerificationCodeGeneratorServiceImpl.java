package org.example.teaappbackend.gateway.services.verification;

import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class VerificationCodeGeneratorServiceImpl implements VerificationCodeGeneratorService{
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
