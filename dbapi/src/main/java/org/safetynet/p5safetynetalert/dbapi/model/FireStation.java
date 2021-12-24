package org.safetynet.p5safetynetalert.dbapi.model;

import lombok.Data;
import org.apache.catalina.LifecycleState;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="fire_stations")
public class FireStation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String number;

  @OneToMany(mappedBy="fireStation")
  private List<Address> address;

  public FireStation() {};

  public FireStation(String number) {
    this.number = number;
  }
}
