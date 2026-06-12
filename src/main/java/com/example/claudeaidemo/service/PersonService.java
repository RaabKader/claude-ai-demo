package com.example.claudeaidemo.service;

import com.example.claudeaidemo.dto.PersonDto;
import com.example.claudeaidemo.dto.PersonRequest;
import com.example.claudeaidemo.entity.Address;
import com.example.claudeaidemo.entity.JobFunction;
import com.example.claudeaidemo.entity.Person;
import com.example.claudeaidemo.exception.ResourceNotFoundException;
import com.example.claudeaidemo.mapper.PersonMapper;
import com.example.claudeaidemo.repository.AddressRepository;
import com.example.claudeaidemo.repository.JobFunctionRepository;
import com.example.claudeaidemo.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final JobFunctionRepository jobFunctionRepository;
    private final PersonMapper personMapper;

    public PersonService(PersonRepository personRepository,
                         AddressRepository addressRepository,
                         JobFunctionRepository jobFunctionRepository,
                         PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.jobFunctionRepository = jobFunctionRepository;
        this.personMapper = personMapper;
    }

    @Transactional(readOnly = true)
    public List<PersonDto> findAll() {
        return personRepository.findAll().stream().map(personMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public PersonDto findById(Long id) {
        return personMapper.toDto(getOrThrow(id));
    }

    public PersonDto create(PersonRequest request) {
        Person person = new Person();
        applyRequest(person, request);
        return personMapper.toDto(personRepository.save(person));
    }

    public PersonDto update(Long id, PersonRequest request) {
        Person person = getOrThrow(id);
        applyRequest(person, request);
        return personMapper.toDto(personRepository.save(person));
    }

    public void delete(Long id) {
        getOrThrow(id);
        personRepository.deleteById(id);
    }

    private void applyRequest(Person person, PersonRequest request) {
        person.setFirstName(request.firstName());
        person.setLastName(request.lastName());
        person.setEmail(request.email());
        person.setPhone(request.phone());

        Address address = addressRepository.findById(request.addressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address", request.addressId()));
        JobFunction function = jobFunctionRepository.findById(request.functionId())
                .orElseThrow(() -> new ResourceNotFoundException("JobFunction", request.functionId()));

        person.setAddress(address);
        person.setFunction(function);
    }

    private Person getOrThrow(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person", id));
    }
}
