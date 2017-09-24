package com.saludate.saludate.Models;

/**
 * Created by USUARIO on 17/09/2017.
 */

public class Patient {
    public Integer id;
    public String civil_status;
    public Person person;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCivil_status() {
        return civil_status;
    }

    public void setCivil_status(String civil_status) {
        this.civil_status = civil_status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
