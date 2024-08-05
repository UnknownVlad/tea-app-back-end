package org.example.teaappbackend.gateway.confirmation;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerCodesSender {
    private final ConfirmationProcessor confirmationProcessor;

    @Async("asyncExecutor")
    @Scheduled(fixedRate = 1000)
    public void sendCodes() {
        confirmationProcessor.process();
    }
}
