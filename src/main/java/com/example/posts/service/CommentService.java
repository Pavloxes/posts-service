package com.example.posts.service;

import com.example.posts.dto.CommentResponse;
import com.example.posts.dto.CreateCommentRequest;
import com.example.posts.exception.PostNotFoundException;
import com.example.posts.exception.UserNotFoundException;
import com.example.posts.model.Comment;
import com.example.posts.model.Post;
import com.example.posts.model.User;
import com.example.posts.repository.CommentRepository;
import com.example.posts.repository.PostRepository;
import com.example.posts.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(
            CommentRepository commentRepository,
            PostRepository postRepository,
            UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CommentResponse createComment(
            Long postId,
            CreateCommentRequest request
    ) {
        Comment comment = new Comment();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException(request.userId()));

        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(request.content());

        Comment savedComment = commentRepository.save(comment);

        return toResponse(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByPostId(Long postId){
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException(postId);
        }

        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId);

        return comments.stream()
                .map(this::toResponse)
                .toList();
    }

    private CommentResponse toResponse(Comment comment) {

        return new CommentResponse(
                comment.getId(),
                comment.getPost().getId(),
                comment.getUser().getId(),
                comment.getContent(),
                comment.getCreatedAt()
                );
    }
}
