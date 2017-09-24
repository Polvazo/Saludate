package com.saludate.saludate.Models;

/**
 * Created by USUARIO on 17/09/2017.
 */

public class Doctor {

    public Integer id;
    public String school_medicine;
    public Person person;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchool_medicine() {
        return school_medicine;
    }

    public void setSchool_medicine(String school_medicine) {
        this.school_medicine = school_medicine;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
