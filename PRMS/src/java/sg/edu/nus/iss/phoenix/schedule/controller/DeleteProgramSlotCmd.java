/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.controller;

import sg.edu.nus.iss.phoenix.radioprogram.controller.*;
import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.schedule.delegate.ReviewSelectScheduledProgramDelegate;
import sg.edu.nus.iss.phoenix.schedule.delegate.ScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.util.Util;

/**
 *
 * @author boonkui
 */
@Action("deleteps")
public class DeleteProgramSlotCmd implements Perform {
    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ScheduleDelegate del = new ScheduleDelegate();
        
        Date dateOfProgram;
        Time duration;
        try {
            dateOfProgram = Util.stringToDate(req.getParameter("dateOfProgram"));
            duration = Util.stringToTime(req.getParameter("duration"));
            
            del.processDelete(duration, dateOfProgram);
        } catch (ParseException ex) {
            Logger.getLogger(DeleteProgramSlotCmd.class.getName()).log(Level.SEVERE, null, ex);
        }

        ReviewSelectScheduledProgramDelegate rsDel = new ReviewSelectScheduledProgramDelegate();
        List<ProgramSlot> data = rsDel.reviewSelectScheduledProgram();
        req.setAttribute("pss", data);
        return "/pages/crudsc.jsp";
    }
}
