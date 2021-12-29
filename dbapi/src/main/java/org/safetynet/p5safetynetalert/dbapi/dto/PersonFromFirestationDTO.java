package org.safetynet.p5safetynetalert.dbapi.dto;

import lombok.Data;

@Data
public class PersonFromFirestationDTO {
  private String firstName;
  private String lastName;
  private String phone;
  private AddressDTO address;

  public PersonFromFirestationDTO(){}
  public PersonFromFirestationDTO(String firstName, String lastName, String phone, AddressDTO address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.address = address;
  }
}
