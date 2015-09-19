package sg.edu.nus.iss.phoenix.schedule.entity;

import sg.edu.nus.iss.phoenix.radioprogram.entity.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.List;

public class AnnualSchedule implements Cloneable, Serializable {

    /**
	 * eclipse identifier
	 */
	private static final long serialVersionUID = -5500218812568593553L;
	
	/** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private int year;
    private String assignedBy;
    List<WeeklySchedule> listOfWeeklySchedule;


    /** 
     * Constructors. 
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */

    public AnnualSchedule () {

    }

    public AnnualSchedule (int yearIn) {

          this.year = yearIn;

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

    public String getAssignedBy() {
          return this.assignedBy;
    }
    public void setAssignedBy(String assignedByIn) {
          this.assignedBy = assignedByIn;
    }

    public List<WeeklySchedule> getListOfWeeklySchedule() {
        return listOfWeeklySchedule;
    }

    public void setListOfWeeklySchedule(List<WeeklySchedule> listOfWeeklySchedule) {
        this.listOfWeeklySchedule = listOfWeeklySchedule;
    }



    /** 
     * setAll allows to set all persistent variables in one method call.
     * This is useful, when all data is available and it is needed to 
     * set the initial state of this object. Note that this method will
     * directly modify instance variables, without going trough the 
     * individual set-methods.
     */

    public void setAll(int yearIn,
          String assignedByIn) {
          this.year = yearIn;
          this.assignedBy = assignedByIn;
    }


    /** 
     * hasEqualMapping-method will compare two RadioProgram instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(AnnualSchedule valueObject) {

          if (this.year == 0) {
                    if (valueObject.getYear() != 0)
                           return(false);
          } else if (this.year != valueObject.getYear()) {
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
        out.append("\nAnnualSchedule class, mapping to table annual-schedule\n");
        out.append("Persistent attributes: \n"); 
        out.append("year = " + this.year + "\n"); 
        out.append("assignedBy = " + this.assignedBy + "\n");
        return out.toString();
    }


    /**
     * Clone will return identical deep copy of this valueObject.
     * Note, that this method is different than the clone() which
     * is defined in java.lang.Object. Here, the returned cloned object
     * will also have all its attributes cloned.
     */
    public Object clone() {
        AnnualSchedule cloned = new AnnualSchedule();

        if (this.year != 0)
             cloned.setYear(this.year); 
        if (this.assignedBy != null)
             cloned.setAssignedBy(new String(this.assignedBy)); 
        return cloned;
    }



}