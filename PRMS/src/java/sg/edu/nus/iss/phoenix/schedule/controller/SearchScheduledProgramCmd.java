/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.radioprogram.delegate.ProgramDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RPSearchObject;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.delegate.ScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.SPSearchObject;

/**
 * <p>This class represent the Action class that handles 
 * HttpServletRequest which is sent to PhoenixFrontController to review scheduled program slots 
 * {@link ProgramSlot}.</p>
 * 
 * <p>The method <code>perform() </code> which implements the {@link Perform} 
 * Interface will be invoked when a request is sent to <strong>searchsp</strong>
 * URL </p>
 *
 * @author xiejiabao
 * @version 1.0 2015/10/03
 */
@Action("searchsp")
public class SearchScheduledProgramCmd implements Perform {
    /**
     * 
     * @param path the path of of invoking this Action
     * @param req the HttpServletRequest that sent from browser in order to 
     *            transmit sufficient data to perform intended task
     * @param resp the HttpServletResponse will return process result back to 
     *             browser
     * @return the path of web page to display result
     * @throws IOException if an error has occurred in IO of System
     * @throws ServletException if an error has occurred in Servlet
     */
    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
 
        User user = (User)req.getSession().getAttribute("user");
        if ((null==user)||!user.hasRole("manager")){
            return "/pages/error.jsp";
        }
                
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
        req.setAttribute("searchsplist", data);
        return "/pages/searchsp.jsp";
    }
}
