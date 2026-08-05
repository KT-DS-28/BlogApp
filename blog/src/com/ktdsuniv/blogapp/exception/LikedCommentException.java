package com.ktdsuniv.blogapp.exception;

public class LikedCommentException extends BlogException {

	private static final long serialVersionUID = 1L;

	public LikedCommentException() {
		super("좋아요가 있는 댓글은 수정하거나 삭제할 수 없습니다.");
	}

}
