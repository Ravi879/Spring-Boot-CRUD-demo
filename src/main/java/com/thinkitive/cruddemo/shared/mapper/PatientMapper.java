package com.thinkitive.cruddemo.shared.mapper;

import com.thinkitive.cruddemo.repository.entity.PatientEntity;
import com.thinkitive.cruddemo.shared.dto.PatientDto;
import com.thinkitive.cruddemo.ui.resopnse.PatientResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDto toPatientDTO(PatientEntity patientEntity);

    PatientEntity toPatientEntity(PatientEntity patientEntity);

    List<PatientDto> toPatientDTOs(List<PatientEntity> patients);

    PatientResponse toPatientResponse(PatientDto patientDto);

    List<PatientResponse> toPatientResponses(List<PatientDto> patients);

}
