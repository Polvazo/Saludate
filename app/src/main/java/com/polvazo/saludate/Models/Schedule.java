package com.polvazo.saludate.Models;

/**
 * Created by USUARIO on 17/09/2017.
 */

public class Schedule {
    public Integer id;
    public String start_hour;
    public String finish_hour;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    public String getFinish_hour() {
        return finish_hour;
    }

    public void setFinish_hour(String finish_hour) {
        this.finish_hour = finish_hour;
    }
}
