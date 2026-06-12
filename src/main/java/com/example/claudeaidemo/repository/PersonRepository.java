package com.example.claudeaidemo.repository;

import com.example.claudeaidemo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByAddressId(Long addressId);
    boolean existsByFunctionId(Long functionId);
}
