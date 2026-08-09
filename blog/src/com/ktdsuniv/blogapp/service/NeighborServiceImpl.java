package com.ktdsuniv.blogapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ktdsuniv.blogapp.domain.Neighbor;
import com.ktdsuniv.blogapp.domain.User;
import com.ktdsuniv.blogapp.enums.NeighborState;
import com.ktdsuniv.blogapp.exception.BlogException;
import com.ktdsuniv.blogapp.exception.DuplicateNeighborRequestException;
import com.ktdsuniv.blogapp.exception.NotFoundException;
import com.ktdsuniv.blogapp.exception.NotLoggedInException;
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

			System.out.println("(" + (i + 1) + ") ID: " + currUser.getId() + ", 이름: " + currUser.getName() + ", 블로그명: "
					+ currUser.getBlogName());
			// (1) ID: " ", 이름: " ", 블로그명: " "
		}
		// 신청 대상 아이디 입력받기
		String receiverId = ScannerUtil.nextLine("이웃신청할 아이디").trim();
		if (receiverId == null || receiverId.isBlank()) {
			throw new BlogException("아이디는 필수로 입력해야합니다");
		}
		if (receiverId.equals(requester.getId())) {
			throw new BlogException("본인에게는 이웃 신청을 할 수 없습니다.");
		}
		// 신청 대상 아이디로 user 가져오기
		User receiver = userService.findById(receiverId);
		if (receiver == null) {
			throw new NotFoundException("존재하지 않는 아이디입니다");
		}
		
		if (requester.getNeighbors().stream() // Stream<Neighbor>
				.anyMatch(neighbor -> neighbor.getOther(requester) == receiver)) {
					
					throw new DuplicateNeighborRequestException();
					
				}
		
		// 이웃 인스턴스 하나 생성
		Neighbor neighbor = new Neighbor(requester, receiver);
		
		// 양쪽 리스트에 같은 객체 추가
		requester.getNeighbors().add(neighbor);
		receiver.getNeighbors().add(neighbor);
		// 완료 메세지
		System.out.println(receiver.getName() + "님에게 이웃 신청을 보냈습니다.");
	}

	@Override
	public void handleRequest() {
		// TODO 엄예진
		if (!Session.isLoggedIn()) {
			throw new NotLoggedInException();
		}
		User user = Session.getLoginUser();

		List<Neighbor> pendingNeighbors = new ArrayList<>();
		for (Neighbor neighbor : user.getNeighbors()) {
			if (user == neighbor.getReceiver() && neighbor.getState() == NeighborState.PENDING) {
				pendingNeighbors.add(neighbor);
			}
		}
		if (pendingNeighbors.size() == 0) {
			System.out.println("이웃 신청 목록이 비어있습니다.");
			return;
		}

		Neighbor neighbor = null;
		for (int i = 0; i < pendingNeighbors.size(); i++) {
			neighbor = pendingNeighbors.get(i);
			System.out.println(i + ". 이웃 신청: " + neighbor.getRequester().getName());
		}

		int number = ScannerUtil.nextInt("처리할 번호를 입력하세요: ");
		if (number < 0 || number >= pendingNeighbors.size()) {
			System.out.println("잘못된 번호입니다.");
			return;
		}

		neighbor = pendingNeighbors.get(number);
		// 수락
		int answer = ScannerUtil.nextInt("수락은 1번, 거절은 2번을 숫자로 입력하세요.: ");
		if (answer == 1) {
			neighbor.setState(NeighborState.ACCEPTED);
			System.out.println("이웃 신청을 수락했습니다.");
		// 거절
		} else if (answer == 2) {
			neighbor.getRequester().getNeighbors().remove(neighbor);
			neighbor.getReceiver().getNeighbors().remove(neighbor);
			System.out.println("이웃 신청을 거부했습니다.");
		} else {
			System.out.println("잘못 입력하셨습니다.");
			return;
		}
	}

	@Override
	public void showNeighbors() {
		if (!Session.isLoggedIn()) {
			throw new NotLoggedInException();
		}
		User loginedUser = Session.getLoginUser();

		List<User> userList = getNeighbors(loginedUser);

		if (userList.isEmpty()) {
			System.out.println("이웃이 존재하지 않습니다.");
			return;
		}

		for (int i = 0; i < userList.size(); ++i) {
			System.out.println((i + 1) + ": " + userList.get(i));
		}
	}

	@Override
	public void removeNeighbor() {
		// TODO 최미서
		// 로그인 유무 확인
		User user = Session.getLoginUser();
		// 현재 Accepted상태인 이웃만 보여주기
		List<Neighbor> neighbors = new ArrayList<>();
		
		for (Neighbor neighbor : user.getNeighbors()) {
			if (neighbor.getState() == NeighborState.ACCEPTED) {
				neighbors.add(neighbor);
			}
		}
		// 이웃이 없을 경우
		if (neighbors.isEmpty()) {
			throw new BlogException("아직 이웃이 없습니다.");
		}
		
		// 이웃 출력하기 
		for (int i = 0; i < neighbors.size(); i++) {
			Neighbor neighbor = neighbors.get(i);
			
			User neighborUser = null;
			// neighbor에 있는 receiver가 본인인지 확인
			if(neighbor.getReceiver() == user ) {
				neighborUser = neighbor.getRequester();
			} else {
				neighborUser = neighbor.getReceiver();
			}
			System.out.println(i + ". 이웃의 이름: " +neighborUser.getName() + 
					", 블로그 명: " + neighborUser.getBlogName() );
		}
		
		int neighborIndex = ScannerUtil.nextInt("해제할 이웃의 번호를 입력해주세요. 번호: ");
		// 번호 존재 유무확인
 
		if (neighborIndex < 0 || neighborIndex >= neighbors.size()) {
			 throw new BlogException("잘못된 이웃 번호입니다.");
		}
		// 선택한 이웃 가져오기
		Neighbor selectedneighbor = neighbors.get(neighborIndex);
		
		//neighbor의 유저에서 본인이 아닌쪽이 receiver인지 requester인지 확인
		User neighbor = null;
		if (selectedneighbor.getReceiver() == user) {
			neighbor = selectedneighbor.getRequester();
		} else {
			neighbor = selectedneighbor.getReceiver();
		}
		// 양쪽에서 이웃 해제하기
		user.getNeighbors().remove(selectedneighbor);
		neighbor.getNeighbors().remove(selectedneighbor);
		
		System.out.println(neighbor.getName() + "님과 이웃이 해제되었습니다.");
		
		
		

	}

	// ============ 다른 기능에서 사용하는 조회 ============

	@Override
	public List<User> getNeighbors(User user) {
		List<User> userList = new ArrayList<>();
		for (Neighbor neighbor : user.getNeighbors()) {
			userList.add(neighbor.getOther(user));
		}
		return userList;
	}

}
