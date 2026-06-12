package com.example.claudeaidemo.controller;

import com.example.claudeaidemo.dto.JobFunctionDto;
import com.example.claudeaidemo.dto.JobFunctionRequest;
import com.example.claudeaidemo.service.JobFunctionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/functions")
@Tag(name = "Functions", description = "Job function management")
public class JobFunctionController {

    private final JobFunctionService jobFunctionService;

    public JobFunctionController(JobFunctionService jobFunctionService) {
        this.jobFunctionService = jobFunctionService;
    }

    @GetMapping
    @Operation(summary = "Get all job functions")
    public List<JobFunctionDto> getAll() {
        return jobFunctionService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get job function by ID")
    public JobFunctionDto getById(@PathVariable Long id) {
        return jobFunctionService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new job function")
    public JobFunctionDto create(@Valid @RequestBody JobFunctionRequest request) {
        return jobFunctionService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a job function")
    public JobFunctionDto update(@PathVariable Long id, @Valid @RequestBody JobFunctionRequest request) {
        return jobFunctionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a job function")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jobFunctionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
