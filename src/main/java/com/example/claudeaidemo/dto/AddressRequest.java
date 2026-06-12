package com.example.claudeaidemo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequest(
        @NotBlank(message = "Street is required") @Size(max = 200) String street,
        @NotBlank(message = "City is required")   @Size(max = 100) String city,
        @NotBlank(message = "Zip code is required") @Size(max = 20) String zipCode,
        @NotBlank(message = "Country is required") @Size(max = 100) String country
) {}
