package org.safetynet.p5safetynetalert.model.entity;

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

  public Person getPerson() {
    return person;
  }

  public Medication getMedication() {
    return medication;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public void setMedication(Medication medication) {
    this.medication = medication;
  }
}
