package com.ktdsuniv.blogapp.exception;

public class DuplicateLikeException extends BlogException {

	private static final long serialVersionUID = 1L;

	public DuplicateLikeException() {
		super("이미 좋아요를 하였습니다.");
	}

}
