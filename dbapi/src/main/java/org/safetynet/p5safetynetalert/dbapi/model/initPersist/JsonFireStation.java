package org.safetynet.p5safetynetalert.dbapi.model.initPersist;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class JsonFireStation {
  private String address;
  private String station;

  public JsonFireStation() {
  }

  public JsonFireStation(String address, String station) {
    this.address = address;
    this.station = station;
  }


}
