/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.schedule.delegate.ReviewSelectScheduledProgramDelegate;
import sg.edu.nus.iss.phoenix.schedule.delegate.ScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.util.Util;


/**
 * <p>This class represent the Action class that handles 
 * HttpServletRequest which is sent to PhoenixFrontController to delete a 
 * {@link ProgramSlot}.</p>
 * 
 * <p>The method <code>perform() </code> which implements the {@link Perform} 
 * Interface will be invoked when a request is sent to <strong>deleteps</strong>
 * URL </p>
 * 
 * @author Xu Minsheng
 * @version 1.0 2015/09/13
 */
@Action("deleteps")
public class DeleteProgramSlotCmd implements Perform {
    
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
        
        // Authorization Check
        User user = (User)req.getSession().getAttribute("user");
        if ((null==user)||!user.hasRole("manager")){
            req.setAttribute("errorMsg", 
                    "You do not have the privileges to perform this operation");
            return "/pages/error.jsp";
        }
        
        // collect data from requset
        Date dateOfProgram = null;
        Time startTime = null;
        int year;
        int weekNum;
        try {
            year = Integer.parseInt(req.getParameter("year"));
            weekNum = Integer.parseInt(req.getParameter("weekNum"));
            dateOfProgram = Util.stringToDate(req.getParameter("dateOfProgram"));
            startTime = Util.stringToTime(req.getParameter("startTime"));
        } catch (Exception ex) {
            Logger.getLogger(DeleteProgramSlotCmd.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("errorMsg", "Invalid input");
            return "/pages/error.jsp";
        }
        
        // execute deletion
        try {
            ScheduleDelegate del = new ScheduleDelegate();
            del.processDelete(year,weekNum,dateOfProgram, startTime);
        } catch (Exception ex) {
            Logger.getLogger(DeleteProgramSlotCmd.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("errorMsg", ex.getMessage());
            return "/pages/error.jsp";
        }
        
        // forward to managesc servlet to process 
        RequestDispatcher rd = req.getRequestDispatcher("managesc");
        rd.forward(req, resp);
        return "";
    }
}
