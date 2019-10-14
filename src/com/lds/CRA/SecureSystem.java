package com.lds.CRA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.lds.CRA.obj.Client;

public class SecureSystem {

	private static Client c = new Client("Lyndon", "p@ssW0rd");

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String sysHash;
		String userHash;

		System.out.println("Please enter your username.");
		String usr = c.getUsername();
		System.out.printf("Username recieved: %s\n", usr);

		int nonce = login(usr);

		System.out.printf("Hashing...\n");
		sysHash = hash(nonce, c.getPassword());

		System.out.println("\nPlease enter password");
		String pass = in.readLine();
		userHash = hash(nonce, pass);

		authenticate(sysHash, userHash);
	}

	public static int login(String username) {
		String user = c.getUsername();
		int nonce = 0;

		if (user.equals(username)) {
			System.out.println("Username is valid...");
			nonce = (int) (Math.random() * 1000);
			System.out.printf("\nGenerating nonce...\n");

		} else {
			System.out.printf("Username is not valid. Please try again...\n");
		}
		return nonce;
	}

	public static String hash(int r, String password) {
		String hash = r + password;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] arr = md.digest(hash.getBytes());
			BigInteger bInt = new BigInteger(1, arr);
			String hex = bInt.toString(16);
			while (hex.length() < 32) {
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
		} else {
			System.out.printf("\nSorry, an incorrect password was given.\n");
		}
	}

}
