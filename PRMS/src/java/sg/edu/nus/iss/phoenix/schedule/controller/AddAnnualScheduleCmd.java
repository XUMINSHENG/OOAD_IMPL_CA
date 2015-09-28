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
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException 
    {
        System.out.println(path);
        // Authorization Check
        User user = (User)req.getSession().getAttribute("user");
        if ((null==user)||!user.hasRole("manager")){
            req.setAttribute("errorMsg","You do not have the privileges to perform this operation");
            return "/pages/error.jsp";
        }
       
        ScheduleDelegate del = new ScheduleDelegate();
          
        //need to validate that the year is not null and appropiate
          String year=  req.getParameter("year");
          if (year != null)
          {
            try {
                del.processCreateAnnualSchedule(Integer.parseInt(year), user.getId().toString());
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.set(Calendar.YEAR, Integer.parseInt(year));
                mCalendar.set(Calendar.MONTH, Calendar.JANUARY);
                mCalendar.set(Calendar.DAY_OF_MONTH, 1);
                int totalWeeks = mCalendar.getActualMaximum(Calendar.WEEK_OF_YEAR);
                for (int i=1; i<=totalWeeks;i++)
                {
                        del.processCreateWeeklySchedule(Integer.parseInt(year),i, user.getId().toString());
                }
            }catch (SQLException ex) {
                
                        req.setAttribute("errorMsg",ex.getMessage().toString());
                        return "/pages/error.jsp";
                    }
            }
          return "/pages/addasc.jsp";   
          }
}
