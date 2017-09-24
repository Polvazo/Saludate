package com.saludate.saludate.Models;

/**
 * Created by USUARIO on 17/09/2017.
 */

public class SpecialityDoctor {

    public Integer id;
    public Speciality speciality;
    public Doctor doctor;

    public SpecialityDoctor(Integer id, Speciality speciality, Doctor doctor) {
        this.id = id;
        this.speciality = speciality;
        this.doctor = doctor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return doctor.getPerson().getUser().getFirst_name()+" "+doctor.getPerson().getUser().getLast_name();
    }
}
