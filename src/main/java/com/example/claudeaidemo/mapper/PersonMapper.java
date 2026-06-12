package com.example.claudeaidemo.mapper;

import com.example.claudeaidemo.dto.PersonDto;
import com.example.claudeaidemo.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    private final AddressMapper addressMapper;
    private final JobFunctionMapper jobFunctionMapper;

    public PersonMapper(AddressMapper addressMapper, JobFunctionMapper jobFunctionMapper) {
        this.addressMapper = addressMapper;
        this.jobFunctionMapper = jobFunctionMapper;
    }

    public PersonDto toDto(Person person) {
        return new PersonDto(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getEmail(),
                person.getPhone(),
                addressMapper.toDto(person.getAddress()),
                jobFunctionMapper.toDto(person.getFunction())
        );
    }
}
