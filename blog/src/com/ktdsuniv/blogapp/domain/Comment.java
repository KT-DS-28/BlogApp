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

	@Override
	public String toString() {
		return "작성자: " + this.author + "\t작성 시간: " + this.addTime + "\t좋아요: " + this.getLikeUsers().size() + "개\n내용"
				+ this.content;
	}

}
