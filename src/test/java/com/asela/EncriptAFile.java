package com.asela;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

public class EncriptAFile {
	
	private static final String ALIAS = "fileencript";
	private static final char[] PASSWORD = "password".toCharArray();
	
	private static final String FOLDER = "C:\\Asela\\workspace\\security\\%s";
	private static final Path CIPHER_FILE_PATH = Paths.get(String.format(FOLDER, "Cipher-File.txt"));
	private static final Path ORIGINAL_FILE_PATH = Paths.get(String.format(FOLDER, "Original-File.txt"));
	private static final Path KEYSTORE_PATH = Paths.get(String.format(FOLDER, "keystore.jks"));
	private static final Path PUBLIC_KEY_PATH = Paths.get(String.format(FOLDER, "PUBLICKEY"));
	private static final Path SIGNATURE_PATH = Paths.get(String.format(FOLDER, "SIGNATURE"));

	public static void main(String[] args) {
		
		try {
			
			
			KeyStore keystore = KeyStore.getInstance("JKS");
			keystore.load(Files.newInputStream(KEYSTORE_PATH)
								, PASSWORD);
			
			Certificate certificate = keystore.getCertificate(ALIAS);
			PublicKey publicKey = certificate.getPublicKey();
			PrivateKey privateKey = (PrivateKey) keystore.getKey(ALIAS, PASSWORD);
	
			p(publicKey, "Public Key");
			p(privateKey, "Private Key");
			
			Signature signature = Signature.getInstance("SHA1WithRSA");
			signature.initSign(privateKey);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			try(
					InputStream inStream = Files.newInputStream(ORIGINAL_FILE_PATH);
					OutputStream outStream = Files.newOutputStream(CIPHER_FILE_PATH, 
							StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
					CipherOutputStream cipherOutputStream = new CipherOutputStream(outStream,  cipher);
			){
				write(inStream, cipherOutputStream);
			} 
			
			try(
					InputStream in = Files.newInputStream(CIPHER_FILE_PATH);
					
			) {
				byte[] buffer = new byte[8 * 1024];
				int bytesRead;
				while((bytesRead = in.read(buffer)) != -1) {
					signature.update(buffer, 0, bytesRead);
				}
			}
			
			
			try(
					OutputStream out = Files.newOutputStream(SIGNATURE_PATH
							, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
			) {
				out.write(signature.sign());
			}
			
			try(
					OutputStream out = Files.newOutputStream(PUBLIC_KEY_PATH
							, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
			) {
				out.write(publicKey.getEncoded());
			}
			
			byte[] pub= Files.readAllBytes(PUBLIC_KEY_PATH);
			byte[] sin= Files.readAllBytes(SIGNATURE_PATH);
			
//			try (
//					FileInputStream fileInputStream = new FileInputStream(PUBLIC_KEY_PATH.toFile());
//			) {
//				pub =  new byte[fileInputStream.available()];
//				fileInputStream.read(pub);
//			}
			
			
			X509EncodedKeySpec spec = new X509EncodedKeySpec(pub);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey public1 = keyFactory.generatePublic(spec);
			
			Signature signature2 = Signature.getInstance("SHA1WithRSA");
			signature2.initVerify(public1);
			
			try(
					InputStream in = Files.newInputStream(CIPHER_FILE_PATH);
					
			) {
				byte[] buffer = new byte[8 * 1024];
				int bytesRead;
				while((bytesRead = in.read(buffer)) != -1) {
					signature2.update(buffer, 0, bytesRead);
				}
			}
			
			System.out.println(signature2.verify(sin));
		
			
			
		} catch (KeyStoreException 
					| NoSuchAlgorithmException 
					| UnrecoverableKeyException
					| NoSuchPaddingException
					| InvalidKeyException
					| SignatureException
					| InvalidKeySpecException
					| CertificateException
					e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void write(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[8 * 1024];
		int bytesRead;
		while((bytesRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
		}
	}
	public static <T> void p(T t, String discription) {
		System.out.printf("%n---%s---%n", discription);
		System.out.print(t.toString());
		System.out.printf("%n--------%n");
	}
}
