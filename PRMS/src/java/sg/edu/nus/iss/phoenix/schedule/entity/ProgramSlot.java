package sg.edu.nus.iss.phoenix.schedule.entity;

import sg.edu.nus.iss.phoenix.radioprogram.entity.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;
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
    private int year;
    private int weekNum;
    private Date dateOfProgram;
    private Time startTime;
    private Time duration;
    private RadioProgram program;
    private Producer producer;
    private Presenter presenter;
    
    /** 
     * Constructors. 
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */

    public ProgramSlot () {

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public ProgramSlot (Date dateOfProgram, Time startTime) {
        this.dateOfProgram = dateOfProgram;
        this.startTime = startTime;
    }

    public ProgramSlot (int year, int weekNum, Date dateOfProgram, Time startTime) {
        this.year = year;
        this.weekNum = weekNum;
        this.dateOfProgram = dateOfProgram;
        this.startTime = startTime;
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

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public RadioProgram getProgram() {
        return program;
    }

    public void setProgram(RadioProgram program) {
        this.program = program;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public void setAll(Date dateOfProgram, Time startTime, Time duration, RadioProgram program, Presenter presenter, Producer producer) {
        this.dateOfProgram = dateOfProgram;
        this.startTime = startTime;
        this.duration = duration;
        this.program = program;
        this.presenter = presenter;
        this.producer = producer;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.dateOfProgram);
        hash = 23 * hash + Objects.hashCode(this.startTime);
        hash = 23 * hash + Objects.hashCode(this.duration);
        hash = 23 * hash + Objects.hashCode(this.program);
        hash = 23 * hash + Objects.hashCode(this.producer);
        hash = 23 * hash + Objects.hashCode(this.presenter);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProgramSlot other = (ProgramSlot) obj;
        if (!Objects.equals(this.year, other.year)) {
            return false;
        }
        if (!Objects.equals(this.weekNum, other.weekNum)) {
            return false;
        }
        if (!Objects.equals(this.dateOfProgram, other.dateOfProgram)) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.duration, other.duration)) {
            return false;
        }
        if (!Objects.equals(this.program, other.program)) {
            return false;
        }
        if (!Objects.equals(this.producer, other.producer)) {
            return false;
        }
        if (!Objects.equals(this.presenter, other.presenter)) {
            return false;
        }
        return true;
    }
    
    /** 
     * hasEqualMapping-method will compare two RadioProgram instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(ProgramSlot valueObject) {

          if (this.dateOfProgram == null) {
                    if (valueObject.getDateOfProgram()!= null)
                           return(false);
          } else if (!this.dateOfProgram.equals(valueObject.getDateOfProgram())) {
                    return(false);
          }
          
          if (this.startTime == null) {
                    if (valueObject.getStartTime()!= null)
                           return(false);
          } else if (!this.startTime.equals(valueObject.getStartTime())) {
                    return(false);
          }
          
          if (this.duration == null) {
                    if (valueObject.getDateOfProgram()!= null)
                           return(false);
          } else if (!this.duration.equals(valueObject.getDateOfProgram())) {
                    return(false);
          }
          
          if (this.program == null) {
                    if (valueObject.getProgram()!= null)
                           return(false);
          } else if (!this.program.equals(valueObject.getProgram())) {
                    return(false);
          }
          
          if (this.producer == null) {
                    if (valueObject.getProducer()!= null)
                           return(false);
          } else if (!this.producer.equals(valueObject.getProducer())) {
                    return(false);
          }
          
          if (this.presenter == null) {
                    if (valueObject.getPresenter()!= null)
                           return(false);
          } else if (!this.presenter.equals(valueObject.getPresenter())) {
                    return(false);
          }

          return true;
    }


    /**
     * toString will return String object representing the state of this 
     * valueObject. This is useful during application development, and 
     * possibly when application is writing object states in text log.
     */
    public String toString() {
        StringBuffer out = new StringBuffer();
        out.append("\nProgramSlot class, mapping to table program-slot\n");
        out.append("Persistent attributes: \n"); 
        out.append("dateOfProgram = " + this.dateOfProgram + "\n"); 
        out.append("startTime = " + this.startTime + "\n"); 
        out.append("duration = " + this.duration + "\n"); 
        out.append("program = " + this.program + "\n"); 
        out.append("presenter = " + this.presenter + "\n"); 
        out.append("producer = " + this.producer + "\n"); 
        return out.toString();
    }

    /**
     * Clone will return identical deep copy of this valueObject.
     * Note, that this method is different than the clone() which
     * is defined in java.lang.Object. Here, the returned cloned object
     * will also have all its attributes cloned.
     */
    public Object clone() {
        ProgramSlot cloned = new ProgramSlot();

        if (this.dateOfProgram != null)
             cloned.setDateOfProgram((Date)this.dateOfProgram.clone()); 
        if (this.startTime != null)
             cloned.setStartTime((Time)this.startTime.clone()); 
        if (this.duration != null)
             cloned.setDuration((Time)this.duration.clone()); 
        if (this.program != null)
             cloned.setProgram((RadioProgram)this.program.clone()); 
        if (this.producer != null)
             cloned.setProducer((Producer)this.producer.clone()); 
        if (this.presenter != null)
             cloned.setPresenter((Presenter)this.presenter.clone()); 
        return cloned;
    }


}