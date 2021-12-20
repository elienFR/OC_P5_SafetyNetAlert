package org.safetynet.p5safetynetalert.dbapi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "persons_medications")
public class PersonsMedication {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  private Person person;

  @ManyToOne
  private Medication medication;

  public PersonsMedication() {
  }

  public PersonsMedication(Person person, Medication medication) {
    this.person = person;
    this.medication = medication;
  }
}
