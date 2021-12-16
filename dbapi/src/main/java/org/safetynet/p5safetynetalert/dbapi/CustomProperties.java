package org.safetynet.p5safetynetalert.dbapi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "org.safetynet.p5safetynetalert.dbapi")
@Data
public class CustomProperties {

  private static String dumbProp;

}
