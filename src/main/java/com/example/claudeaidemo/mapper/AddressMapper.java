package com.example.claudeaidemo.mapper;

import com.example.claudeaidemo.dto.AddressDto;
import com.example.claudeaidemo.dto.AddressRequest;
import com.example.claudeaidemo.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressDto toDto(Address address) {
        return new AddressDto(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getZipCode(),
                address.getCountry()
        );
    }

    public Address toEntity(AddressRequest request) {
        Address address = new Address();
        address.setStreet(request.street());
        address.setCity(request.city());
        address.setZipCode(request.zipCode());
        address.setCountry(request.country());
        return address;
    }

    public void updateEntity(Address address, AddressRequest request) {
        address.setStreet(request.street());
        address.setCity(request.city());
        address.setZipCode(request.zipCode());
        address.setCountry(request.country());
    }
}
