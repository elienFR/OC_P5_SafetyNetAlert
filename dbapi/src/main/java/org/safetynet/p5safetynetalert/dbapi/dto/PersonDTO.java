package org.safetynet.p5safetynetalert.dbapi.dto;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.Address;

@Data
public class PersonDTO {
  private String firstName;
  private String lastName;
  private String birthDate;
  private String phone;
  private String email;

  public PersonDTO(){}
  public PersonDTO(String firstName, String lastName, String birthDate, String phone, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.phone = phone;
    this.email = email;
  }
}
