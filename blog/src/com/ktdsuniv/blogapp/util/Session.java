package com.ktdsuniv.blogapp.util;

import com.ktdsuniv.blogapp.domain.User;
import com.ktdsuniv.blogapp.exception.NotLoggedInException;

/**
 * 현재 로그인한 사용자를 보관한다. 로그인은 한 번에 한 명만 가능하다.
 */
public class Session {

	private static User loginUser;

	public static User getLoginUser() {
		if (loginUser == null) {
			throw new NotLoggedInException();
		}
		return loginUser;
	}

	public static void login(User user) {
		loginUser = user;
	}

	public static void logout() {
		loginUser = null;
	}

	public static boolean isLoggedIn() {
		return loginUser != null;
	}

}
