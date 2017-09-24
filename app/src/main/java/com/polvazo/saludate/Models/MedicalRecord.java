package com.polvazo.saludate.Models;

import retrofit2.http.Path;

/**
 * Created by USUARIO on 23/09/2017.
 */

public class MedicalRecord {

    private Integer id;
    private Patient patient;
    private String blood_type;
    private Float weight;
    private Float height;
    private String harmful_habits;
    private String background;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getHarmful_habits() {
        return harmful_habits;
    }

    public void setHarmful_habits(String harmful_habits) {
        this.harmful_habits = harmful_habits;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
