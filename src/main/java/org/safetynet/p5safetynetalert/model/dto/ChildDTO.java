package org.safetynet.p5safetynetalert.model.dto;


import lombok.Setter;

import java.util.Objects;

@Setter
public class ChildDTO {
  private String firstName;
  private String lastName;
  private Integer age;

   public ChildDTO() {}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChildDTO childDTO = (ChildDTO) o;
    return Objects.equals(firstName, childDTO.firstName) && Objects.equals(lastName, childDTO.lastName) && Objects.equals(age, childDTO.age);
  }
}
