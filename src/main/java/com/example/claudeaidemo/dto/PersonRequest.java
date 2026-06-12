package com.example.claudeaidemo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PersonRequest(
        @NotBlank(message = "First name is required") @Size(max = 100) String firstName,
        @NotBlank(message = "Last name is required")  @Size(max = 100) String lastName,
        @NotBlank(message = "Email is required") @Email(message = "Email must be valid") @Size(max = 255) String email,
        @Size(max = 30) String phone,
        @NotNull(message = "Address ID is required")  Long addressId,
        @NotNull(message = "Function ID is required") Long functionId
) {}
