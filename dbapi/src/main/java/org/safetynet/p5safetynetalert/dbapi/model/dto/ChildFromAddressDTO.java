package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Data;

@Data
public class ChildFromAddressDTO {
  private String title = "List of child at selected address";
  private Iterable<ChildDTO> childrenAtAddress;
  private Iterable<PersonDTO> otherAdultsAtAddress;
}
