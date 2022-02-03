package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class MedicalRecordsDTO {
  private Iterable<String> medications;
  private Iterable<String> allergies;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MedicalRecordsDTO that = (MedicalRecordsDTO) o;
    return Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
  }
}
