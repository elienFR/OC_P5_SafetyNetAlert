package org.safetynet.p5safetynetalert.dbapi.model;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

import javax.persistence.*;

@Data
@Entity
@Table(name = "persons")
public class Person {
  @Id
  @GeneratedValue
  @Column(name = "person_id")
  private Integer personId;
  @Column(name = "first_name")
  private String firstname;
  @Column(name = "last_name")
  private String lastname;
  private String birthdate;
  private String address;
  private String phone;
  private String email;
}
