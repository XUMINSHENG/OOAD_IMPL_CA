/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.nocturne.core;

import java.util.*;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NocturneActionMap {

    private final Map<String, Class<?>> actions = new HashMap<>();

    public NocturneActionMap() {
    }
    
    public Class<?> get(final String pathName) {
        return (actions.get(pathName));
    }
    
    public Set<String> keySet() {
        return (actions.keySet());
    }

    public Map<String, Class<?>> getInstance() {
        return (actions);
    }
}
