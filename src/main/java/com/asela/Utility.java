package com.asela;

import javax.xml.bind.DatatypeConverter;

public interface Utility {

	public static String printHexBinary(byte...bytes) {
		return DatatypeConverter.printHexBinary(bytes);
	}
	
	public static <T> void decoratedPrint(T t, String discription) {
		System.out.printf("%n---%s---%n", discription);
		System.out.print(t.toString());
		System.out.printf("%n--------%n");
	}
}
