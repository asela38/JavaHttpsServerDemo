package com.asela.security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.util.Arrays;

import org.junit.Test;

import com.asela.Utility;

public class DSATest {

	@Test
	public void testName() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA", "SUN");
		keyPairGenerator.initialize(1024, SecureRandom.getInstance("SHA1PRNG", "SUN"));
		KeyPair generateKeyPair = keyPairGenerator.generateKeyPair();
		PublicKey publicKey = generateKeyPair.getPublic();
		PrivateKey privateKey = generateKeyPair.getPrivate();
	
		DSAPublicKey dsaPublicKey = (DSAPublicKey) publicKey;
		DSAPrivateKey dsaPrivateKey = (DSAPrivateKey) privateKey;
		
		BigInteger publicP = dsaPublicKey.getParams().getP();
		BigInteger publicQ = dsaPublicKey.getParams().getQ();
		BigInteger publicG = dsaPublicKey.getParams().getG();
		BigInteger publicY = dsaPublicKey.getY();

		BigInteger privateP = dsaPrivateKey.getParams().getP();
		BigInteger privateQ = dsaPrivateKey.getParams().getQ();
		BigInteger privateG = dsaPrivateKey.getParams().getG();
		BigInteger privateX = dsaPrivateKey.getX();
		
		assertThat(publicP, is(privateP));
		assertThat(publicQ, is(privateQ));
		assertThat(publicG, is(privateG));
		
		BigInteger p = publicP;
		BigInteger q = publicQ;
		BigInteger g = publicG;
		BigInteger x = privateX;
		BigInteger y = publicY;
		
		assertTrue(p.isProbablePrime(100));
		assertTrue(q.isProbablePrime(100));
		BigInteger[] answer = p.subtract(BigInteger.ONE).divideAndRemainder(q);
		
		System.out.printf("%s / %s is %s and Remainder %s", p.subtract(BigInteger.ONE), q, answer[0], answer[1] );
		
		assertThat(answer[1], is(BigInteger.ZERO));
		// g < p
		assertTrue(g.compareTo(p) < 0);
		
		BigInteger remainder = g.modPow(q,p);
		
		assertThat(remainder, is(BigInteger.ONE));
		
		System.out.printf("%n%nX=%s and Y=%s %n",x, y);
		assertThat(g.modPow(x, p), is(y));
		
		//assertTrue(p.divideAndRemainder(val))

		
		
	}
	
	@Test
	public void checksum() throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		try (InputStream is = Files.newInputStream(Paths.get("C:/Asela/workspace/security/asela.com.crt"));
		     DigestInputStream dis = new DigestInputStream(is, md)) 
		{
		  /* Read decorated stream (dis) to EOF as normal... */
		}
		byte[] digest = md.digest();
		System.out.println(Arrays.toString(digest));
		BigInteger x = new BigInteger(digest);
		System.out.println(x.toString(2));
		System.out.println(x.toString(16));
		
		System.out.println(Utility.printHexBinary(digest).toUpperCase());
	}
	
}
