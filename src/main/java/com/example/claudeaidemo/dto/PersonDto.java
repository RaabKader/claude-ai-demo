package com.example.claudeaidemo.dto;

public record PersonDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        AddressDto address,
        JobFunctionDto function
) {}
