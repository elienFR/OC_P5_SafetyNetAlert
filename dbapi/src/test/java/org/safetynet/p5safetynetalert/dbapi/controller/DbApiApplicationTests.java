package org.safetynet.p5safetynetalert.dbapi.controller;

import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.JsonDataInjectorService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class DbApiApplicationTests {

	@MockBean
	private JsonDataInjectorService jsonDataInjectorService;

	@Test
	void contextLoads() {
	}

}