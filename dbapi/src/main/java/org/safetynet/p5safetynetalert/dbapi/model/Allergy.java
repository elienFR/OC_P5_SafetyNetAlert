package org.safetynet.p5safetynetalert.dbapi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "allergies")
public class Allergy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  public Allergy() {};

  public Allergy(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
