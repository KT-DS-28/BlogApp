package com.ktdsuniv.blogapp.service;

import java.util.ArrayList;
import java.util.List;

import com.ktdsuniv.blogapp.domain.User;

/**
 * 사용자 기능 구현. 구현 방법은 docs/IMPLEMENTATION_GUIDE.md 참고.
 *
 * 메뉴 기능은 입력 -> 처리 -> 출력을 모두 담당한다.
 * 자기 담당 메서드 안에서만 작업하고 다른 메서드의 위치나 순서를 바꾸지 말 것. (Git 충돌 방지)
 */
public class UserServiceImpl implements UserService {

	/** 전체 사용자 저장소. 모든 데이터는 여기에서 시작한다. */
	private static final List<User> USERS = new ArrayList<>();

	// ================= 메뉴 기능 =================

	@Override
	public void register() {
		// TODO 이한결
	}

	@Override
	public void login() {
		// TODO 최미서
	}

	@Override
	public void logout() {
		// TODO 최미서
	}

	@Override
	public void withdraw() {
		// TODO 최형선
	}

	// ============ 다른 기능에서 사용하는 조회 ============

	@Override
	public User findById(String id) {
		// TODO 이한결
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		return USERS;
	}

}
