package com.thinkitive.cruddemo.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "Patient")
@Table(name = "PATIENT")
public class PatientEntity implements Serializable {

    private static final long serialVersionUID = -1410555778645100595L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_sequence_generator")
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String patientId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 100)
    private String disease;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PatientEntity{" +
                "id=" + id +
                ", patientId='" + patientId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", disease='" + disease + '\'' +
                ", age=" + age +
                ", date=" + date +
                '}';
    }
}
