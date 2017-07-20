package com.asela.security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.junit.Test;

public class RSATest {

	@Test
	public void loadPrivateAndPublicKeys() throws Exception {
		KeyStore keyStore = KeyStore.getInstance("JKS");
		Path jskPath = Paths.get("C:/Asela/workspace/security/keystore.jks");
		
		try(InputStream newInputStream = Files.newInputStream(jskPath)) {
			keyStore.load(newInputStream, "password".toCharArray());
		}
		
		for(String rsaAlaias : new String[] {"asela-rsa", "asela-rsa2", "asela-rsa3", "asela-rsa4"} ) {
			
	//		System.out.println("---RSA-Public---");
			Certificate crtRsa = keyStore.getCertificate(rsaAlaias);
	//		System.out.println(crtRsa.getType());
			
			RSAPublicKey publicKeyRsa = (RSAPublicKey) crtRsa.getPublicKey();
		//	System.out.println(publicKeyRsa.getAlgorithm());
		//	System.out.println(publicKeyRsa.getFormat());
			String publicKeyString = publicKeyRsa.getModulus().toString(16);
		//	System.out.println(publicKeyString);
	//		System.out.println(publicKeyString.length());
		//	System.out.println(publicKeyRsa.getPublicExponent());
			
			
	//		System.out.println("---RSA-Private---");
			RSAPrivateKey privateKeyRsa = (RSAPrivateKey)keyStore.getKey(rsaAlaias, "password".toCharArray());
		//	System.out.println(privateKeyRsa.getAlgorithm());
		//	System.out.println(privateKeyRsa.getFormat());
		//	System.out.println(privateKeyRsa.getPrivateExponent());
			String privateKeyString = privateKeyRsa.getModulus().toString(16);
	//		System.out.println(privateKeyString);
	//		System.out.println(privateKeyString.length());
			
			System.out.println("--RSA exponents---");
			assertThat(privateKeyRsa.getModulus(), is(publicKeyRsa.getModulus()));
			BigInteger d, e, m = privateKeyRsa.getModulus();
			System.out.println("Private key Exponent (d): " + (d = privateKeyRsa.getPrivateExponent()));
			System.out.println("Public Key Exponenet (e): " + (e = publicKeyRsa.getPublicExponent()));
			System.out.println("Public Key Exponenet (m): " + m);
			System.out.println("is m prime :  " + m.isProbablePrime(1000));
			
		
			BigInteger message = new BigInteger("10101"), encrypt;
	    	System.out.println("Encrypt: " + (encrypt = message.modPow(e, m)));
			System.out.println("Decrypt: " + encrypt.modPow(d, m));
			
		}
	}
}
