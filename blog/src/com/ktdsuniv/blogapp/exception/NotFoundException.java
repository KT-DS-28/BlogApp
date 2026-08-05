package com.ktdsuniv.blogapp.exception;

public class NotFoundException extends BlogException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String target) {
		super(target + "을(를) 찾을 수 없습니다.");
	}

}
