package com.thinkitive.cruddemo.repository;

import com.thinkitive.cruddemo.repository.entity.PatientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<PatientEntity, Long> {

    List<PatientEntity> findAll();

    List<PatientEntity> findAllByAge(Integer age);

    @Query("Select p from Patient p Where p.age = :age and p.disease = :disease ")
    List<PatientEntity> findAllByAgeAndDisease(Integer age, String disease);

    Optional<PatientEntity> findByEmail(String email);

    Optional<PatientEntity> findByPatientId(String patientId);

}
