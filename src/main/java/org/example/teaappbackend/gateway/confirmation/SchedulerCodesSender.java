package org.example.teaappbackend.gateway.confirmation;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerCodesSender {
    private final ConfirmationProcessor confirmationProcessor;

    @SneakyThrows
    @Async("asyncExecutor")
    @Scheduled(fixedRate = 100)
    public void sendCodes() {
        confirmationProcessor.process();
    }
}
