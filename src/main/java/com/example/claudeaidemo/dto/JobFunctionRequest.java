package com.example.claudeaidemo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JobFunctionRequest(
        @NotBlank(message = "Label is required") @Size(max = 100) String label,
        @Size(max = 500) String description
) {}
