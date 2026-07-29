package com.example.posts.dto;

import com.example.posts.model.PostType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdatePostRequest(

        @NotNull
        PostType type,

        @Size(max = 5000)
        String content,

        @Size(max = 500)
        String picturePath
) {}
