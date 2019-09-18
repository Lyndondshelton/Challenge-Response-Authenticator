package com.lds.CRA;

import com.lds.CRA.obj.Client;

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

	public static int login(String username) {
		/*
		 * When a valid user logs on, the system will generate a random number(nonce)
		 * that will be hashed with the user's known password and the hash will be
		 * stored in the system.
		 */
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
		// INSERT PASSWORD HASH ALGORITHM HERE

		String hash = r + password;

		return hash;
	}

	public static void authenticate(String h1, String h2) {
		if (h2 == h1) {
			System.out.printf("\nCongratulations! The passwords match!\n");
			System.out.printf("sysHash = %s", h1);
			System.out.printf("userHash = %s", h2);
		} else {
			System.out.printf("\nSorry, an incorrect password was given.\n");
			System.out.printf("sysHash = %s\n", h1);
			System.out.printf("userHash = %s\n", h2);
		}
	}

}