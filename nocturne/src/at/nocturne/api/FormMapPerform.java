/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.nocturne.api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cmlee
 */
public class FormMapPerform implements Perform {
    
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String path;

    @Override
    public String perform(String p, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        request = req;
        response = resp;
        this.path = p;
        
        for (Method m: this.getClass().getDeclaredMethods()) {
            if (null == m.getAnnotation(Invoke.class))
               continue;
            
            Parameter[] parameters = m.getParameters();
            Object[] paramValues = new Object[m.getParameterCount()];
            for (int i = 0; i < parameters.length; i++) {
                Field f = parameters[i].getAnnotation(Field.class);
                if (null != f)
                    paramValues[i] = req.getParameter(f.value());                                   
            }
            try {
                return (m.invoke(this, paramValues).toString());
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                ex.printStackTrace();
                request.setAttribute("invoke_method", m.getName());
                request.setAttribute("invoke_class", this.getClass().getName());
                request.setAttribute("invoke_exception", ex.getMessage());
            }
        }
        return ("/invoke_error.jsp");
    }
    
    protected void setResult(String name, Object value) {
        request.setAttribute(name, value);
    }
    
}
