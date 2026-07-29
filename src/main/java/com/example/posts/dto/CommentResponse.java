package com.example.posts.dto;

import java.time.Instant;

public record CommentResponse(
        Long id,
        Long postId,
        Long userId,
        String content,
        Instant createdAt
) {}