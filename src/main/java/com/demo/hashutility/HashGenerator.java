package com.demo.hashutility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashGenerator {

	private static final String pepper = "Xy12Z36&$@#DPF1r7";

	// Generate secure salt
	public static String generateSalt(int length) {
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[length];
		sr.nextBytes(salt);
		return bytesToHex(salt);
	}

	// Generate hash with salt + pepper
	public static String hashWithSaltAndPepper(String input, String salt, HashAlgorithm algorithm) {
		try {
			String algo_str = getAlgorithm(algorithm);
			MessageDigest digest = MessageDigest.getInstance(algo_str);
			String combined = salt + input + pepper; // Salt + input + pepper
			byte[] hashBytes = digest.digest(combined.getBytes());
			return bytesToHex(hashBytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Invalid Algorithm: " + algorithm);
		}
	}

	private static String getAlgorithm(HashAlgorithm algorithm) {
		// TODO Auto-generated method stub
		String str = null;
		if (algorithm == HashAlgorithm.sha256) {
			str = "SHA-256";
		} else if (algorithm == HashAlgorithm.sha512) {
			str = "SHA-512";
		} else if (algorithm == HashAlgorithm.sha3512) {
			str = "SHA3-512";
		}
		return str;
	}

	// Convert byte array to hex string
	private static String bytesToHex(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
