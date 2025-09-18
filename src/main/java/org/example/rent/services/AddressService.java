package org.example.rent.services;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.AddressDTO;
import org.example.rent.entity.Address;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.CustomLogger;
import org.example.rent.repositories.interfaces.AddressRepository;
import org.example.rent.services.mappers.AddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

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
        log.info("Get address with id: " + id);
        return addressMapper.toDto(address);
    }

    //getAll()
    public List<AddressDTO> getAll() {
        List<Address> addresses = addressRepository.findAll();
        List<AddressDTO> addressDTO = addresses.stream().map(addressMapper::toDto).collect(Collectors.toList());
        log.info("Get all addresses: " + addressDTO.size());
        return addressDTO;
    }

    //save(DTO dto)
    @Transactional
    public void save(AddressDTO dto) {
        Address entity = addressMapper.toEntity(dto);
        addressRepository.save(entity);
        log.info("Save address with id: " + entity.getId());
    }

    //delete(Long id)
    @Transactional
    public void deleteById(Long id) {
        if (!addressRepository.existsById(id))
            throw new NotFoundException("Address with id: " + id + " not found");
        addressRepository.deleteById(id);
        log.info("Delete address with id: " + id);
    }

    //update(Long id, DTO dto)
    @Transactional
    public void update(Long id, AddressDTO dto) {
        if (!addressRepository.existsById(id))
            throw new NotFoundException("Address with id: " + id + " not found");
        Address entity = addressMapper.toEntity(dto);
        entity.setId(id);
        addressRepository.save(entity);
        log.info("Update address with id: " + entity.getId());
    }
}
