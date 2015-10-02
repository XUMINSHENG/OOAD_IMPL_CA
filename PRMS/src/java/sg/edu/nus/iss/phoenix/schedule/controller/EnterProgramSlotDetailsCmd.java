/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.delegate.ScheduleDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.delegate.ReviewSelectProgramDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.user.entity.*;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.delegate.ReviewSelectScheduledProgramDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.util.Util;
import sg.edu.nus.iss.phoenix.radioprogram.delegate.ProgramDelegate;
import sg.edu.nus.iss.phoenix.presenter.delegate.PresenterDelegate;
import sg.edu.nus.iss.phoenix.producer.delegate.ProducerDelegate;

/**
 *
 * @author Liu xinzhuo
 */
@Action("enterps")
public class EnterProgramSlotDetailsCmd implements Perform 
{
    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException 
    {   
        ScheduleDelegate sdel = new ScheduleDelegate();
        ProgramDelegate pdel = new ProgramDelegate();
        
        ProgramSlot ps = new ProgramSlot();
        Date date;
        Calendar cal = Calendar.getInstance();
        try {
            date = Util.stringToDate(req.getParameter("dateOfProgram"));
            cal.setTime(date);
            ps.setYear(cal.get(Calendar.YEAR));
            ps.setDateOfProgram(date);
            ps.setWeekNum(cal.get(Calendar.WEEK_OF_YEAR));
            ps.setStartTime(Util.stringToTime(req.getParameter("startTime")));
        } catch (ParseException ex) {
            Logger.getLogger(EnterProgramSlotDetailsCmd.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        RadioProgram trp = pdel.findRP(req.getParameter("name"));
        ps.setProgram(trp);
        
        String proName = req.getParameter("producer");
        Producer producer = new Producer();
        producer.setName(proName);
        ps.setProducer(producer);
        
        String preName = req.getParameter("presenter");
        Presenter presenter = new Presenter();
        presenter.setName(preName);
        ps.setPresenter(presenter);
        
        ps.setDuration(ps.getProgram().getTypicalDuration());
        
        String ins = (String) req.getParameter("ins");
        Logger.getLogger(getClass().getName()).log(Level.INFO,
                        "Insert Flag: " + ins);
        if (ins.equalsIgnoreCase("true")) {
            try {
                sdel.processCreate(ps);
            } catch (SQLException ex) {
                Logger.getLogger(EnterProgramSlotDetailsCmd.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                sdel.processModify(ps);
            } catch (NotFoundException | SQLException ex) {
                Logger.getLogger(EnterProgramSlotDetailsCmd.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
           int year = ps.getYear();
           int week = ps.getWeekNum();  
            // forward to managesc servlet to process 
            req.setAttribute("year",year);
            req.setAttribute("current_week", week);
            RequestDispatcher rd = req.getRequestDispatcher("managesc");
            rd.forward(req, resp);
            return "";        
           
        }
}
