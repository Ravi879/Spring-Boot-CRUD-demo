package com.thinkitive.cruddemo.service;

import com.thinkitive.cruddemo.shared.dto.PatientDto;

import java.util.List;

public interface PatientService {

    List<PatientDto> getAllPatients();

    PatientDto newPatient(PatientDto patientDto);

    PatientDto updateItem(PatientDto patientDto);

    void deleteItem(String patientId);


}
