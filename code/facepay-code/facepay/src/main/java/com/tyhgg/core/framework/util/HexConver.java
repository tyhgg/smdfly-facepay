package com.tyhgg.core.framework.util;

import java.util.ArrayList;

public class HexConver {

	/**
	 * 16进制串转字节数组
	 * @param hexString
	 * @return
	 */
	public static byte[] HexToBin(String hexString) {
		ArrayList<Byte> list = new ArrayList<Byte>();
		for (int i = 0; i < hexString.length(); i += 2) {
			char a = hexString.charAt(i);
			char b = hexString.charAt(i + 1);
			int ia = getIntValue(a);
			int ib = getIntValue(b);
			list.add(Byte.valueOf(Integer.valueOf(ia * 16 + ib).byteValue()));
		}
		list.trimToSize();
		byte[] ret = new byte[list.size()];
		for (int i = 0; i < list.size(); ++i) {
			ret[i] = list.get(i).byteValue();
		}
		return ret;

	}

	/**
	 * 字节数组转16进制串
	 * @param arr
	 * @return
	 */
	public static String BinToHex(byte[] arr) {
		ArrayList<Character> list = new ArrayList<Character>();
		for (int i = 0; i < arr.length; ++i) {
			int v = arr[i];
			if (v < 0) {
				v += 256;
			}
			int a = v / 16;
			int b = v % 16;
			list.add(Character.valueOf(getHexValue(a)));
			list.add(Character.valueOf(getHexValue(b)));
		}
		list.trimToSize();
		char[] cArr = new char[list.size()];
		for (int i = 0; i < list.size(); ++i) {
			cArr[i] = list.get(i).charValue();
		}

		return new String(cArr);

	}

	private static int getIntValue(char c) {
		switch (c) {
		case 'A':
		case 'a':
			return 10;
		case 'B':
		case 'b':
			return 11;
		case 'C':
		case 'c':
			return 12;
		case 'D':
		case 'd':
			return 13;
		case 'E':
		case 'e':
			return 14;
		case 'F':
		case 'f':
			return 15;
		default:
			return (int) (c - '0');

		}
	}

	private static char getHexValue(int c) {
		String s = Integer.toHexString(c).toUpperCase();
		return s.charAt(0);
	}

}