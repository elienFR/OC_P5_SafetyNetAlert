package org.safetynet.p5safetynetalert;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Configuration
@ConfigurationProperties(prefix = "org.safetynet.p5safetynetalert.dbapi")
@Setter
@Getter
public class CustomProperties {

  private String mainPath;
  private String mainResourcesPath;

  @Override
  public int hashCode() {
    return Objects.hash(mainPath, mainResourcesPath);
  }
}
