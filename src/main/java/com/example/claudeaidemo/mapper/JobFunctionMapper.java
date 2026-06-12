package com.example.claudeaidemo.mapper;

import com.example.claudeaidemo.dto.JobFunctionDto;
import com.example.claudeaidemo.dto.JobFunctionRequest;
import com.example.claudeaidemo.entity.JobFunction;
import org.springframework.stereotype.Component;

@Component
public class JobFunctionMapper {

    public JobFunctionDto toDto(JobFunction jf) {
        return new JobFunctionDto(jf.getId(), jf.getLabel(), jf.getDescription());
    }

    public JobFunction toEntity(JobFunctionRequest request) {
        JobFunction jf = new JobFunction();
        jf.setLabel(request.label());
        jf.setDescription(request.description());
        return jf;
    }

    public void updateEntity(JobFunction jf, JobFunctionRequest request) {
        jf.setLabel(request.label());
        jf.setDescription(request.description());
    }
}
