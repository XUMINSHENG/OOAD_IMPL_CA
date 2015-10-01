/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.producer.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.producer.delegate.ProducerDelegate;
import sg.edu.nus.iss.phoenix.user.entity.Producer;

/**
 *
 * @author tanuj
 */
@Action("selectProducer")
public class SelectProducerCmd implements Perform {

    @Override
    public String perform(String string, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ProducerDelegate del = new ProducerDelegate();
        
        ArrayList<Producer> data = null;
        String name = req.getParameter("name");
        if (name != null && !name.isEmpty())
            data = del.FetchProducersByName(name);
        else 
            data = del.FetchProducers();
        req.setAttribute("name", name);
                

        req.setAttribute("selProducerList", data);
        return "/pages/selectProducer.jsp";
    }
    
}
