package org.safetynet.p5safetynetalert.dbapi.model.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="fire_stations")
public class FireStation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String number;

  @OneToMany(mappedBy="fireStation", fetch = FetchType.LAZY)
  private Collection<Address> addresses;

  public FireStation() {};

  public FireStation(String number) {
    this.number = number;
  }

  public Collection<Address> getAddresses() {
    return addresses;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }
}
