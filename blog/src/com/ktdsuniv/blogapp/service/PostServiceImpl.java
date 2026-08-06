package com.ktdsuniv.blogapp.service;

import java.util.ArrayList;
import java.util.List;

import com.ktdsuniv.blogapp.domain.Post;
import com.ktdsuniv.blogapp.domain.User;
import com.ktdsuniv.blogapp.exception.BlogException;
import com.ktdsuniv.blogapp.util.ScannerUtil;
import com.ktdsuniv.blogapp.util.Session;

/**
 * 게시글 기능 구현. 구현 방법은 docs/IMPLEMENTATION_GUIDE.md 참고.
 *
 * 이 파일은 5명이 나눠 쓴다. 자기 담당 메서드 안에서만 작업하고
 * 
 * 다른 메서드의 위치나 순서를 바꾸지 말 것. (Git 충돌 방지)
 */
public class PostServiceImpl implements PostService {

	private final UserService userService;

	public PostServiceImpl() {
		this.userService = new UserServiceImpl();
	}

	// ================= 메뉴 기능 =================

	@Override
	public void write() {
		User author = Session.getLoginUser();
		String title = ScannerUtil.nextLine("제목: ").trim();
		if (title.isBlank()) {
			throw new BlogException("제목은 필수로 입력해야 합니다.");
		}
		String content = ScannerUtil.nextLine("내용: ").trim();
		if (content.isBlank()) {
			throw new BlogException("내용은 필수로 입력해야 합니다.");
		}
		Post post = new Post(author, title, content);
		author.getPostList().add(post);
		System.out.println("게시글이 등록되었습니다.");
	}

	@Override
	public void showPostList() {
		// TODO 김경환
	}

	@Override
	public void showPostDetail() {
		// TODO 엄예진
	}

	@Override
	public void publish() {
		// TODO 이한결
	}

	@Override
	public void like() {
		// TODO 최형선
	}

	// ============ 다른 기능에서 사용하는 조회 ============

	@Override
	public List<Post> getPostList(User blogOwner) {
		// TODO 김경환
		return new ArrayList<>();
	}

	@Override
	public Post getPost(User blogOwner, int index) {
		// TODO 엄예진
		return null;
	}

}
