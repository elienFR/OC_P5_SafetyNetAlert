package org.safetynet.p5safetynetalert.dbapi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="persons_allergies")
public class PersonsAllergy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  private Person person;

  @ManyToOne
  private Allergy allergy;

}
