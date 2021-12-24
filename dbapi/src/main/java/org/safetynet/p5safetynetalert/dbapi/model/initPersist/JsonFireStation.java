package org.safetynet.p5safetynetalert.dbapi.model.initPersist;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class JsonFireStation {
  private Integer id;
  private String address;
  private String station;
}
