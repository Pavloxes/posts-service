package com.example.posts.dto;

import jakarta.validation.constraints.Size;

public record ErrorResponse(

        @Size(max = 5000)
        String message
) {}