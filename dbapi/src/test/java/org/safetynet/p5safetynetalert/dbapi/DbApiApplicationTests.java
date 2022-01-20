package org.safetynet.p5safetynetalert.dbapi;

import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class DbApiApplicationTests {

	@MockBean
	private IJsonDataInjectorService IJsonDataInjectorService;

	@Test
	void contextLoads() {
	}

}
