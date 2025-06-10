package com.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SSystemApplicationTests {

	@Autowired
    private PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {
	}

	@Test
	void testPasswordEncoder() {
		String rawPassword = "200505";
		String encoded = passwordEncoder.encode(rawPassword);
		System.out.println("加密结果: " + encoded);
		assertTrue(passwordEncoder.matches(rawPassword, encoded));
}

}
