package org.safetynet.p5safetynetalert.dbapi.model.dto;



import java.util.Collection;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailListDTO {
  private String cityName;
  private Collection<String> emailList;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EmailListDTO that = (EmailListDTO) o;
    return Objects.equals(cityName, that.cityName) && Objects.equals(emailList, that.emailList);
  }
}
