package org.safetynet.p5safetynetalert;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
class DbApiApplicationTests {

	@Autowired
	DbApiApplication dbApiApplication;
	@MockBean
	private IJsonDataInjectorService iJsonDataInjectorService;

	@Test
	void contextLoads() {
		verify(iJsonDataInjectorService, Mockito.times(1)).initDb();
	}

}
