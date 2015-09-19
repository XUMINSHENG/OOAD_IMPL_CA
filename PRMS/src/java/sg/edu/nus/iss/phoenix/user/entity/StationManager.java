/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author achyut
 */
public class StationManager implements Cloneable,Serializable{
    
    private static final long serialVersionUID = -5500218812568593553L;
    
    private String userId;
    private String name;

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

    public StationManager() {
    }

    public StationManager(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StationManager{" + "userId=" + userId + ", name=" + name + '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
         StationManager cloned = new StationManager();

        if (this.name != null)
             cloned.setName(this.name); 
        if (this.userId != null)
             cloned.setName(this.userId); 
        return cloned;
    
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.userId);
        hash = 53 * hash + Objects.hashCode(this.name);
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
        final StationManager other = (StationManager) obj;
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    public boolean hasEqualMapping(StationManager valueObject) {

          if (this.name == null) {
                    if (valueObject.getName() != null)
                           return(false);
          } else if (!this.name.equals(valueObject.getName())) {
                    return(false);
          }

          return true;
    }
}
