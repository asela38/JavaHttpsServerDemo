package com.asela.security;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;

public class DigestTest {

	@Test
	public void print_digestValueInHex() throws Exception {
		for (String algo : new String[] {"SHA-1", "MD5", "SHA-256"}) {
			
			MessageDigest digest = MessageDigest.getInstance(algo);
			
			InputStream in = Files.newInputStream(Paths.get("C:\\Users\\asela.illayapparachc\\Downloads\\openssl-1.0.2l.tar.gz"));
			byte[] buffer = new byte[8 * 1024];
			int bytesRead;
			while((bytesRead = in.read(buffer)) != -1) {
				digest.update(buffer, 0, bytesRead);
			}
			System.out.println( algo + " : " +
					DatatypeConverter.printHexBinary(digest.digest()).toLowerCase());
		}
	}
}
