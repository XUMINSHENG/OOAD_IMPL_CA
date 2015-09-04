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
import sg.edu.nus.iss.phoenix.radioprogram.delegate.ProgramDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.delegate.ReviewSelectProgramDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RPSearchObject;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

/**
 *
 * @author boonkui
 */
@Action("searchrp")
public class SearchRadioProgramCmd implements Perform {
    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ProgramDelegate del = new ProgramDelegate();
        RPSearchObject rpso = new RPSearchObject();
        rpso.setName(req.getParameter("name"));
        rpso.setDescription(req.getParameter("description"));
        System.out.println(rpso.toString());
        
        ArrayList<RadioProgram> data = null;
        
        if ((rpso.getName() != null && !rpso.getName().isEmpty()) || 
                (rpso.getDescription()!= null && !rpso.getDescription().isEmpty()))
            data = del.findRPByCriteria(rpso);
        else 
            data = del.findAllRP();
                
        req.setAttribute("name", rpso.getName());
        req.setAttribute("description", rpso.getDescription());
        req.setAttribute("searchrplist", data);
        return "/pages/searchrp.jsp";
    }
}
