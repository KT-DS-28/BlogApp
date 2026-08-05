package com.ktdsuniv.blogapp.exception;

public class LoginFailedException extends BlogException {

	private static final long serialVersionUID = 1L;

	public LoginFailedException(int leftCount) {
		super("아이디 또는 비밀번호가 올바르지 않습니다. (남은 시도 " + leftCount + "회)");
	}

}
