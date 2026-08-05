package com.ktdsuniv.blogapp.exception;

public class SelfLikeException extends BlogException {

	private static final long serialVersionUID = 1L;

	public SelfLikeException() {
		super("자신이 작성한 글에는 좋아요를 할 수 없습니다.");
	}

}
