package com.ktdsuniv.blogapp.util;

import java.util.Scanner;

/**
 * 콘솔 입력을 담당한다.
 * Scanner 를 직접 생성하거나 close() 하지 말 것. System.in 이 닫히면 전체 입력이 중단된다.
 */
public class ScannerUtil {

	private static final Scanner SCANNER = new Scanner(System.in);

	public static String nextLine(String message) {
		System.out.print(message);
		return SCANNER.nextLine();
	}

	public static int nextInt(String message) {
		while (true) {
			String input = nextLine(message);
			try {
				return Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력해 주세요.");
			}
		}
	}

}
