package sg.edu.nus.iss.phoenix.radioprogram.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Objects;

public class RadioProgram implements Cloneable, Serializable {

    /**
	 * eclipse identifier
	 */
	private static final long serialVersionUID = -5500218812568593553L;
	
	/** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private String name;
    private String description;
    private Time typicalDuration;



    /** 
     * Constructors. 
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */

    public RadioProgram () {

    }

    public RadioProgram (String nameIn) {

          this.name = nameIn;

    }


    /** 
     * Get- and Set-methods for persistent variables. The default
     * behaviour does not make any checks against malformed data,
     * so these might require some manual additions.
     */

    public String getName() {
          return this.name;
    }
    public void setName(String nameIn) {
          this.name = nameIn;
    }

    public String getDescription() {
          return this.description;
    }
    public void setDescription(String descriptionIn) {
          this.description = descriptionIn;
    }

    public Time getTypicalDuration() {
          return this.typicalDuration;
    }
    public void setTypicalDuration(Time typicalDurationIn) {
          this.typicalDuration = typicalDurationIn;
    }



    /** 
     * setAll allows to set all persistent variables in one method call.
     * This is useful, when all data is available and it is needed to 
     * set the initial state of this object. Note that this method will
     * directly modify instance variables, without going trough the 
     * individual set-methods.
     */

    public void setAll(String nameIn,
          String descriptionIn,
          Time typicalDurationIn) {
          this.name = nameIn;
          this.description = descriptionIn;
          this.typicalDuration = typicalDurationIn;
    }


    /** 
     * hasEqualMapping-method will compare two RadioProgram instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(RadioProgram valueObject) {

          if (this.name == null) {
                    if (valueObject.getName() != null)
                           return(false);
          } else if (!this.name.equals(valueObject.getName())) {
                    return(false);
          }
          if (this.description == null) {
                    if (valueObject.getDescription() != null)
                           return(false);
          } else if (!this.description.equals(valueObject.getDescription())) {
                    return(false);
          }
          if (this.typicalDuration == null) {
                    if (valueObject.getTypicalDuration() != null)
                           return(false);
          } else if (!this.typicalDuration.equals(valueObject.getTypicalDuration())) {
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
        out.append("name = " + this.name + "\n"); 
        out.append("description = " + this.description + "\n"); 
        out.append("typicalDuration = " + this.typicalDuration + "\n"); 
        return out.toString();
    }


    /**
     * Clone will return identical deep copy of this valueObject.
     * Note, that this method is different than the clone() which
     * is defined in java.lang.Object. Here, the returned cloned object
     * will also have all its attributes cloned.
     */
    public Object clone() {
        RadioProgram cloned = new RadioProgram();

        if (this.name != null)
             cloned.setName(new String(this.name)); 
        if (this.description != null)
             cloned.setDescription(new String(this.description)); 
        if (this.typicalDuration != null)
             cloned.setTypicalDuration((Time)this.typicalDuration.clone()); 
        return cloned;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.description);
        hash = 17 * hash + Objects.hashCode(this.typicalDuration);
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
        final RadioProgram other = (RadioProgram) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.typicalDuration, other.typicalDuration)) {
            return false;
        }
        return true;
    }



}