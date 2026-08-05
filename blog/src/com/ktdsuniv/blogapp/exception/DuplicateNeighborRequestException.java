package com.ktdsuniv.blogapp.exception;

public class DuplicateNeighborRequestException extends BlogException {

	private static final long serialVersionUID = 1L;

	public DuplicateNeighborRequestException() {
		super("이미 이웃 신청을 하였습니다.");
	}

}
