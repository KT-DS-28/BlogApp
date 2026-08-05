package com.ktdsuniv.blogapp.service;

import java.util.List;

import com.ktdsuniv.blogapp.domain.User;

/**
 * 이웃 기능 계약. 구현은 NeighborServiceImpl 에서 한다.
 *
 * 이 파일은 팀장이 관리한다. 시그니처 변경이 필요하면 팀장에게 요청할 것.
 */
public interface NeighborService {

	// ================= 메뉴 기능 =================

	/** 31번 메뉴 — 이웃 신청 (담당: 김경환) */
	void request();

	/** 32번 메뉴 — 받은 신청 처리, 수락 또는 거부 (담당: 엄예진) */
	void handleRequest();

	/** 33번 메뉴 — 이웃 목록 (담당: 류지훈) */
	void showNeighbors();

	/** 34번 메뉴 — 이웃 해제 (담당: 최미서) */
	void removeNeighbor();

	// ============ 다른 기능에서 사용하는 조회 ============

	/** 이웃 목록. state 가 ACCEPTED 인 상대만 반환한다. */
	List<User> getNeighbors(User user);

}
