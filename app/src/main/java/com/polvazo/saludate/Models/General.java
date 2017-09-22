package com.polvazo.saludate.Models;

/**
 * Created by USUARIO on 17/09/2017.
 */

public class General {

    public Integer id;
    public ScheduleDoctor schedule_doctor;
    public SpecialityDoctor speciality_doctor;
    public Patient patient;
    public String description;
    public String annotations;
    public String status;
    public Boolean is_modifiable;

    public General(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ScheduleDoctor getSchedule_doctor() {
        return schedule_doctor;
    }

    public void setSchedule_doctor(ScheduleDoctor schedule_doctor) {
        this.schedule_doctor = schedule_doctor;
    }

    public SpecialityDoctor getSpeciality_doctor() {
        return speciality_doctor;
    }

    public void setSpeciality_doctor(SpecialityDoctor speciality_doctor) {
        this.speciality_doctor = speciality_doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
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

    public Boolean getIs_modifiable() {
        return is_modifiable;
    }

    public void setIs_modifiable(Boolean is_modifiable) {
        this.is_modifiable = is_modifiable;
    }
}
