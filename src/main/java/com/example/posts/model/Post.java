package com.example.posts.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Audited;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PostType type;

    @Column(name = "content")
    private String content;

    @Column(name = "picture_path")
    private String picturePath;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();



    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public PostType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}