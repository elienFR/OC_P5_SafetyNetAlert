package org.safetynet.p5safetynetalert.dbapi.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Medication {

  @Id
  @GeneratedValue
  private Integer id;
  private String name;

}
