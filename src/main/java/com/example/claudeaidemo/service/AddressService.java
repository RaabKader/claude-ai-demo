package com.example.claudeaidemo.service;

import com.example.claudeaidemo.dto.AddressDto;
import com.example.claudeaidemo.dto.AddressRequest;
import com.example.claudeaidemo.entity.Address;
import com.example.claudeaidemo.exception.ReferentialIntegrityException;
import com.example.claudeaidemo.exception.ResourceNotFoundException;
import com.example.claudeaidemo.mapper.AddressMapper;
import com.example.claudeaidemo.repository.AddressRepository;
import com.example.claudeaidemo.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;
    private final PersonRepository personRepository;
    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, PersonRepository personRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.personRepository = personRepository;
        this.addressMapper = addressMapper;
    }

    @Transactional(readOnly = true)
    public List<AddressDto> findAll() {
        return addressRepository.findAll().stream().map(addressMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public AddressDto findById(Long id) {
        return addressMapper.toDto(getOrThrow(id));
    }

    public AddressDto create(AddressRequest request) {
        Address saved = addressRepository.save(addressMapper.toEntity(request));
        return addressMapper.toDto(saved);
    }

    public AddressDto update(Long id, AddressRequest request) {
        Address address = getOrThrow(id);
        addressMapper.updateEntity(address, request);
        return addressMapper.toDto(addressRepository.save(address));
    }

    public void delete(Long id) {
        getOrThrow(id);
        if (personRepository.existsByAddressId(id)) {
            throw new ReferentialIntegrityException("Cannot delete address: it is referenced by one or more persons");
        }
        addressRepository.deleteById(id);
    }

    private Address getOrThrow(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", id));
    }
}
