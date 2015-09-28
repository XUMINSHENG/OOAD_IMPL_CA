package sg.edu.nus.iss.phoenix.user.entity;

import java.io.Serializable;
import java.util.Objects;


public class Presenter implements Cloneable, Serializable {

    /**
    * eclipse identifier
    */
    private static final long serialVersionUID = -5500218812568593553L;
	
    /** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private String userId;
    private String name;
    private String isActive;


    /**
     * Get the value of userId
     *
     * @return the value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Set the value of userId
     *
     * @param userId new value of userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    

    /** 
     * Constructors. 
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */

    public Presenter () {

    }

    public Presenter (String nameIn) {

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
   

    /** 
     * setAll allows to set all persistent variables in one method call.
     * This is useful, when all data is available and it is needed to 
     * set the initial state of this object. Note that this method will
     * directly modify instance variables, without going trough the 
     * individual set-methods.
     */

    public void setName(String nameIn) {
          this.name = nameIn;
    }

    
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsActive() {
        return isActive;
    }

    /** 
     * hasEqualMapping-method will compare two RadioProgram instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(Presenter valueObject) {

          if (this.name == null) {
                    if (valueObject.getName() != null)
                           return(false);
          } else if (!this.name.equals(valueObject.getName())) {
                    return(false);
          }

          return true;
    }

    @Override
    public String toString() {
        return "Presenter{" + "userId=" + userId + ", name=" + name + '}';
    }


    /**
     * Clone will return identical deep copy of this valueObject.
     * Note, that this method is different than the clone() which
     * is defined in java.lang.Object. Here, the returned cloned object
     * will also have all its attributes cloned.
     */
    public Object clone() {
        Presenter cloned = new Presenter();

        if (this.name != null)
             cloned.setName(new String(this.name)); 
        if (this.userId != null)
             cloned.setName(new String(this.userId)); 
        return cloned;
    }
    
    /**
     * Hashcode will return a hashed code for the given object.
     * Used when inserting into a HashTable, HashMap or HashSet.
     */

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.userId);
        hash = 71 * hash + Objects.hashCode(this.name);
        return hash;
    }

    /**
     * Used to determine if an object contains a given element.
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Presenter other = (Presenter) obj;
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }



}