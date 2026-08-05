package com.ktdsuniv.blogapp.service;

import java.util.List;

import com.ktdsuniv.blogapp.domain.Post;
import com.ktdsuniv.blogapp.domain.User;

/**
 * 게시글 기능 계약. 구현은 PostServiceImpl 에서 한다.
 *
 * 이 파일은 팀장이 관리한다. 시그니처 변경이 필요하면 팀장에게 요청할 것.
 */
public interface PostService {

	// ================= 메뉴 기능 =================

	/** 11번 메뉴 — 게시글 등록 (담당: 류지훈) */
	void write();

	/** 12번 메뉴 — 게시글 목록 (담당: 김경환) */
	void showPostList();

	/** 13번 메뉴 — 게시글 상세 조회 (담당: 엄예진) */
	void showPostDetail();

	/** 14번 메뉴 — 게시글 발행 (담당: 이한결) */
	void publish();

	/** 15번 메뉴 — 게시글 좋아요 (담당: 최형선) */
	void like();

	// ============ 다른 기능에서 사용하는 조회 ============

	/**
	 * 게시글 목록 조회.
	 * 로그인 사용자가 blogOwner 면 미발행 글까지, 아니면 발행된 글만 반환한다.
	 */
	List<Post> getPostList(User blogOwner);

	/**
	 * 게시글 상세 조회. index 는 getPostList 로 받은 목록의 순번이다.
	 * 처음 조회하는 사용자면 조회 목록에 추가한다.
	 */
	Post getPost(User blogOwner, int index);

}
