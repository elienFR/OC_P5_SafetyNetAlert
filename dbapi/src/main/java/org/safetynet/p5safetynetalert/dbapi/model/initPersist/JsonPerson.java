package org.safetynet.p5safetynetalert.dbapi.model.initPersist;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class JsonPerson {
  private Integer id;
  private String firstName;
  private String lastName;
  private String address;
  private String city;
  private String zip;
  private String phone;
  private String email;
}
