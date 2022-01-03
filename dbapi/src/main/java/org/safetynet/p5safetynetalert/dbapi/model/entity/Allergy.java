package org.safetynet.p5safetynetalert.dbapi.model.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "allergies")
public class Allergy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @OneToMany(mappedBy = "allergy", fetch = FetchType.LAZY)
  private Collection<PersonsAllergy> personsAllergies;

  public Allergy() {};

  public Allergy(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
