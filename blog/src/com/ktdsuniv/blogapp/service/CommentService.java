package com.ktdsuniv.blogapp.service;

/**
 * 댓글 기능 계약. 구현은 CommentServiceImpl 에서 한다.
 *
 * 이 파일은 팀장이 관리한다. 시그니처 변경이 필요하면 팀장에게 요청할 것.
 */
public interface CommentService {

	/** 21번 메뉴 — 댓글 달기 (담당: 최미서) */
	void addComment();

	/** 22번 메뉴 — 댓글 수정 (담당: 류지훈) */
	void updateComment();

	/** 23번 메뉴 — 댓글 삭제 (담당: 김경환) */
	void deleteComment();

	/** 24번 메뉴 — 댓글 좋아요 (담당: 엄예진) */
	void likeComment();

}
