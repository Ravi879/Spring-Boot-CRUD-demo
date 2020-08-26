package com.thinkitive.cruddemo.ui.controller;

import com.thinkitive.cruddemo.service.PatientService;
import com.thinkitive.cruddemo.shared.dto.PatientDto;
import com.thinkitive.cruddemo.ui.request.PatientRequest;
import com.thinkitive.cruddemo.ui.resopnse.PatientResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@Validated
@Slf4j
public class PatientController {

    private final ModelMapper modelMapper;
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
        modelMapper = new ModelMapper();
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        // get all patients
        List<PatientDto> allPatients = patientService.getAllPatients();
        // type - for mapping list of DTOs to list of patient response
        Type type = new TypeToken<List<PatientResponse>>() {
        }.getType();
        log.info("GetAllPatients -- get.patients -- ");
        // map list of PatientDto -> list of PatientResponse
        List<PatientResponse> response = modelMapper.map(allPatients, type);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PatientResponse> newPatient(@RequestBody @Valid PatientRequest patientRequest) {
        // map to dto
        PatientDto patientDto = modelMapper.map(patientRequest, PatientDto.class);
        // insert to database
        PatientDto storedPatientDto = patientService.newPatient(patientDto);

        log.info("PatientAdded -- create" +
                ".patient -- patientId={}", storedPatientDto.getPatientId());

        // map to response object
        PatientResponse response = modelMapper.map(storedPatientDto, PatientResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientResponse> updateItem(@PathVariable String patientId,
                                                   @RequestBody @Valid PatientRequest patientRequest) {
        // map to dto
        PatientDto patientDto = modelMapper.map(patientRequest, PatientDto.class);
        patientDto.setPatientId(patientId);
        // update to item database
        PatientDto storedPatientDto = patientService.updateItem(patientDto);

        log.info("UpdatePatient -- update.patient -- patientId={}", storedPatientDto.getPatientId());

        // map to response object
        PatientResponse response = modelMapper.map(storedPatientDto, PatientResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deleteItem(@PathVariable String patientId) {
        // delete item from database
        patientService.deleteItem(patientId);

        log.info("DeletePatient -- delete.patient -- patientId={}", patientId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
