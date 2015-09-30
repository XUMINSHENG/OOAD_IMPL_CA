/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.controller;

import java.io.IOException;
import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.schedule.delegate.ScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
/**
 *
 * @author AVISHEK
 */
@Action("addasc")
public class AddAnnualScheduleCmd implements Perform {
    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) 
            throws IOException, ServletException 
    {
        // Authorization Check
        User user = (User)req.getSession().getAttribute("user");
        if ((null==user)||!user.hasRole("manager")){
            req.setAttribute("errorMsg",
                    "You do not have the privileges to perform this operation");
            return "/pages/error.jsp";
        }
       
        ScheduleDelegate del = new ScheduleDelegate();
        
        if((null!=req.getParameter("year"))
                &&(!"".equals(req.getParameter("year")))){
            int year;
            //need to validate that the year is not null and appropiate
            try{
                year = Integer.parseInt(req.getParameter("year"));
            }catch (NumberFormatException ex){
                req.setAttribute("errorMsg","Invalid Input");
                return "/pages/error.jsp";
            }

            try {
                // get total week number of this year
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, Calendar.JANUARY);
                mCalendar.set(Calendar.DAY_OF_MONTH, 1);
                int totalWeeks = mCalendar.getActualMaximum(Calendar.WEEK_OF_YEAR);

                // create annual and weekly schedule
                del.processCreateAnnualSchedule(year, totalWeeks, 
                        user.getId());
                
                req.setAttribute("msg", "Year " + year + " and its weekly schedules created successfully");
            }catch (Exception ex) {
                req.setAttribute("errorMsg",ex.getMessage());
                return "/pages/error.jsp";
            }
        }
        return "/pages/addasc.jsp";   
    }
}
