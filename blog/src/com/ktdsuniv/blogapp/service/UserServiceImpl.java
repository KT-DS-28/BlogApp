package com.ktdsuniv.blogapp.service;

import java.util.ArrayList;
import java.util.List;

import com.ktdsuniv.blogapp.domain.User;
import com.ktdsuniv.blogapp.exception.BlogException;
import com.ktdsuniv.blogapp.exception.DuplicateUserIdException;
import com.ktdsuniv.blogapp.exception.LoginBlockedException;
import com.ktdsuniv.blogapp.exception.NotFoundException;
import com.ktdsuniv.blogapp.exception.NotLoggedInException;
import com.ktdsuniv.blogapp.util.ScannerUtil;
import com.ktdsuniv.blogapp.util.Session;

/**
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
		String id = ScannerUtil.nextLine("아이디:").trim();
		if(id == null || id.isBlank()) {
			throw new BlogException("아이디는 필수로 입력해야합니다");
		}
		if (findById(id) !=null) {
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
		USERS.add(newUser);
	}

	@Override
	public void login() {
		// TODO 최미서

		//아이디 비밀번호 입력 받기
		String id = ScannerUtil.nextLine("아이디:").trim();
		
		User user = findById(id);
		
		// 사용자의 아이디가 없다면
		if(user == null) {
			throw new NotFoundException("사용자");
		} 
		
		String password = null;
		
		for (int i = 0; i < 10; i++) {
			password= ScannerUtil.nextLine("비밀번호:").trim();
			// 사용자의 비밀번호가 일치
			 if (user.getPassword().equals(password)) {
				 	//세션에 유저정보 저장
					Session.login(user);
					// 로그인 시도횟수 초기화
					user.resetLoginTryCount();
					System.out.println(user.getName() + "로그인 되었습니다.");
					return;
				// 비밀번호가 일치 하지않는다.
				} else {
					// 로그인 시도횟수 증가
					user.increaseLoginTryCount();
					System.out.println("비밀번호가 올바르지 않습니다. "
							+ "(남은 시도 " +(10 - user.getLoginTryCount())  + "회)");
				}
			 
		}
		// 로그인시도 횟수가 10회 이상이라면
		if(user.isLoginBlocked()) {
			// 로그인 시도횟수 초기화
			user.resetLoginTryCount();
			throw new LoginBlockedException();	
		}
		
		
		
	}

	@Override
	public void logout() {
		// TODO 최미서
		// 사용자가 로그인이 되어있다면
		if(Session.isLoggedIn()) {
			Session.logout();
			System.out.println("로그아웃 되었습니다.");
		} else {
			throw new NotLoggedInException();
		}
	}

	@Override
	public void withdraw() {
		// TODO 최형선

		// 로그인한 사용자 정보는 Session 이 가지고 있다.
		User user = Session.getLoginUser();
		
		// 유저 삭제 후에 출력 메시지를 위한 임시 변수.
		String userName = user.getName();
		
		// getLoginUser 에서 NotLoggedInException 에서 이미 처리중이기때문에 
		// 로그인 하지 않은 상황에 대한 추가 예외처리를 하지 않아도 된다.
		USERS.remove(user);
		
		// 탈퇴 메시지 출력.
		System.out.println(userName+"님 탈퇴 처리 되었습니다.");
		
		// 유저 삭제 이후에 로그 아웃 처리한다.
		Session.logout();
	}

	// ============ 다른 기능에서 사용하는 조회 ============

	@Override
	public User findById(String id) {
		// TODO 이한결
		for(User user:USERS) {
			if(user.getId().equals(id)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		return USERS;
	}

}
