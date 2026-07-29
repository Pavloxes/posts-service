package com.example.posts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record CreateCommentRequest(

        @NotNull
        @Positive
        Long userId,

        @NotBlank
        @Size(max = 5000)
        String content
) {}