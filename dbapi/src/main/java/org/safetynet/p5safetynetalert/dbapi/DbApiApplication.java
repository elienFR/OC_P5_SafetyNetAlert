package org.safetynet.p5safetynetalert.dbapi;

import org.safetynet.p5safetynetalert.dbapi.model.initPersist.*;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.JsonDataInjectorService;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.JsonFileExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbApiApplication implements CommandLineRunner {

  @Autowired
  JsonDataInjectorService jsonDataInjectorService;

  public static void main(String[] args) {
    SpringApplication.run(DbApiApplication.class, args);
  }

  @Override
  public void run(String... args) {

    jsonDataInjectorService.initDb();

    System.out.println("test");

  }
}
