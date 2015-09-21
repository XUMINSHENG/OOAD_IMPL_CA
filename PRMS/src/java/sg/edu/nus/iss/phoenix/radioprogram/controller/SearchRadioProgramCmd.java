/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.radioprogram.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.schedule.delegate.ScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.SPSearchObject;

/**
 *
 * @author boonkui
 */
@Action("searchrp")
public class SearchRadioProgramCmd implements Perform {
    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ScheduleDelegate del = new ScheduleDelegate();
        SPSearchObject rpso = new SPSearchObject();
        rpso.setName(req.getParameter("name"));
        
        System.out.println(rpso.toString());
        
        ArrayList<ProgramSlot> data = null;
        
        if (rpso.getName() != null && !rpso.getName().isEmpty())
            data = del.findSPByCriteria(rpso);
        else 
            data = del.findAllSP();
                
        req.setAttribute("name", rpso.getName());
        req.setAttribute("searchrplist", data);
        return "/pages/searchrp.jsp";
    }
}
