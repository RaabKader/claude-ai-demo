package com.example.claudeaidemo.service;

import com.example.claudeaidemo.dto.JobFunctionDto;
import com.example.claudeaidemo.dto.JobFunctionRequest;
import com.example.claudeaidemo.entity.JobFunction;
import com.example.claudeaidemo.exception.ReferentialIntegrityException;
import com.example.claudeaidemo.exception.ResourceNotFoundException;
import com.example.claudeaidemo.mapper.JobFunctionMapper;
import com.example.claudeaidemo.repository.JobFunctionRepository;
import com.example.claudeaidemo.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobFunctionService {

    private final JobFunctionRepository jobFunctionRepository;
    private final PersonRepository personRepository;
    private final JobFunctionMapper jobFunctionMapper;

    public JobFunctionService(JobFunctionRepository jobFunctionRepository, PersonRepository personRepository, JobFunctionMapper jobFunctionMapper) {
        this.jobFunctionRepository = jobFunctionRepository;
        this.personRepository = personRepository;
        this.jobFunctionMapper = jobFunctionMapper;
    }

    @Transactional(readOnly = true)
    public List<JobFunctionDto> findAll() {
        return jobFunctionRepository.findAll().stream().map(jobFunctionMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public JobFunctionDto findById(Long id) {
        return jobFunctionMapper.toDto(getOrThrow(id));
    }

    public JobFunctionDto create(JobFunctionRequest request) {
        JobFunction saved = jobFunctionRepository.save(jobFunctionMapper.toEntity(request));
        return jobFunctionMapper.toDto(saved);
    }

    public JobFunctionDto update(Long id, JobFunctionRequest request) {
        JobFunction jf = getOrThrow(id);
        jobFunctionMapper.updateEntity(jf, request);
        return jobFunctionMapper.toDto(jobFunctionRepository.save(jf));
    }

    public void delete(Long id) {
        getOrThrow(id);
        if (personRepository.existsByFunctionId(id)) {
            throw new ReferentialIntegrityException("Cannot delete function: it is referenced by one or more persons");
        }
        jobFunctionRepository.deleteById(id);
    }

    private JobFunction getOrThrow(Long id) {
        return jobFunctionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobFunction", id));
    }
}
