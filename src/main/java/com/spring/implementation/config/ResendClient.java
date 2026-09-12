package com.spring.implementation.config;

import com.resend.Resend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResendClient {
    @Bean(name = "resendClient-1")
    public Resend resendClient(
            @Value("${resend.api-key}") String apiKey
    ) {
        return new Resend(apiKey);
    }
}
