package com.ktdsuniv.blogapp.exception;

public class LoginBlockedException extends BlogException {

	private static final long serialVersionUID = 1L;

	public LoginBlockedException() {
		super("비밀번호를 10회 이상 틀려 로그인할 수 없습니다.");
	}

}
