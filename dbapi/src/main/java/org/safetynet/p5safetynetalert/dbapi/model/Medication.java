package org.safetynet.p5safetynetalert.dbapi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="medications")
public class Medication {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @OneToMany(mappedBy = "medication", fetch = FetchType.LAZY)
  private Collection<PersonsMedication> personsMedications;

  public Medication() {};

  public Medication(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
