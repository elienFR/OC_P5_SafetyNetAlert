package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Data;

@Data
public class AddressDTO {
  private String road;
  private String city;
  private String zip;

  public AddressDTO(String road, String city, String zip) {
    this.road = road;
    this.city = city;
    this.zip = zip;
  }
}
