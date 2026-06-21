package com.example.claudeaidemo.repository;

import com.example.claudeaidemo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
    boolean existsByAddressId(Long addressId);
    boolean existsByFunctionId(Long functionId);
}
