package org.safetynet.p5safetynetalert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbApiApplication implements CommandLineRunner {

  @Autowired
  org.safetynet.p5safetynetalert.service.initPersist.IJsonDataInjectorService IJsonDataInjectorService;

  public static void main(String[] args) {
    SpringApplication.run(DbApiApplication.class, args);
  }

  @Override
  public void run(String... args) {
    //Initialisation of the database thanks to the data.json file located in resources
    IJsonDataInjectorService.initDb();

  }
}
