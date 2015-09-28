package sg.edu.nus.iss.phoenix.user.entity;

import java.io.Serializable;
import java.util.Objects;


public class Producer implements Cloneable, Serializable {

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
     * Constructors. 
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */

    public Producer () {

    }

    public Producer (String nameIn) {

          this.name = nameIn;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getIsActive() {
        return isActive;
    }
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Producer{" + "userId=" + userId + ", name=" + name + '}';
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
        final Producer other = (Producer) obj;
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Clone will return identical deep copy of this valueObject.
     * Note, that this method is different than the clone() which
     * is defined in java.lang.Object. Here, the returned cloned object
     * will also have all its attributes cloned.
     */
    public Object clone() {
        Producer cloned = new Producer();

        if (this.name != null)
             cloned.setName(this.name);
        if (this.userId != null)
             cloned.setName(this.userId);
        
        return cloned;
    }


}