package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class EmailListDTO {
  private String cityName;
  private Collection<String> emailList;
}
