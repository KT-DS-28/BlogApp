package com.ktdsuniv.blogapp.exception;

/**
 * 블로그 기능 수행 중 발생하는 모든 예외의 부모.
 * Main 에서 catch (BlogException e) 하나로 처리하므로, 각 기능에서는 throw 만 하면 된다.
 */
public class BlogException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BlogException(String message) {
		super(message);
	}

}
