package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Getter;

import java.util.Objects;

@Getter
public class ChildFromAddressDTO {
  private String title = "List of child at selected address";
  private Iterable<ChildDTO> childrenAtAddress;
  private Iterable<PersonDTO> otherAdultsAtAddress;

  public void setChildrenAtAddress(Iterable<ChildDTO> childrenAtAddress) {
    this.childrenAtAddress = childrenAtAddress;
  }

  public void setOtherAdultsAtAddress(Iterable<PersonDTO> otherAdultsAtAddress) {
    this.otherAdultsAtAddress = otherAdultsAtAddress;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChildFromAddressDTO that = (ChildFromAddressDTO) o;
    return Objects.equals(title, that.title) && Objects.equals(childrenAtAddress, that.childrenAtAddress) && Objects.equals(otherAdultsAtAddress, that.otherAdultsAtAddress);
  }
}
