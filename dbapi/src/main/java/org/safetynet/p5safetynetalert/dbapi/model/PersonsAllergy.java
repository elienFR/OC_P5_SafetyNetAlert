package org.safetynet.p5safetynetalert.dbapi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "persons_allergies")
public class PersonsAllergy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Person person;

  @ManyToOne(fetch = FetchType.LAZY)
  private Allergy allergy;

  public PersonsAllergy() {
  }

  public PersonsAllergy(Person person, Allergy allergy) {
    this.person = person;
    this.allergy = allergy;
  }
}
