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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.schedule.delegate.ReviewSelectScheduledProgramDelegate;
import sg.edu.nus.iss.phoenix.schedule.delegate.ScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.util.Util;

/**
 *
 * @author 
 */
@Action("deleteps")
public class DeleteProgramSlotCmd implements Perform {
    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        
        // Authorization Check
        User user = (User)req.getSession().getAttribute("user");
        if ((null==user)||!user.hasRole("manager")){
            req.setAttribute("errorMsg", "You do not have the privileges to perform this operation");
            return "/pages/error.jsp";
        }
        
        // collect data from requset
        Date dateOfProgram = null;
        Time startTime = null;
        try {
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
            del.processDelete(dateOfProgram, startTime);
        } catch (Exception ex) {
            Logger.getLogger(DeleteProgramSlotCmd.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("errorMsg", ex.getMessage());
            return "/pages/error.jsp";
        }
        
        // forward
        ReviewSelectScheduledProgramDelegate rsDel = new ReviewSelectScheduledProgramDelegate();
        List<ProgramSlot> data = rsDel.reviewSelectScheduledProgram();
        req.setAttribute("pss", data);
        return "/pages/crudsc.jsp";
    }
}
