package com.ktdsuniv.blogapp.service;

import java.util.ArrayList;
import java.util.List;

import com.ktdsuniv.blogapp.domain.Neighbor;
import com.ktdsuniv.blogapp.domain.User;
import com.ktdsuniv.blogapp.exception.BlogException;
import com.ktdsuniv.blogapp.exception.NotFoundException;
import com.ktdsuniv.blogapp.util.ScannerUtil;
import com.ktdsuniv.blogapp.util.Session;

/**
 * 이웃 기능 구현. 구현 방법은 docs/IMPLEMENTATION_GUIDE.md 참고.
 *
 * 이 파일은 4명이 나눠 쓴다. 자기 담당 메서드 안에서만 작업하고 다른 메서드의 위치나 순서를 바꾸지 말 것. (Git 충돌 방지)
 */
public class NeighborServiceImpl implements NeighborService {

	private final UserService userService = new UserServiceImpl();

	// ================= 메뉴 기능 =================

	@Override
	public void request() {
		User requester = Session.getLoginUser(); // 로그인한 신청자 가져오기
		List<User> userList = userService.getAllUsers();
		User currUser = null;
		for (int i = 0; i < userList.size(); i++) {
			currUser = userList.get(i);

			// 본인이면 pass
			if (currUser == requester) {
				continue;
			}

			// 이미 대기중이거나 이웃인애들은 패스
			if (requester.getNeighbors().contains(currUser)) {
				continue;
			}

			//
			System.out.println("(" + (i + 1) + ") ID: " + currUser.getId() + ", 이름: " + currUser.getName() + ", 블로그명: "
					+ currUser.getBlogName());
			// (1) ID: " ", 이름: " ", 블로그명: " "
		}
//		System.out.println(userList);

		// 신청 대상 아이디 입력받기
		String receiverId = ScannerUtil.nextLine("이웃신청할 아이디").trim();
		if (receiverId == null || receiverId.isBlank()) {
			throw new BlogException("아이디는 필수로 입력해야합니다");
		}
		if (receiverId == requester.getId()) {
			throw new BlogException("");
		}
		// 신청 대상 아이디로 user 가져오기 
		User receiver = userService.findById(receiverId);
		if (receiver == null) {
			throw new NotFoundException("존재하지 않는 아이디입니다");
		}
//
//		// 자기 자신 체크
//
//		// 중복 신청 체크
//
		// 이웃 인스턴스 하나 생성
		Neighbor neighbor = new Neighbor(requester, receiver); // 이웃 인스턴스 하나 생성
//
//		// 양쪽 리스트에 같은 객체 추가
		requester.getNeighbors().add(neighbor);
		receiver.getNeighbors().add(neighbor);
//		// 완료 메세지
		
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
