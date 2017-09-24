package com.saludate.saludate.Models;

/**
 * Created by Luis on 05/05/2017.
 */

public class appointmentProcess {
    private Integer id;
    private Integer schedule_doctor;
    private Integer speciality_doctor;
    private Integer patient;
    private String description;
    private String annotations;
    private String status;

    public appointmentProcess(Integer schedule_doctor, Integer speciality_doctor, Integer patient, String description, String annotations, String status){
        this.schedule_doctor=schedule_doctor;
        this.speciality_doctor=speciality_doctor;
        this.patient=patient;
        this.description = description;
        this.annotations=annotations;
        this.status=status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSchedule_doctor() {
        return schedule_doctor;
    }

    public void setSchedule_doctor(Integer schedule_doctor) {
        this.schedule_doctor = schedule_doctor;
    }

    public Integer getSpeciality_doctor() {
        return speciality_doctor;
    }

    public void setSpeciality_doctor(Integer speciality_doctor) {
        this.speciality_doctor = speciality_doctor;
    }

    public Integer getPatient() {
        return patient;
    }

    public void setPatient(Integer patient) {
        this.patient = patient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  ", schedule_doctor=" + schedule_doctor +
                ", speciality_doctor=" + speciality_doctor +
                ", patient=" + patient +
                ", description='" + description + '\'' +
                ", annotations='" + annotations + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}