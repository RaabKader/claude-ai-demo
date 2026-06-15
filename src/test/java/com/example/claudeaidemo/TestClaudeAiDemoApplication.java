package com.example.claudeaidemo;

import org.springframework.boot.SpringApplication;

public class TestClaudeAiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.from(ClaudeAiDemoApplication::main)
                .with(TestcontainersConfiguration.class)
                .withAdditionalProfiles("observability")
                .run(args);
    }

}
