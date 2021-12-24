package org.safetynet.p5safetynetalert.dbapi;

import org.safetynet.p5safetynetalert.dbapi.model.Address;
import org.safetynet.p5safetynetalert.dbapi.model.FireStation;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.safetynet.p5safetynetalert.dbapi.repository.FireStationRepository;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.JsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DbApiApplication implements CommandLineRunner {

  @Autowired
  JsonDataInjectorService jsonDataInjectorService;
  @Autowired
  FireStationRepository fireStationRepository;

  public static void main(String[] args) {
    SpringApplication.run(DbApiApplication.class, args);
  }

  @Override
  public void run(String... args) {
    //Initialisation of the database thanks to the data.json file located in resources
    jsonDataInjectorService.initDb();

  }
}
