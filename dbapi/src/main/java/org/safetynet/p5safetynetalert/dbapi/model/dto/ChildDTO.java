package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Data;

@Data
public class ChildDTO {
  private String firstName;
  private String lastName;
  private Integer age;

  public ChildDTO(){}
  public ChildDTO(String firstName, String lastName, Integer age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }
}
