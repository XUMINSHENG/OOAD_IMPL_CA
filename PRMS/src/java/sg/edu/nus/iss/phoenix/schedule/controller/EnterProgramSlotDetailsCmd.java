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
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author boonkui
 */
@Action("enterps")
public class EnterProgramSlotDetailsCmd implements Perform 
{
    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException 
    {
        if (req.getParameter("Submit").equals("Submit"))
        {     
        ScheduleDelegate del = new ScheduleDelegate();
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

        RadioProgram trp = new RadioProgram();
        trp.setName("news");
        trp.setTypicalDuration(Util.stringToTime("00:30:00"));
        ps.setProgram(trp);
        
        Presenter presenter = new Presenter();
        presenter.setName("dilbert, the hero");
        ps.setPresenter(presenter);
        
        Producer producer = new Producer();
        producer.setName("dogbert, the CEO");
        ps.setProducer(producer);
        ps.setDuration(ps.getProgram().getTypicalDuration());
        String ins = (String) req.getParameter("ins");
        Logger.getLogger(getClass().getName()).log(Level.INFO,
                        "Insert Flag: " + ins);
        if (ins.equalsIgnoreCase("true")) {
            try {
                del.processCreate(ps);
            } catch (SQLException ex) {
                Logger.getLogger(EnterProgramSlotDetailsCmd.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                del.processModify(ps);
            } catch (NotFoundException | SQLException ex) {
                Logger.getLogger(EnterProgramSlotDetailsCmd.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int year = 0;
        int week = 0;
        ReviewSelectScheduledProgramDelegate del2 = new ReviewSelectScheduledProgramDelegate();
        List<AnnualSchedule> yearList = del2.reviewSelectAnnualSchedule();
        
        if(req.getParameter("year") != null && req.getParameter("week") != null){
            year = Integer.parseInt(req.getParameter("year"));
            week = Integer.parseInt(req.getParameter("week"));
            System.out.println("test year");
            System.out.println(year+week);
        }else{
            if((year <= 0 || week <= 0 || week > 52)){
                Calendar now = Calendar.getInstance();
                year = now.get(Calendar.YEAR);
                week = now.get(Calendar.WEEK_OF_YEAR);
            }
        }
        
        List<ProgramSlot> data = del2.searchScheduledProgramSlot(year, week);
        req.setAttribute("year",year);
        req.setAttribute("week", week);
        req.setAttribute("yearlist", yearList);
        req.setAttribute("pss", data);
        return "/pages/crudsc.jsp";
        }
        if (req.getParameter("Submit").equals("selectrp"))
        {
            return "/pages/searchrp.jsp";
        }
        return null;
    }
}
