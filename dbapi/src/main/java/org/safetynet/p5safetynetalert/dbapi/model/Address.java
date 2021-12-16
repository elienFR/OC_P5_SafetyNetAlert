package org.safetynet.p5safetynetalert.dbapi.model;

import lombok.Data;

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

}
