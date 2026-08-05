package com.ktdsuniv.blogapp.exception;

public class NotLoggedInException extends BlogException {

	private static final long serialVersionUID = 1L;

	public NotLoggedInException() {
		super("로그인이 필요한 기능입니다.");
	}

}
