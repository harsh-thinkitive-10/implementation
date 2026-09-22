package com.spring.implementation.dto.specification;

import com.spring.implementation.dto.AppointmentFilterDTO;
import com.spring.implementation.entity.AppointmentEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AppointmentSpecification {

    public static Specification<AppointmentEntity> filter(AppointmentFilterDTO filter) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getStatus() != null) predicates.add(cb.equal(root.get("status"), filter.getStatus()));

            if (filter.getPatientUuid() != null)
                predicates.add(cb.equal(root.get("patient").get("uuid"), filter.getPatientUuid()));

            if (filter.getDoctorUuid() != null)
                predicates.add(cb.equal(root.get("doctor").get("uuid"), filter.getDoctorUuid()));

            if (filter.getLocationUuid() != null)
                predicates.add(cb.equal(root.get("location").get("uuid"), filter.getLocationUuid()));

            if (filter.getFromDate() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("appointmentDate"), filter.getFromDate()));

            if (filter.getToDate() != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("appointmentDate"), filter.getToDate()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}