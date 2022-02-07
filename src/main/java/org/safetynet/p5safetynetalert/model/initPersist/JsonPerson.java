package org.safetynet.p5safetynetalert.model.initPersist;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class JsonPerson {
  private String firstName;
  private String lastName;
  private String address;
  private String city;
  private String zip;
  private String phone;
  private String email;

  public JsonPerson(){}
  public JsonPerson(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.city = city;
    this.zip = zip;
    this.phone = phone;
    this.email = email;
  }
}
