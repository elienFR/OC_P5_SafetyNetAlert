package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Getter;

import java.util.Objects;

@Getter
public class ChildDTO {
  private String firstName;
  private String lastName;
  private Integer age;

   public ChildDTO(String firstName, String lastName, Integer age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChildDTO childDTO = (ChildDTO) o;
    return Objects.equals(firstName, childDTO.firstName) && Objects.equals(lastName, childDTO.lastName) && Objects.equals(age, childDTO.age);
  }
}
