package com.ktdsuniv.blogapp.domain;

import java.util.ArrayList;
import java.util.List;

public class User {

	private final static int MAX_LOGIN_TRY_COUNT = 10;

	private final String id;
	private String password;
	private String name;
	private String blogName;
	private final List<Neighbor> neighbors = new ArrayList<>();
	private final List<Post> postList = new ArrayList<>();

	private int loginTryCount;

	public User(String id, String password, String name, String blogName) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.blogName = blogName;
	}

	public boolean isLoginBlocked() {
		return this.loginTryCount >= MAX_LOGIN_TRY_COUNT;
	}

	public void increaseLoginTryCount() {
		this.loginTryCount++;
	}

	public void resetLoginTryCount() {
		this.loginTryCount = 0;
	}

	public String getId() {
		return this.id;
	}

	public String getPassword() {
		return this.password;
	}

	public String getName() {
		return this.name;
	}

	public String getBlogName() {
		return this.blogName;
	}

	public List<Neighbor> getNeighbors() {
		return this.neighbors;
	}

	public List<Post> getPostList() {
		return this.postList;
	}

	public int getLoginTryCount() {
		return this.loginTryCount;
	}
	
	public int getMaxLoginTryLimit() {
		return MAX_LOGIN_TRY_COUNT;
	}

	@Override
	public String toString() {
		return "ID: " + this.getId() + ", 이름: " + this.getName() + ", 블로그명: " + this.getBlogName();
	}
}
