package com.example.claudeaidemo.repository;

import com.example.claudeaidemo.dto.PersonSearchCriteria;
import com.example.claudeaidemo.entity.Person;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds a {@link Specification} from {@link PersonSearchCriteria}. Each non-blank
 * criterion contributes a case-insensitive {@code LIKE %value%} predicate; predicates
 * are combined with {@code AND}.
 */
public final class PersonSpecifications {

    private PersonSpecifications() {}

    public static Specification<Person> matching(PersonSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            like(predicates, cb, root.get("firstName"), criteria.firstName());
            like(predicates, cb, root.get("lastName"), criteria.lastName());
            like(predicates, cb, root.get("email"), criteria.email());
            like(predicates, cb, root.get("phone"), criteria.phone());

            boolean needsAddress = anyNotBlank(criteria.street(), criteria.city(), criteria.zipCode(), criteria.country());
            if (needsAddress) {
                Join<Object, Object> address = root.join("address", JoinType.LEFT);
                like(predicates, cb, address.get("street"), criteria.street());
                like(predicates, cb, address.get("city"), criteria.city());
                like(predicates, cb, address.get("zipCode"), criteria.zipCode());
                like(predicates, cb, address.get("country"), criteria.country());
            }

            if (StringUtils.hasText(criteria.functionLabel())) {
                Join<Object, Object> function = root.join("function", JoinType.LEFT);
                like(predicates, cb, function.get("label"), criteria.functionLabel());
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

private static void like(List<Predicate> predicates,
                             jakarta.persistence.criteria.CriteriaBuilder cb,
                             jakarta.persistence.criteria.Expression<String> path,
                             String value) {
        if (StringUtils.hasText(value)) {
            predicates.add(cb.like(cb.lower(path), "%" + value.trim().toLowerCase() + "%"));
        }
    }

    private static boolean anyNotBlank(String... values) {
        for (String value : values) {
            if (StringUtils.hasText(value)) {
                return true;
            }
        }
        return false;
    }
}
