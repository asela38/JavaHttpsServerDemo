package com.asela.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import org.junit.Test;

import com.asela.Utility;

public class DigestTest {

	private static final Path PATH = Paths.get("C:\\Users\\asela.illayapparachc\\Downloads\\openssl-1.0.2l.tar.gz");

    @Test
	public void print_digestValueInHex()  {
		Stream.of("SHA-1", "MD5", "SHA-256").forEach( algo -> {
			try {
    			MessageDigest digest = MessageDigest.getInstance(algo);
    
    			try (
    			        InputStream in = Files.newInputStream(PATH);
    			) {
        			byte[] buffer = new byte[8 * 1024];
        			int bytesRead;
        			while((bytesRead = in.read(buffer)) != -1) {
        				digest.update(buffer, 0, bytesRead);
        			}
    			} catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
    			Utility.decoratedPrint(
    					Utility.printHexBinary(digest.digest()).toLowerCase()
    					, algo
    					);
			
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalArgumentException(e);
            }
            
		} );
	}
}
