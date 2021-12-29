package org.safetynet.p5safetynetalert.dbapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PersonFromFirestationDTO {
  private String firstName;
  private String lastName;
  private String phone;
  private AddressDTO address;
  private String birthDate;

  public PersonFromFirestationDTO(){}
  public PersonFromFirestationDTO(String firstName, String lastName, String phone, AddressDTO address, String birthDate) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.address = address;
    this.birthDate = birthDate;
  }
}
