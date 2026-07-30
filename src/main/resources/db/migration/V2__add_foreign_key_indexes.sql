CREATE INDEX idx_posts_user_id
    ON posts (user_id);

CREATE INDEX idx_comments_post_id_created_at
    ON comments (post_id, created_at);

CREATE INDEX idx_comments_user_id
    ON comments (user_id);

CREATE INDEX idx_post_tags_tag_id
    ON post_tags (tag_id);