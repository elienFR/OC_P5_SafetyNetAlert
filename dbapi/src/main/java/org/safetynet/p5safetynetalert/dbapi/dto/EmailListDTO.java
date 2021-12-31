package org.safetynet.p5safetynetalert.dbapi.dto;

import lombok.Data;

@Data
public class EmailListDTO {
  private String cityName;
  private Iterable<String> emailList;
}
