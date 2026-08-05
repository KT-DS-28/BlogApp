package com.ktdsuniv.blogapp.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
	private final User author;
	private String title;
	private String content;
	private final List<User> likeUsers = new ArrayList<>();
	private final List<User> viewedUsers = new ArrayList<>();
	private final List<Comment> comments = new ArrayList<>();
	private final List<String> tags = new ArrayList<>();
	private LocalDateTime publishTime;

	public Post(User author, String title, String content) {
		this.author = author;
		this.title = title;
		this.content = content;
	}

	public boolean isPublished() {
		return this.publishTime != null;
	}

	public void publish() {
		this.publishTime = LocalDateTime.now();
	}

	public User getAuthor() {
		return this.author;
	}

	public String getTitle() {
		return this.title;
	}

	public String getContent() {
		return this.content;
	}

	public List<User> getLikeUsers() {
		return this.likeUsers;
	}

	public List<User> getViewedUsers() {
		return this.viewedUsers;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public List<String> getTags() {
		return this.tags;
	}

	public LocalDateTime getPublishTime() {
		return this.publishTime;
	}

}
