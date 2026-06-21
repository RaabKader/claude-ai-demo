package com.example.claudeaidemo.dto;

/**
 * Free-form search criteria for persons. Every field is optional; a {@code null}
 * or blank value means "do not filter on this field". All text fields are matched
 * case-insensitively using a {@code contains} (substring) strategy.
 */
public record PersonSearchCriteria(
        String firstName,
        String lastName,
        String email,
        String phone,
        String street,
        String city,
        String zipCode,
        String country,
        String functionLabel
) {}