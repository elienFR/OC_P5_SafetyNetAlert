package org.safetynet.p5safetynetalert.dbapi.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "persons_medications")
public class PersonsMedication {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Person person;

  @ManyToOne(fetch = FetchType.LAZY)
  private Medication medication;

  public PersonsMedication() {
  }

  public PersonsMedication(Person person, Medication medication) {
    this.person = person;
    this.medication = medication;
  }

  public Person getPerson() {
    return person;
  }

  public Medication getMedication() {
    return medication;
  }
}