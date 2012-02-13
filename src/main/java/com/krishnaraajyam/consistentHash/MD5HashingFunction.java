package com.krishnaraajyam.consistentHash;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5HashingFunction implements HashingFunction {

	private static MessageDigest md;

	private static final String INITIALIZATION_FAILED = "Initialization Failed";

	public MD5HashingFunction() {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			throw new IllegalStateException(INITIALIZATION_FAILED, e);
		}
	}

	public int hash(String key) {
		md.update(key.getBytes(),0,key.length());
		int value = new BigInteger(md.digest()).intValue();
		md.reset();
		return value;
	}

}
