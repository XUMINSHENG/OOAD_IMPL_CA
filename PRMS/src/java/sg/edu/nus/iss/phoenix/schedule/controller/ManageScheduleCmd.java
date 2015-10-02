/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.schedule.delegate.ReviewSelectScheduledProgramDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;

/**
 *
 * 
 */
@Action("managesc")
public class ManageScheduleCmd implements Perform {
    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        
        User user = (User)req.getSession().getAttribute("user");
        if ((null==user)||!user.hasRole("manager")){
            return "/pages/error.jsp";
        }
        int year = 0;
        int week = 0;
        ReviewSelectScheduledProgramDelegate del = new ReviewSelectScheduledProgramDelegate();
        List<AnnualSchedule> yearList = del.reviewSelectAnnualSchedule();
        
        System.out.println(req.getParameter("year"));
        System.out.println(req.getParameter("current_week"));
        if(req.getParameter("year") != null && req.getParameter("current_week") != null){
            year = Integer.parseInt(req.getParameter("year"));
            week = Integer.parseInt(req.getParameter("current_week"));
            System.out.println(year);
            System.out.println(week);
            if( year < 0 || week < 0 || week > 53 ){
                year = 0;
                week = 0;
            }
        }else{
            System.out.println("no data");
        }

        if(year != 0 && week != 0){
            WeeklySchedule weeklySchedule = del.reviewSelectWeeklySchedule(year, week);
            req.setAttribute("ws", weeklySchedule);
            System.out.println("weekschedule");
        }
        
        req.setAttribute("year",year);
        req.setAttribute("current_week", week);
        req.setAttribute("yearlist", yearList);

        
        return "/pages/crudsc.jsp";
    }
}
