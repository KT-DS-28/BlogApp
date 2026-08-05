package com.ktdsuniv.blogapp.exception;

public class DuplicateUserIdException extends BlogException {

	private static final long serialVersionUID = 1L;

	public DuplicateUserIdException() {
		super("이미 사용 중인 아이디입니다.");
	}

}
