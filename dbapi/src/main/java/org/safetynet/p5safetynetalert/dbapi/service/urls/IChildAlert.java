package org.safetynet.p5safetynetalert.dbapi.service.urls;

import org.safetynet.p5safetynetalert.dbapi.model.dto.ChildFromAddressDTO;

public interface IChildAlert {

/**
 * This method returns a list of children (anyone aged 18 or under) living at a specific road.
 * The list includes each child's first and last name, age, and a list of others
 * household members. If there is no child, this method return an empty string for children.
 */
  ChildFromAddressDTO getChildrenFromAddress(String address);

}
