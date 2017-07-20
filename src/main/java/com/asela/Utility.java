package com.asela;

import javax.xml.bind.DatatypeConverter;

public interface Utility {

	public static String printHexBinary(byte...bytes) {
		return DatatypeConverter.printHexBinary(bytes);
	}
}
