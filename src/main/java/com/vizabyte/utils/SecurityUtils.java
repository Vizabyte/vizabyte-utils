package com.vizabyte.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.text.Normalizer;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SecurityUtils {

	private static final String PBKDF2_SHA512 = "PBKDF2WithHmacSHA512"; // first in preference
	@SuppressWarnings("unused")
	private static final String PBKDF2_SHA1 = "PBKDF2WithHmacSHA1"; // second in preference

	public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	
	public static final int SAFE_KEY_LENGTH = 256;
	public static final int SAFER_KEY_LENGTH = 512;
	public static final int SAFEST_KEY_LENGTH = 1024;

	public static final int SAFE_SALT_LENGTH = 32;
	public static final int SAFER_SALT_LENGTH = 64;

	public static final int MINIMUM_ITERATIONS = 1000;
	public static final int BALANCED_ITERATIONS = 5000;
	public static final int EXTREME_ITERATIONS = 10000;

	private static final Random randomizer = new SecureRandom();
	private static final Encoder base64Encoder = Base64.getEncoder();
	
	public static byte[] hashPassword(String password, byte[] salt) {
		String nfcPassword = Normalizer.normalize(password, Normalizer.Form.NFD);
		nfcPassword = new String(nfcPassword.getBytes(), DEFAULT_CHARSET);
		return hash(nfcPassword.toCharArray(), salt, MINIMUM_ITERATIONS, SAFE_KEY_LENGTH);
	}
	
	private static byte[] hash(final char[] password, final byte[] salt, final int iterations,
			final int keyLength) {

		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_SHA512);
			PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
			SecretKey key = skf.generateSecret(spec);
			byte[] res = key.getEncoded();
			return res;

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	public static final byte[] generateSalt() {
		return getNextSalt(SAFE_SALT_LENGTH);
	}
	
	private static byte[] getNextSalt(int length) {
		byte[] salt = new byte[length];
		randomizer.nextBytes(salt);
		return salt;
	}

	
	/**
	 * Securely matches the input password with the hashed password with the given salt
	 * @param password - input password to be matched
	 * @param salt - salt used 
	 * @param expectedHash - hashed password
	 * @return
	 */
	public static boolean matchPassword(String password, byte[] salt, byte[] expectedHash) {
		byte[] pwdHash = hashPassword(password, salt);

		// secure match 
		int diff = pwdHash.length ^ expectedHash.length;
		for (int i = 0; i < pwdHash.length && i < expectedHash.length; i++) {
			diff |= pwdHash[i] ^ expectedHash[i];
		}
		return diff == 0;
	} // matchPassword() ends

	public static String toBase64StringUTF8(byte[] bytes) {
		return toBase64String(bytes, DEFAULT_CHARSET);
	}
	
	private static String toBase64String(byte[] bytes, Charset charset) {
		byte[] encodedBytes = base64Encoder.encode(bytes);
		return new String(encodedBytes, charset);
	}
	
}
