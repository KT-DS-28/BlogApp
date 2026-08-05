package com.ktdsuniv.blogapp.exception;

public class AccessDeniedException extends BlogException {

	private static final long serialVersionUID = 1L;

	public AccessDeniedException() {
		super("권한이 없습니다.");
	}

}
