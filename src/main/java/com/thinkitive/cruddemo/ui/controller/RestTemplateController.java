package com.thinkitive.cruddemo.ui.controller;

import com.thinkitive.cruddemo.ui.request.PatientRequest;
import com.thinkitive.cruddemo.ui.resopnse.PatientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/template/patients")
@Slf4j
public class RestTemplateController {

    private RestTemplate restTemplate;

    @Value("${server.port}")
    private String port;

    public RestTemplateController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<PatientResponse[]> getAllPatients() {
        return restTemplate.exchange(getBaseUrl(),
                HttpMethod.GET,
                null,
                PatientResponse[].class);
    }

    @GetMapping(value = "/{patientId}")
    public PatientResponse getPatientByPatientId(@PathVariable("patientId") String patientId) {
        return restTemplate.getForObject(getBaseUrl() + "/" + patientId,
                PatientResponse.class);
    }

    @PostMapping
    public PatientResponse newPatient(@RequestBody PatientRequest patientRequest) {
        return restTemplate.postForObject(getBaseUrl(), patientRequest, PatientResponse.class);
    }

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/patients";
    }

}
