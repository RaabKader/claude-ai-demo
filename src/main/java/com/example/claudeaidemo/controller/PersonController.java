package com.example.claudeaidemo.controller;

import com.example.claudeaidemo.dto.PersonDto;
import com.example.claudeaidemo.dto.PersonRequest;
import com.example.claudeaidemo.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/persons")
@Tag(name = "Persons", description = "Person management")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @Operation(summary = "Get all persons")
    public List<PersonDto> getAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get person by ID")
    public PersonDto getById(@PathVariable Long id) {
        return personService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new person")
    public PersonDto create(@Valid @RequestBody PersonRequest request) {
        return personService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a person")
    public PersonDto update(@PathVariable Long id, @Valid @RequestBody PersonRequest request) {
        return personService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a person")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
