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
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.delegate.ScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.user.entity.*;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.util.Util;
import sg.edu.nus.iss.phoenix.radioprogram.delegate.ProgramDelegate;
import sg.edu.nus.iss.phoenix.schedule.delegate.ReviewSelectScheduledProgramDelegate;

/**
 *
 * @author Liu xinzhuo
 */
@Action("enterps")
public class EnterProgramSlotDetailsCmd implements Perform {

    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ScheduleDelegate sdel = new ScheduleDelegate();
        ProgramDelegate pdel = new ProgramDelegate();

        ProgramSlot ps = new ProgramSlot();
        Date date;
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        String dateOfProgram = req.getParameter("dateOfProgram");
        String startTime = req.getParameter("startTime");
        String duration = req.getParameter("duration");
        String radioProgramName = req.getParameter("name");
        String producerName = req.getParameter("producer");
        String presenterName = req.getParameter("presenter");

        //Val null
        if (Util.IsNull(dateOfProgram) || Util.IsNull(startTime)
                || Util.IsNull(duration) || Util.IsNull(radioProgramName)
                || Util.IsNull(producerName) || Util.IsNull(presenterName)) {
            req.setAttribute("errorMsg", "Null Input Error!");
            return "/pages/error.jsp";
        }
        //Val date and time
        if (valDate(dateOfProgram) == false || valTime(startTime) == false
                || valTime(duration) == false) {
            req.setAttribute("errorMsg", "Data Format Error!");
            return "/pages/error.jsp";
        }
        
        if (valDurationTime(duration)==false)
        {
            req.setAttribute("errorMsg", "Duration Format Error! It should be 'HH:30:00'or 'HH:00:00'! ");
            return "/pages/error.jsp";
        }

        try {
            date = Util.stringToDate(dateOfProgram);
            cal.setTime(date);
            ps.setYear(cal.get(Calendar.YEAR));
            ps.setDateOfProgram(date);
            ps.setWeekNum(cal.get(Calendar.WEEK_OF_YEAR));
            ps.setStartTime(Util.stringToTime(startTime));
            ps.setDuration(Util.stringToTime(duration));
        } catch (ParseException ex) {
            Logger.getLogger(DeleteProgramSlotCmd.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("errorMsg", "Invalid input");
            return "/pages/error.jsp";
        }

        RadioProgram trp = pdel.findRP(radioProgramName);
        ps.setProgram(trp);

        Producer producer = new Producer();
        producer.setName(producerName);
        ps.setProducer(producer);

        Presenter presenter = new Presenter();
        presenter.setName(presenterName);
        ps.setPresenter(presenter);

        String ins = (String) req.getParameter("ins");
        Logger.getLogger(getClass().getName()).log(Level.INFO, "Insert Flag: " + ins);

        if (ins.equalsIgnoreCase("true")) {
            try {
                if (valOverLapOverWeek(ps, req) == false) {
                    return "/pages/error.jsp";
                }
                sdel.processCreate(ps);
            } catch (SQLException ex) {
                Logger.getLogger(DeleteProgramSlotCmd.class.getName()).log(Level.SEVERE, null, ex);
                req.setAttribute("errorMsg", ex.getMessage());
                return "/pages/error.jsp";
            } catch (ParseException ex) {
                Logger.getLogger(EnterProgramSlotDetailsCmd.class.getName()).log(Level.SEVERE, null, ex);
                req.setAttribute("errorMsg", ex.getMessage());
                return "/pages/error.jsp";
            }
        } else {
            try {
                sdel.processModify(ps);
            } catch (NotFoundException | SQLException ex) {
                Logger.getLogger(DeleteProgramSlotCmd.class.getName()).log(Level.SEVERE, null, ex);
                req.setAttribute("errorMsg", ex.getMessage());
                return "/pages/error.jsp";
            }
        }

        int year = ps.getYear();
        int week = ps.getWeekNum();
        // forward to managesc servlet to process 
        req.setAttribute("year", year);
        req.setAttribute("current_week", week);
        RequestDispatcher rd = req.getRequestDispatcher("managesc");
        rd.forward(req, resp);
        return "";
    }

    private boolean valDate(String dateString) {
        String rexDate = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern datePattern = Pattern.compile(rexDate);
        Matcher dateMatcher = datePattern.matcher(dateString);
        return dateMatcher.matches();
    }

    private boolean valTime(String timeString) {
        String rexTime = "^(([0-1][0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9]$";
        Pattern timePattern = Pattern.compile(rexTime);
        Matcher timeMatcher = timePattern.matcher(timeString);
        return timeMatcher.matches();
    }
    
    private boolean valDurationTime(String timeString) {
        String rexTime = "^(([0-1][0-9])|(2[0-3])):[0|3][0]:[0][0]$";
        Pattern timePattern = Pattern.compile(rexTime);
        Matcher timeMatcher = timePattern.matcher(timeString);
        return timeMatcher.matches();
    }

    private boolean valOverLapOverWeek(ProgramSlot target, HttpServletRequest req) throws ParseException {
        ReviewSelectScheduledProgramDelegate del = new ReviewSelectScheduledProgramDelegate();
        List<ProgramSlot> psList = del.searchScheduledProgramSlot(target.getYear(), target.getWeekNum());
        Calendar start = Util.DateAddTime(target.getDateOfProgram(), target.getStartTime());
        Calendar end = Util.CalAddTime(start, target.getDuration());
        start.setFirstDayOfWeek(Calendar.MONDAY);
        end.setFirstDayOfWeek(Calendar.MONDAY);
        if (start.get(Calendar.WEEK_OF_YEAR) != end.get(Calendar.WEEK_OF_YEAR)) {
            Logger.getLogger(DeleteProgramSlotCmd.class.getName()).log(Level.SEVERE, null, "");
            req.setAttribute("errorMsg", "Program Slot Over Week!");
            return false;
        }
        for (ProgramSlot ps : psList) {
            Calendar psStart = Util.DateAddTime(ps.getDateOfProgram(), ps.getStartTime());
            Calendar psEnd = Util.CalAddTime(psStart, ps.getDuration());
            if ((end.after(psStart)&& end.before(psEnd))|| (start.before(psEnd)&&start.after(psStart))) {
                Logger.getLogger(DeleteProgramSlotCmd.class.getName()).log(Level.SEVERE, null, "");
                req.setAttribute("errorMsg", "Program Slot Over Lap!");
                return false;
            }
        }
        return true;
    }

}
