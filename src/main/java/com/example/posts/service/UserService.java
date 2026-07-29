package com.example.posts.service;

import com.example.posts.dto.*;
import com.example.posts.exception.UserNotFoundException;
import com.example.posts.model.User;
import com.example.posts.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());

        User savedUser = userRepository.save(user);

        return toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return toResponse(user);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
