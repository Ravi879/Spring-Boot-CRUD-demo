package com.thinkitive.cruddemo.ui.controller;

import com.thinkitive.cruddemo.service.PatientService;
import com.thinkitive.cruddemo.shared.dto.PatientDto;
import com.thinkitive.cruddemo.shared.mapper.PatientMapper;
import com.thinkitive.cruddemo.ui.request.PatientFilter;
import com.thinkitive.cruddemo.ui.request.PatientRequest;
import com.thinkitive.cruddemo.ui.resopnse.PatientResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patients")
@Validated
@Slf4j
public class PatientController {

    private final ModelMapper modelMapper;
    private final PatientService patientService;
    private final PatientMapper patientMapper;

    public PatientController(PatientService patientService, PatientMapper patientMapper) {
        this.patientService = patientService;
        this.patientMapper = patientMapper;
        modelMapper = new ModelMapper();
    }

    @GetMapping(value = "/{patientId}")
    public ResponseEntity<PatientResponse> getPatientByPatientId(@PathVariable("patientId") String patientId) {
        // get patient details
        PatientDto patientDto = patientService.getPatientByPatientId(patientId);
        log.info("GetPatient -- get.patient -- patientId={}", patientId);
        // map list of PatientDto -> PatientResponse
        // return new ResponseEntity<>(modelMapper.map(patientDto, PatientResponse.class), HttpStatus.OK);
        return new ResponseEntity<>(patientMapper.toPatientResponse(patientDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        // get all patients
        List<PatientDto> allPatients = patientService.getAllPatients();

        log.info("GetAllPatients -- get.patients -- ");
        // map list of PatientDto -> list of PatientResponse
        List<PatientResponse> response = patientMapper.toPatientResponses(allPatients);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = "age")
    public ResponseEntity<List<PatientResponse>> getPatientsByAge(@RequestParam("age") Integer age) {
        // get all patients by age
        List<PatientDto> patients = patientService.getPatientsByAge(age);

        log.info("GetPatientsByAge -- get.patients.by.age -- age={}", age);
        // map list of PatientDto -> list of PatientResponse
        List<PatientResponse> response = patientMapper.toPatientResponses(patients);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/modal")
    public ResponseEntity<List<PatientResponse>> getPatientsByAgeAndDisease(@ModelAttribute PatientFilter filter) {
        // get all patients by age and disease
        List<PatientDto> patients = patientService.getPatientsByAgeAndDisease(filter.getAge(), filter.getDisease());

        log.info("GetPatientsByAgeAndDisease -- get.patients.by.age.disease -- age={} disease={}", filter.getAge(), filter.getDisease());
        // map list of PatientDto -> list of PatientResponse
        List<PatientResponse> response = patientMapper.toPatientResponses(patients);
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
        PatientResponse response = patientMapper.toPatientResponse(storedPatientDto);
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
        PatientResponse response = patientMapper.toPatientResponse(storedPatientDto);
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
