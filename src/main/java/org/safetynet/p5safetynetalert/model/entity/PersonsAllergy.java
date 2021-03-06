package org.safetynet.p5safetynetalert.model.entity;

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

  public Allergy getAllergy() {
    return allergy;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public void setAllergy(Allergy allergy) {
    this.allergy = allergy;
  }
}
