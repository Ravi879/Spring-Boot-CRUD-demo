package com.thinkitive.cruddemo.ui.request;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PatientFilter {

    @NotBlank(message = "patient.disease.not.empty")
    @Size(min = 2, max = 100, message = "patient.disease.size")
    @Pattern(regexp = "^[A-Za-z0-9]{2,100}$", message = "patient.disease.invalid")
    private String disease;

    @NotNull(message = "patient.age.not.empty")
    @Range(min = 1, max = 100, message = "patient.age.size")
    private Integer age;


    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
