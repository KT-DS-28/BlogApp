package com.ktdsuniv.blogapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktdsuniv.blogapp.domain.User;
import com.ktdsuniv.blogapp.exception.BlogException;
import com.ktdsuniv.blogapp.exception.DuplicateUserIdException;
import com.ktdsuniv.blogapp.util.ScannerUtil;

/**
 * 사용자 기능 구현. 구현 방법은 docs/IMPLEMENTATION_GUIDE.md 참고.
 *
 * 메뉴 기능은 입력 -> 처리 -> 출력을 모두 담당한다. 자기 담당 메서드 안에서만 작업하고 다른 메서드의 위치나 순서를 바꾸지 말 것.
 * (Git 충돌 방지)
 */
public class UserServiceImpl implements UserService, NeighborService {

	/** 전체 사용자 저장소. 모든 데이터는 여기에서 시작한다. */
	private List<User> users;
	private Map<User, List<User>> neighbors;

	public UserServiceImpl() {
		this.users = new ArrayList<>();
		this.neighbors = new HashMap<>();
	}
	// ================= 메뉴 기능 =================

	@Override
	public void register() {
		// TODO 이한결
		String id = ScannerUtil.nextLine("아이디:").trim();
		if (id == null || id.isBlank()) {
			throw new BlogException("아이디는 필수로 입력해야합니다");
		}
		if (findById(id) != null) {
			throw new DuplicateUserIdException();
		}

		String password = ScannerUtil.nextLine("password:").trim();
		if (password == null || password.isBlank()) {
			throw new BlogException("비밀번호는 필수로 입력해야 합니다.");
		}

		String name = ScannerUtil.nextLine("이름:").trim();
		if (name == null || name.isBlank()) {
			throw new BlogException("이름은 필수로 입력해야 합니다.");
		}

		String blogName = ScannerUtil.nextLine("블로그 제목:").trim();
		if (blogName == null || blogName.isBlank()) {
			throw new BlogException("블로그 명은 필수로 입력해야 합니다.");
		}
		User newUser = new User(id, password, name, blogName);
		users.add(newUser);
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
		for (User user : users) {
			if (user.getId().equals(id)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		return this.users;
	}

	@Override
	public void request() {
		// TODO 김경환
	}

	@Override
	public void handleRequest() {
		// TODO 엄예진
	}

	@Override
	public void showNeighbors() {
		// TODO 류지훈
	}

	@Override
	public void removeNeighbor() {
		// TODO 최미서
	}

	// ============ 다른 기능에서 사용하는 조회 ============

	@Override
	public List<User> getNeighbors(User user) {
		// TODO 류지훈
		return new ArrayList<>();
	}

}
