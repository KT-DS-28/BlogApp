package com.ktdsuniv.blogapp.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Comment {
    private final User author;
    private String content;
    private final List<User> likeUsers = new ArrayList<>();
    private final LocalDateTime addTime = LocalDateTime.now();

    public Comment(User author, String content) {
        this.author = author;
        this.content = content;
    }

    public User getAuthor() {
        return this.author;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<User> getLikeUsers() {
        return this.likeUsers;
    }

    public LocalDateTime getAddTime() {
        return this.addTime;
    }

}
