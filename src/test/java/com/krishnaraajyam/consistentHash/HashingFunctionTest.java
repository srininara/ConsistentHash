package com.krishnaraajyam.consistentHash;

import org.junit.Before;
import org.junit.Test;

public class HashingFunctionTest {
	
	HashingFunction hash;

	@Before
	public void setup() {
		hash = new MD5HashingFunction();
	}
	
	@Test
	public void basicTest() {
		int value = hash.hash("sample");
		System.out.println(value);
	}

}
