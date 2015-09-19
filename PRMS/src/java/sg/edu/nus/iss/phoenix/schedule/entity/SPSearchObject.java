/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.entity;

/**
 *
 * @author xiejiabao
 */
public class SPSearchObject {
    private String year;
    private String week;

    public SPSearchObject() {
    }

    public SPSearchObject(String year, String week) {
        this.year = year;
        this.week = week;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
    
}
