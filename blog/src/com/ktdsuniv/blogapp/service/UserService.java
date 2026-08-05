package com.ktdsuniv.blogapp.service;

import java.util.List;

import com.ktdsuniv.blogapp.domain.User;

/**
 * 사용자 기능 계약. 구현은 UserServiceImpl 에서 한다.
 *
 * 이 파일은 팀장이 관리한다. 시그니처 변경이 필요하면 팀장에게 요청할 것.
 */
public interface UserService {

	// ================= 메뉴 기능 =================

	/** 1번 메뉴 — 사용자 등록 (담당: 이한결) */
	void register();

	/** 2번 메뉴 — 로그인 (담당: 최미서) */
	void login();

	/** 3번 메뉴 — 로그아웃 (담당: 최미서) */
	void logout();

	/** 4번 메뉴 — 회원 탈퇴 (담당: 최형선) */
	void withdraw();

	// ============ 다른 기능에서 사용하는 조회 ============

	/** 아이디로 사용자 조회. 없으면 NotFoundException */
	User findById(String id);

	/** 전체 사용자 목록 */
	List<User> getAllUsers();

}
