package sg.edu.nus.iss.phoenix.schedule.entity;

import sg.edu.nus.iss.phoenix.radioprogram.entity.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;
import sg.edu.nus.iss.phoenix.user.entity.Producer;

public class ProgramSlot implements Cloneable, Serializable {

    /**
    * eclipse identifier
    */
    private static final long serialVersionUID = -5500218812568593553L;
	
    /** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private Time duration;
    private Date dateOfProgram;
    private Date startTime;
    private RadioProgram program;
    private Presenter persenter;
    private Producer producer;

    /** 
     * Constructors. 
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */

    public ProgramSlot () {

    }

    public ProgramSlot (Time duration, Date dateOfProgram) {
        this.duration = duration;
        this.dateOfProgram = dateOfProgram;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Date getDateOfProgram() {
        return dateOfProgram;
    }

    public void setDateOfProgram(Date dateOfProgram) {
        this.dateOfProgram = dateOfProgram;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public RadioProgram getProgram() {
        return program;
    }

    public void setProgram(RadioProgram program) {
        this.program = program;
    }

    public Presenter getPersenter() {
        return persenter;
    }

    public void setPersenter(Presenter persenter) {
        this.persenter = persenter;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public void setAll(Time duration, Date dateOfProgram, Date startTime, RadioProgram program, Presenter persenter, Producer producer) {
        this.duration = duration;
        this.dateOfProgram = dateOfProgram;
        this.startTime = startTime;
        this.program = program;
        this.persenter = persenter;
        this.producer = producer;
    }
    
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }





}