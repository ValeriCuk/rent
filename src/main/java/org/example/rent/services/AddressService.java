package org.example.rent.services;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.AddressDTO;
import org.example.rent.entity.Address;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.CustomLogger;
import org.example.rent.repositories.interfaces.AddressRepository;
import org.example.rent.services.mappers.AddressMapper;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final Logger log = CustomLogger.getLog();

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    //getById(Long id)
    public AddressDTO getById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new NotFoundException("Address with id: " + id + " not found"));
        return addressMapper.toDto(address);
    }

    //getAll()
    //save(DTO dto)
    //delete(Long id)
    //update(Long id, DTO dto)

}
