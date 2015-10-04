package sg.edu.nus.iss.phoenix.schedule.entity;

import sg.edu.nus.iss.phoenix.radioprogram.entity.*;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class WeeklySchedule implements Cloneable, Serializable {

    /**
	 * eclipse identifier
	 */
	private static final long serialVersionUID = -5500218812568593553L;
	
	/** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private int year;
    private int week;
    private String assignedBy;
    private List<ProgramSlot> listOfProgramSlot;


    /** 
     * Constructors. 
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */

    public WeeklySchedule () {

    }

    public WeeklySchedule (int yearIn, int weekIn) {

        this.year = yearIn;
        this.week = weekIn;
    }


    /** 
     * Get- and Set-methods for persistent variables. The default
     * behaviour does not make any checks against malformed data,
     * so these might require some manual additions.
     */

    public int getYear() {
        return this.year;
    }
    
    public void setYear(int yearIn) {
        this.year = yearIn;
    }

    public int getWeek() {
        return this.week;
    }
    
    public void setWeek(int weekIn) {
        this.week = weekIn;
    }
    
    public String getAssignedBy() {
          return this.assignedBy;
    }
    public void setAssignedBy(String assignedByIn) {
          this.assignedBy = assignedByIn;
    }

    public List<ProgramSlot> getListOfProgramSlot() {
        return listOfProgramSlot;
    }

    public void setListOfProgramSlot(List<ProgramSlot> listOfProgramSlot) {
        this.listOfProgramSlot = listOfProgramSlot;
    }

    


    /** 
     * setAll allows to set all persistent variables in one method call.
     * This is useful, when all data is available and it is needed to 
     * set the initial state of this object. Note that this method will
     * directly modify instance variables, without going trough the 
     * individual set-methods.
     */

    public void setAll(int yearIn, int weekIn,
          String assignedByIn) {
          this.year = yearIn;
          this.week = weekIn;
          this.assignedBy = assignedByIn;
    }


    /** 
     * hasEqualMapping-method will compare two RadioProgram instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(WeeklySchedule valueObject) {

          if (this.year == 0) {
                    if (valueObject.getYear() != 0)
                           return(false);
          } else if (this.year != valueObject.getYear()) {
                    return(false);
          }
          if (this.week == 0) {
                    if (valueObject.getWeek() != 0)
                           return(false);
          } else if (this.week != valueObject.getWeek()) {
                    return(false);
          }
          if (this.assignedBy == null) {
                    if (valueObject.getAssignedBy() != null)
                           return(false);
          } else if (!this.assignedBy.equals(valueObject.getAssignedBy())) {
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
        out.append("\nRadioProgram class, mapping to table radio-program\n");
        out.append("Persistent attributes: \n"); 
        out.append("year = " + Integer.toString(this.year) + "\n"); 
        out.append("description = " + this.assignedBy + "\n"); 
        return out.toString();
    }


    /**
     * Clone will return identical deep copy of this valueObject.
     * Note, that this method is different than the clone() which
     * is defined in java.lang.Object. Here, the returned cloned object
     * will also have all its attributes cloned.
     */
    public Object clone() {
        WeeklySchedule cloned = new WeeklySchedule();

        if (this.year != 0)
             cloned.setYear(this.year);
        if (this.week != 0)
             cloned.setWeek(this.week);
        if (this.assignedBy != null)
             cloned.setAssignedBy(new String(this.assignedBy)); 
        
        return cloned;
    }

    public Date getStartDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.year);
        cal.set(Calendar.WEEK_OF_YEAR, this.week);
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DATE, 1);
        Date startOfWeek = new Date(cal.getTimeInMillis());
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd EEE");
        return startOfWeek;
//        return df.format(startOfWeek);
    }
    
    public Date getEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.year);
        cal.set(Calendar.WEEK_OF_YEAR, this.week);
       
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DATE, 1);
//        cal.add(Calendar.DATE,6);
        Date endOfWeek = new Date(cal.getTimeInMillis());
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd EEE");
        return endOfWeek;
//        return df.format(endOfWeek);
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final WeeklySchedule other = (WeeklySchedule) obj;
        if (this.year != other.year) {
            return false;
        }
        if (this.week != other.week) {
            return false;
        }
       
        return true;
    }

    
}