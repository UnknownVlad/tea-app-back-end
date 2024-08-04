package org.example.teaappbackend.gateway.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.constants")
public record GatewayCodeProperties(
        Long ttlAuthCodeInSec
) {
}
