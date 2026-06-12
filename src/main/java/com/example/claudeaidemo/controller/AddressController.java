package com.example.claudeaidemo.controller;

import com.example.claudeaidemo.dto.AddressDto;
import com.example.claudeaidemo.dto.AddressRequest;
import com.example.claudeaidemo.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@Tag(name = "Addresses", description = "Address management")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    @Operation(summary = "Get all addresses")
    public List<AddressDto> getAll() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get address by ID")
    public AddressDto getById(@PathVariable Long id) {
        return addressService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new address")
    public AddressDto create(@Valid @RequestBody AddressRequest request) {
        return addressService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an address")
    public AddressDto update(@PathVariable Long id, @Valid @RequestBody AddressRequest request) {
        return addressService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an address")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
