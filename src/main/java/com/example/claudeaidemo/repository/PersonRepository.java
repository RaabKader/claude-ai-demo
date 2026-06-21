package com.example.claudeaidemo.repository;

import com.example.claudeaidemo.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.jspecify.annotations.Nullable;

public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
    boolean existsByAddressId(Long addressId);
    boolean existsByFunctionId(Long functionId);

    /**
     * Overrides {@link JpaSpecificationExecutor#findAll(Specification, Pageable)} to eagerly
     * fetch the to-one {@code address} and {@code function} associations in the data query,
     * avoiding the N+1 that {@code PersonMapper} would otherwise trigger when mapping each row.
     * Both are {@code @ManyToOne}, so the fetch joins are applied in SQL without forcing
     * in-memory pagination; the separate count query ignores the entity graph.
     */
    @Override
    @EntityGraph(attributePaths = {"address", "function"})
    Page<Person> findAll(@Nullable Specification<Person> spec, Pageable pageable);
}
