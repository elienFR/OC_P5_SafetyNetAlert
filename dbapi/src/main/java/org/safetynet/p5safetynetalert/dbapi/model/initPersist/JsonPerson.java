package org.safetynet.p5safetynetalert.dbapi.model.initPersist;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class JsonPerson {
  private String firstName;
  private String lastName;
  private String address;
  private String city;
  private String zip;
  private String phone;
  private String email;
}
