package com.lds.CRA;

import com.lds.CRA.obj.Client;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecureSystem {

	private static Client c = new Client("Lyndon", "p@ssW0rd");

	public static void main(String[] args) {
		String sysHash;
		String userHash;

		System.out.println("Please enter your username.");
		String usr = c.getUsername();
		System.out.printf("Username recieved: %s\n", usr);
		
		int nonce = login(usr);

		System.out.printf("Hashing...\n");
		sysHash = hash(nonce, c.getPassword());
		System.out.printf("System hash created: %s\n", sysHash);
		
		System.out.println("\nPlease enter password");
		String pass = c.getPassword();
		System.out.printf("Password recieved: %s\n", pass);
		userHash = hash(nonce, pass);

		authenticate(sysHash, userHash);
	}

	/*
	 * When a valid user logs on, the system will generate a random number(nonce)
	 * that will be hashed with the user's known password and the hash will be
	 * stored in the system.
	 */
	public static int login(String username) {
		String user = c.getUsername();
		int nonce = 0;

		if (user == username) {
			System.out.println("Username is valid...");
			nonce = (int) (Math.random() * 1000);
			System.out.printf("\nGenerating nonce = %d\n", nonce);

		} else {
			System.out.printf("Username is not valid. Please try again...\n");
		}
		return nonce;
	}

	
	public static String hash(int r, String password) {
		String hash = r + password;
		try {
			MessageDigest ms = MessageDigest.getInstance("SHA-1");
			
			byte[] arr = ms.digest(hash.getBytes());
			
			BigInteger num = new BigInteger(1, arr);
			
			String hex = num.toString(16);
			
			while(hex.length() < 32) {
				hex = "0" + hex;
			}
			
			return hex;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static void authenticate(String h1, String h2) {
		if (h1.equals(h2)) {
			System.out.printf("\nCongratulations! The passwords match!\n");
			System.out.printf("sysHash = %s\n", h1);
			System.out.printf("userHash = %s\n", h2);
		} else {
			System.out.printf("\nSorry, an incorrect password was given.\n");
			System.out.printf("sysHash = %s\n", h1);
			System.out.printf("userHash = %s\n", h2);
		}
	}

}
