package com.upscale.salesgate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SalesgateApplicationTests {

	private final ApplicationContext applicationContext;

	SalesgateApplicationTests(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Test
	void contextLoads() {
		assertEquals("upscale-salesgate-mvp", applicationContext.getId());
	}

}
