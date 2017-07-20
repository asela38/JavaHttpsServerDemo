package com.asela;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.bind.DatatypeConverter;

public interface Utility {

	public static String printHexBinary(byte...bytes) {
		return DatatypeConverter.printHexBinary(bytes);
	}
	
	public static <T> void decoratedPrint(T t, String discription) {
	    String content = t.toString();
	    String title = discription;
	    
	    Integer maxLength = Integer.max(content.length(), title.length()) ;
	    if(maxLength == content.length()) title = center(title, maxLength);
	    else content = center(content, maxLength);
	    
	    String lineSeperator = "+" + IntStream.range(0, maxLength + 2).boxed().map(i -> "-").collect(Collectors.joining()) + "+";
	    System.out.println(lineSeperator);
	    System.out.println("| " + title + " |");
	    System.out.println(lineSeperator);
		System.out.println("| "  + content + " |");
		System.out.println(lineSeperator);
	}
	
	public static String center(String string, Integer length) {
	    Integer diff = length - string.length();
        Integer halfOfDiff = diff / 2;
        Integer diffRemainder = diff % 2;
        String rightAdj = IntStream.range(0, halfOfDiff).boxed().map(i -> " ").collect(Collectors.joining());
        String LeftAdj = IntStream.range(0, halfOfDiff + diffRemainder).boxed().map(i -> " ").collect(Collectors.joining());
        return rightAdj + string + LeftAdj;
	}
}
