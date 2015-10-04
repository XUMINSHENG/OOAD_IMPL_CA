/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.schedule.delegate.ReviewSelectScheduledProgramDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;

/**
 * <p>This class represent the Action class that handles 
 * HttpServletRequest which is sent to PhoenixFrontController to review program slots of selected weekly schedule 
 * {@link WeeklySchedule}.</p>
 * 
 * <p>The method <code>perform() </code> which implements the {@link Perform} 
 * Interface will be invoked when a request is sent to <strong>managesc</strong>
 * URL </p>
 * 
 * @author XIE JIABAO
 * @version 1.0 2015/10/03
 */
@Action("managesc")
public class ManageScheduleCmd implements Perform {
    
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
        
//        Authorization check
        User user = (User)req.getSession().getAttribute("user");
        if ((null==user)||!user.hasRole("manager")){
            return "/pages/error.jsp";
        }
        
        int year = 0;
        int week = 0;
        ReviewSelectScheduledProgramDelegate del = new ReviewSelectScheduledProgramDelegate();
        List<AnnualSchedule> yearList = del.reviewSelectAnnualSchedule();
        
//        get data from request
        if(getValueFromReq(req,"year")!= null && getValueFromReq(req,"current_week")!= null){
            year = Integer.parseInt(getValueFromReq(req,"year"));
            week = Integer.parseInt(getValueFromReq(req,"current_week"));
            if( year < 0 || week < 0 || week > 53 ){
                year = 0;
                week = 0;
            }
        }else{
            System.out.println("no data");
        }
           
//        execute query to get weekly schedule
        if(year != 0 && week != 0){
            WeeklySchedule weeklySchedule = del.reviewSelectWeeklySchedule(year, week);
            req.setAttribute("ws", weeklySchedule);
        }
        
//        bind data and display crudsc.jsp
        req.setAttribute("year",year);
        req.setAttribute("current_week", week);
        req.setAttribute("yearlist", yearList);
        return "/pages/crudsc.jsp";
    }
    
    private String getValueFromReq(HttpServletRequest req, String name)
    {
        String Para = req.getParameter(name);
        if (req.getAttribute(name)==null)
            return Para;
        
        String Attr = req.getAttribute(name).toString();
        return (Attr.length()!=0)?( Attr):( Para); 
    }
}
