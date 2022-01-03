package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class FloodPersonsListDTO {
  private final String title = "A list of persons concerned by the selected fire station.";
  Collection<PersonForFloodDTO> persons;

}
