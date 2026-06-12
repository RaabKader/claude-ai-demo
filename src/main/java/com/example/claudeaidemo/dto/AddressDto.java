package com.example.claudeaidemo.dto;

public record AddressDto(
        Long id,
        String street,
        String city,
        String zipCode,
        String country
) {}
