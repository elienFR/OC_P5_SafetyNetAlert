package org.safetynet.p5safetynetalert.dbapi.model;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.repository.AddressRepository;

import javax.persistence.*;

@Data
@Entity
@Table(name = "addresses")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String road;

  private String city;

  private String zipCode;

  @ManyToOne
  private FireStation fireStation;

  public Address() {};
  public Address(String road, String city, String zipCode, FireStation fireStation) {
    this.road = road;
    this.city = city;
    this.zipCode = zipCode;
    this.fireStation = fireStation;
  }
}
