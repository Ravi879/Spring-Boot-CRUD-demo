package com.thinkitive.cruddemo.service;

import com.thinkitive.cruddemo.shared.dto.PatientDto;

import java.util.List;

public interface PatientService {

    PatientDto getPatientByPatientId(String patientId);

    List<PatientDto> getAllPatients();

    List<PatientDto> getPatientsByAge(Integer age);

    List<PatientDto> getPatientsByAgeAndDisease(Integer age, String disease);

    PatientDto newPatient(PatientDto patientDto);

    PatientDto updateItem(PatientDto patientDto);

    void deleteItem(String patientId);

}
