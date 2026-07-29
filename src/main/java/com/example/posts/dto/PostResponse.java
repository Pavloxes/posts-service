package com.example.posts.dto;

import com.example.posts.model.PostType;

public record PostResponse(
        Long id,
        Long userId,
        PostType type,
        String content,
        String picturePath
) {}


