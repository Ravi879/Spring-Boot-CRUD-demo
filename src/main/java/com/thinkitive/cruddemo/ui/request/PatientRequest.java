package com.thinkitive.cruddemo.ui.request;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class PatientRequest {

    @NotBlank(message = "patient.name.not.empty")
    @Size(min = 2, max = 100, message = "patient.name.size")
    @Pattern(regexp = "^[A-Za-z]{2,15}$", message = "patient.name.invalid")
    private String name;

    @NotBlank(message = "patient.email.not.empty")
    @Size(min = 2, max = 100, message = "patient.email.size")
    @Pattern(regexp = "^([\\w-.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$", message = "patient.email.invalid")
    private String email;

    @NotBlank(message = "patient.disease.not.empty")
    @Size(min = 2, max = 100, message = "patient.disease.size")
    @Pattern(regexp = "^[A-Za-z0-9]{2,100}$", message = "patient.disease.invalid")
    private String disease;

    @NotNull(message = "patient.age.not.empty")
    @Range(min = 1, max = 100, message = "patient.age.size")
    private Integer age;

    @NotNull(message = "patient.date.not.empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
