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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.radioprogram.delegate.ReviewSelectProgramDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.delegate.ReviewSelectScheduledProgramDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 *
 * @
 */
@Action("managesc")
public class ManageScheduleCmd implements Perform {
    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ReviewSelectScheduledProgramDelegate del = new ReviewSelectScheduledProgramDelegate();
        List<ProgramSlot> data = del.reviewSelectScheduledProgram();
        req.setAttribute("pss", data);
        
        Object o = new SimpleDateFormat("w").format(new java.util.Date());
        System.out.println(o.toString());
        
        return "/pages/crudsc.jsp";
    }
}
