package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Data;

@Data
public class PersonDTO {
  private String firstName;
  private String lastName;
  private String phone;
  private String birthDate;
  private AddressDTO address;


  public PersonDTO(){}
  public PersonDTO(String firstName, String lastName, String phone, String birthDate, AddressDTO address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.birthDate = birthDate;
    this.address = address;
  }
}
