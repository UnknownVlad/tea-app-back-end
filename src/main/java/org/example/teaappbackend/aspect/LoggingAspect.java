package org.example.teaappbackend.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.teaappbackend.aspect.ejectors.KeyEjector;
import org.example.teaappbackend.aspect.mappers.ValueMapper;
import org.example.teaappbackend.utils.MapConverterUtils;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LoggedRepository loggedRepository;

    @Pointcut(value = "@annotation(logged)")
    public void loggedMethods(Logged logged) {
    }

    @AfterReturning(pointcut = "loggedMethods(logged)", returning = "result")
    public void logAfter(JoinPoint joinPoint, Logged logged, Object result) {
        System.out.println("CALL METHOD LOG");
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        KeyEjector keyEjector;
        ValueMapper inputArgsMapper;
        ValueMapper outputArgsMapper;
        try {
            keyEjector = logged.keyEjector().getDeclaredConstructor().newInstance();
            inputArgsMapper = logged.inputArgsMapper().getDeclaredConstructor().newInstance();
            outputArgsMapper = logged.outputArgsMapper().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not instantiate KeyEjector", e);
        }

        LoggedEntity loggedEntity = LoggedEntity.builder()
                .methodName(methodName)
                .potentialKey(keyEjector.eject(methodArgs))
                .inputArgs(inputArgsMapper.toMap(methodArgs))
                .outputArgs(outputArgsMapper.toMap(result))
                .build();

        System.out.println("LOGGED: " + loggedEntity);
        loggedRepository.save(loggedEntity);
    }

}
