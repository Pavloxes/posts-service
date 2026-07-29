package com.example.posts.service;

import com.example.posts.dto.CreatePostRequest;
import com.example.posts.dto.PostResponse;
import com.example.posts.dto.UpdatePostRequest;
import com.example.posts.exception.PostNotFoundException;
import com.example.posts.exception.UserNotFoundException;
import com.example.posts.model.Post;
import com.example.posts.model.PostType;
import com.example.posts.model.User;
import com.example.posts.repository.PostRepository;
import com.example.posts.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public PostResponse createPost(
            Long userId,
            CreatePostRequest request
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        validatePost(
                request.type(),
                request.content(),
                request.picturePath()
        );

        Post post = new Post();
        post.setUser(user);
        post.setType(request.type());
        post.setContent(request.content());
        post.setPicturePath(request.picturePath());

        Post savedPost = postRepository.save(post);

        return toResponse(savedPost);
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        return toResponse(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        List<Post> posts = postRepository.findByUserId(userId);

        return posts.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException(id);
        }

        postRepository.deleteById(id);
    }

    @Transactional
    public PostResponse updatePost(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        validatePost(
                request.type(),
                request.content(),
                request.picturePath()
        );

        post.setType(request.type());
        post.setContent(request.content());
        post.setPicturePath(request.picturePath());

        return toResponse(post);
    }

    private PostResponse toResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getUser().getId(),
                post.getType(),
                post.getContent(),
                post.getPicturePath()
        );
    }

    private void validatePost(
            PostType type,
            String content,
            String picturePath
    ) {
        if (type == PostType.TEXT && (content == null || content.isBlank())) {
            throw new IllegalArgumentException("Text post must contain content");
        }

        if (type == PostType.PICTURE && (picturePath == null || picturePath.isBlank())) {
            throw new IllegalArgumentException("Picture post must contain picturePath");
        }

        if (type == PostType.PICTURE_WITH_TEXT
                && (content == null || content.isBlank()
                || picturePath == null || picturePath.isBlank())) {
            throw new IllegalArgumentException("Picture with text post must contain content and picturePath");
        }
    }
}
