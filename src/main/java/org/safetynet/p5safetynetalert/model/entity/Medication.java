package org.safetynet.p5safetynetalert.model.entity;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
