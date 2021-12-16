package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.Address;
import org.safetynet.p5safetynetalert.dbapi.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class AddressService {

  @Autowired
  private AddressRepository addressRepository;

  public Optional<Address> getAddress(final Integer id) {
    return addressRepository.findById(id);
  }

  public Iterable<Address> getAddresses() {
    return addressRepository.findAll();
  }

  public void deleteAddress(final Integer id) {
    addressRepository.deleteById(id);
  }

  public Address saveAddress(Address address) {
    Address savedAddress = addressRepository.save(address);
    return savedAddress;
  }

}
