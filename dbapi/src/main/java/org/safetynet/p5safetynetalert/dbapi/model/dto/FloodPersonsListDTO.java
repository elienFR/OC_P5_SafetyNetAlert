package org.safetynet.p5safetynetalert.dbapi.model.dto;



import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FloodPersonsListDTO {
  private final String title = "A list of persons concerned by the selected fire station.";
  Collection<PersonForFloodDTO> persons;
}
