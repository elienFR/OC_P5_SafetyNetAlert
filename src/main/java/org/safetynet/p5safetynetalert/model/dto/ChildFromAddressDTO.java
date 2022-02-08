package org.safetynet.p5safetynetalert.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ChildFromAddressDTO {
  private final String title = "List of child at selected address";
  private Iterable<ChildDTO> childrenAtAddress;
  private Iterable<PersonDTO> otherAdultsAtAddress;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChildFromAddressDTO that = (ChildFromAddressDTO) o;
    return Objects.equals(childrenAtAddress, that.childrenAtAddress) && Objects.equals(otherAdultsAtAddress, that.otherAdultsAtAddress);
  }
}
