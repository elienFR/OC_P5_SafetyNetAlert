package org.safetynet.p5safetynetalert.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
  private String road;
  private String city;
  private String zip;

  public AddressDTO() {}

}

