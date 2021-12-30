package org.safetynet.p5safetynetalert.dbapi.model;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.repository.AddressRepository;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "addresses")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String road;

  private String city;

  private String zipCode;

  @ManyToOne(fetch = FetchType.LAZY)
  private FireStation fireStation;

  @OneToMany(mappedBy = "address",fetch = FetchType.LAZY)
  private Collection<Person> persons;

  public Address() {
  }

  public Address(String road, String city, String zipCode, FireStation fireStation) {
    this.road = road;
    this.city = city;
    this.zipCode = zipCode;
    this.fireStation = fireStation;
  }

  public Collection<Person> getPersons() {
    return persons;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setRoad(String road) {
    this.road = road;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public void setFireStation(FireStation fireStation) {
    this.fireStation = fireStation;
  }

  public Integer getId() {
    return id;
  }

  public String getRoad() {
    return road;
  }

  public String getCity() {
    return city;
  }

  public String getZipCode() {
    return zipCode;
  }

  public FireStation getFireStation() {
    return fireStation;
  }
}
