package org.safetynet.p5safetynetalert.dbapi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class City {

  @Id
  private String zip;
  @Column(name="city")
  private String name;

}
