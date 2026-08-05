package com.ktdsuniv.blogapp;

import com.ktdsuniv.blogapp.exception.BlogException;
import com.ktdsuniv.blogapp.service.CommentService;
import com.ktdsuniv.blogapp.service.CommentServiceImpl;
import com.ktdsuniv.blogapp.service.NeighborService;
import com.ktdsuniv.blogapp.service.NeighborServiceImpl;
import com.ktdsuniv.blogapp.service.PostService;
import com.ktdsuniv.blogapp.service.PostServiceImpl;
import com.ktdsuniv.blogapp.service.UserService;
import com.ktdsuniv.blogapp.service.UserServiceImpl;
import com.ktdsuniv.blogapp.util.ScannerUtil;
import com.ktdsuniv.blogapp.util.Session;

public class Main {

	private static final UserService USER_SERVICE = new UserServiceImpl();
	private static final PostService POST_SERVICE = new PostServiceImpl();
	private static final CommentService COMMENT_SERVICE = new CommentServiceImpl();
	private static final NeighborService NEIGHBOR_SERVICE = new NeighborServiceImpl();

	public static void main(String[] args) {
		while (true) {
			printMenu();
			int menu = ScannerUtil.nextInt("메뉴 선택: ");

			if (menu == 0) {
				System.out.println("프로그램을 종료합니다.");
				return;
			}

			try {
				runMenu(menu);
			} catch (BlogException e) {
				System.out.println("[실패] " + e.getMessage());
			}
		}
	}

	private static void printMenu() {
		System.out.println();
		System.out.println("===== 블로그 =====");
		if (Session.isLoggedIn()) {
			System.out.println("로그인: " + Session.getLoginUser().getName());
		}
		System.out.println("[사용자]  1.등록  2.로그인  3.로그아웃  4.탈퇴");
		System.out.println("[게시글] 11.등록 12.목록 13.상세 14.발행 15.좋아요");
		System.out.println("[댓글]   21.작성 22.수정 23.삭제 24.좋아요");
		System.out.println("[이웃]   31.신청 32.신청처리 33.목록 34.해제");
		System.out.println("         0.종료");
	}

	private static void runMenu(int menu) {
		// 사용자
		if (menu == 1) {
			USER_SERVICE.register();
		} else if (menu == 2) {
			USER_SERVICE.login();
		} else if (menu == 3) {
			USER_SERVICE.logout();
		} else if (menu == 4) {
			USER_SERVICE.withdraw();
		// 게시글
		} else if (menu == 11) {
			POST_SERVICE.write();
		} else if (menu == 12) {
			POST_SERVICE.showPostList();
		} else if (menu == 13) {
			POST_SERVICE.showPostDetail();
		} else if (menu == 14) {
			POST_SERVICE.publish();
		} else if (menu == 15) {
			POST_SERVICE.like();
		// 댓글
		} else if (menu == 21) {
			COMMENT_SERVICE.addComment();
		} else if (menu == 22) {
			COMMENT_SERVICE.updateComment();
		} else if (menu == 23) {
			COMMENT_SERVICE.deleteComment();
		} else if (menu == 24) {
			COMMENT_SERVICE.likeComment();
		// 이웃
		} else if (menu == 31) {
			NEIGHBOR_SERVICE.request();
		} else if (menu == 32) {
			NEIGHBOR_SERVICE.handleRequest();
		} else if (menu == 33) {
			NEIGHBOR_SERVICE.showNeighbors();
		} else if (menu == 34) {
			NEIGHBOR_SERVICE.removeNeighbor();
		} else {
			System.out.println("없는 메뉴입니다.");
		}
	}

}
