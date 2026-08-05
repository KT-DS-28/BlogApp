package com.ktdsuniv.blogapp.service;

/**
 * 댓글 기능 구현. 구현 방법은 docs/IMPLEMENTATION_GUIDE.md 참고.
 *
 * 이 파일은 4명이 나눠 쓴다. 자기 담당 메서드 안에서만 작업하고
 * 다른 메서드의 위치나 순서를 바꾸지 말 것. (Git 충돌 방지)
 */
public class CommentServiceImpl implements CommentService {

	private final UserService userService = new UserServiceImpl();
	private final PostService postService = new PostServiceImpl();

	@Override
	public void addComment() {
		// TODO 최미서
	}

	@Override
	public void updateComment() {
		// TODO 류지훈
	}

	@Override
	public void deleteComment() {
		// TODO 김경환
	}

	@Override
	public void likeComment() {
		// TODO 엄예진
	}

}
