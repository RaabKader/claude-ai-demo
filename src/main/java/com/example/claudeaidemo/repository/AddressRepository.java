package com.example.claudeaidemo.repository;

import com.example.claudeaidemo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
