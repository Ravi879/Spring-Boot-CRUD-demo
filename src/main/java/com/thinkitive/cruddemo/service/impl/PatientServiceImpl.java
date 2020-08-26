package com.thinkitive.cruddemo.service.impl;

import com.thinkitive.cruddemo.exception.ResourceAlreadyExistsException;
import com.thinkitive.cruddemo.exception.ResourceNotFoundException;
import com.thinkitive.cruddemo.repository.PatientRepository;
import com.thinkitive.cruddemo.repository.entity.PatientEntity;
import com.thinkitive.cruddemo.service.PatientService;
import com.thinkitive.cruddemo.shared.dto.PatientDto;
import com.thinkitive.cruddemo.shared.utils.RandomIdUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Supplier;

@Service
public class PatientServiceImpl implements PatientService {

    private final ModelMapper mapper;
    private final PatientRepository patientRepo;

    public PatientServiceImpl(PatientRepository patientRepo) {
        this.patientRepo = patientRepo;
        mapper = new ModelMapper();
    }

    @Override
    public List<PatientDto> getAllPatients() {
        List<PatientEntity> patientEntities = patientRepo.findAll();
        Type type = new TypeToken<List<PatientDto>>() {
        }.getType();
        return mapper.map(patientEntities, type);
    }

    @Override
    public PatientDto newPatient(PatientDto patientDto) {
        // check if patient is already registered or not
        if (patientRepo.findByEmail(patientDto.getEmail()).isPresent())
            throw new ResourceAlreadyExistsException("email.registered");

        // add random id to PatientDto
        patientDto.setPatientId(RandomIdUtils.getId());
        // map PatientDto -> PatientEntity
        PatientEntity patientEntity = mapper.map(patientDto, PatientEntity.class);
        // save to database
        PatientEntity storedPatientEntity = patientRepo.save(patientEntity);
        // map saved PatientEntity -> PatientDto
        return mapper.map(storedPatientEntity, PatientDto.class);
    }

    @Override
    public PatientDto updateItem(PatientDto patientDto) {
        // get patient by patientId -> update patient -> save back to database
        PatientEntity patientEntity = patientRepo.findByPatientId(patientDto.getPatientId())
                .orElseThrow(getInvalidIdException(patientDto.getPatientId()));
        updatePatient(patientEntity, patientDto);
        PatientEntity updatedPatientEntity = patientRepo.save(patientEntity);

        // map updated values to dto
        return mapper.map(updatedPatientEntity, PatientDto.class);
    }

    @Override
    public void deleteItem(String patientId) {
        // get patient by patientId -> delete patient if found Or else throw exception
        PatientEntity patientEntity = patientRepo.findByPatientId(patientId)
                .orElseThrow(getInvalidIdException(patientId));
        patientRepo.delete(patientEntity);
    }

    private void updatePatient(PatientEntity patientEntity, PatientDto patientDto) {
        patientEntity.setName(patientDto.getName());
        patientEntity.setEmail(patientDto.getEmail());
        patientEntity.setAge(patientDto.getAge());
        patientEntity.setDisease(patientDto.getDisease());
        patientEntity.setDate(patientDto.getDate());
    }

    private Supplier<RuntimeException> getInvalidIdException(String patientId) {
        return () -> new ResourceNotFoundException("invalid.patientId", "patientId=" + patientId);
    }


}
