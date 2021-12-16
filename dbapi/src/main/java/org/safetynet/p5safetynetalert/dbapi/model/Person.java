package org.safetynet.p5safetynetalert.dbapi.model;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

import javax.persistence.*;

@Data
@Entity
@Table(name = "persons")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String firstName;

  private String lastName;

  private String birthDate;

  private String phone;

  private String email;

  @ManyToOne
  private Address address;

}
