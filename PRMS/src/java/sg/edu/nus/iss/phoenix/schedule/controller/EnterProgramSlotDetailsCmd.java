//EnterProgramSlotDetailsCmd.java
package sg.edu.nus.iss.phoenix.schedule.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
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
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.presenter.delegate.PresenterDelegate;
import sg.edu.nus.iss.phoenix.producer.delegate.ProducerDelegate;
import sg.edu.nus.iss.phoenix.user.entity.*;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.util.Util;
import sg.edu.nus.iss.phoenix.radioprogram.delegate.ProgramDelegate;
import sg.edu.nus.iss.phoenix.schedule.delegate.ReviewSelectScheduledProgramDelegate;

/**
 *<p>This class represent the Action class that handles 
 * HttpServletRequest which is collect details and sent to PhoenixFrontController 
 * to Create or Modify a {@link ProgramSlot}.</p>
 * 
 * <p>The method <code>perform() </code> which implements the {@link Perform} 
 * Interface will be invoked when a request is sent to <strong>deleteps</strong>
 * URL </p>
 * 
 * @author Liu xinzhuo
 * @version 3.2 2015/10/04
 */

@Action("enterps")
public class EnterProgramSlotDetailsCmd implements Perform {

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
     * 
     */
    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        User user = (User) req.getSession().getAttribute("user");
        if ((null == user) || !user.hasRole("manager")) {
            req.setAttribute("errorMsg",
                    "You do not have the privileges to perform this operation");
            return "/pages/error.jsp";
        }

        ScheduleDelegate sdel = new ScheduleDelegate();
        ProgramDelegate pdel = new ProgramDelegate();
        PresenterDelegate predel = new PresenterDelegate();
        ProducerDelegate prodel = new ProducerDelegate();

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
        String insert = req.getParameter("ins");

        //Val null
        if (Util.IsNull(dateOfProgram) || Util.IsNull(startTime)
                || Util.IsNull(duration) || Util.IsNull(radioProgramName)
                || Util.IsNull(producerName) || Util.IsNull(presenterName)
                || Util.IsNull(insert)) {
            req.setAttribute("errorMsg", "Null Input Error!");
            return "/pages/error.jsp";
        }
        //Val date and time
        if (valDate(dateOfProgram) == false || valTime(startTime) == false
                || valTime(duration) == false || valInsert(insert) == false) {
            req.setAttribute("errorMsg", "Invalid input");
            return "/pages/error.jsp";
        }

        if (valDurationTime(duration) == false) {
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
        List<RadioProgram> rpList = pdel.findRPList(radioProgramName);
        if (rpList.size() != 1) {
            req.setAttribute("errorMsg", "Invalid input");
            return "/pages/error.jsp";
        }
        RadioProgram trp = pdel.findRP(radioProgramName);
        ps.setProgram(trp);

        List<Producer> proList = prodel.FetchProducersByName(producerName);
        List<Presenter> preList = predel.FetchPresentersByName(presenterName);

        if (!(valList(proList) && valList(preList))) {
            req.setAttribute("errorMsg", "Invalid input");
            return "/pages/error.jsp";
        }

        ps.setProducer(proList.get(0));
        ps.setPresenter(preList.get(0));

        Logger.getLogger(getClass().getName()).log(Level.INFO, "Insert Flag: " + insert);

        if (insert.equalsIgnoreCase("true")) {
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
    /**
     * 
     * @param dateString the String which need to match the rexDate
     * @return the result if dateString is matched the rexDate
     */
    private boolean valDate(String dateString) {
        String rexDate = "^((\\d{2}(([02468][048])|([13579][26]))[\\-]?((((0?[13578])|(1[02]))[\\-]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]?((((0?[13578])|(1[02]))[\\-]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern datePattern = Pattern.compile(rexDate);
        Matcher dateMatcher = datePattern.matcher(dateString);
        return dateMatcher.matches();
    }
    /**
     * 
     * @param timeString the String which need to match the rexTime
     * @return the result if dateString is matched the rexTime
     */
    private boolean valTime(String timeString) {
        String rexTime = "^(([0-1][0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9]$";
        Pattern timePattern = Pattern.compile(rexTime);
        Matcher timeMatcher = timePattern.matcher(timeString);
        return timeMatcher.matches();
    }
    /**
     * 
     * @param timeString the String which need to match the rexTime
     * @return the result if dateString is matched the rexDuration 
     */
    private boolean valDurationTime(String timeString) {
        String rexDuration = "^(([0-1][0-9])|(2[0-3])):[0|3][0]:[0][0]$";
        Pattern timePattern = Pattern.compile(rexDuration);
        Matcher timeMatcher = timePattern.matcher(timeString);
        return timeMatcher.matches();
    }
    /**
     * 
     * @param target the {@link ProgramSlot} which the startTime and duration need to be validited
     * @param req the HttpServletRequest that sent from browser in order to 
     *            transmit sufficient data to perform intended task
     * @return the result of over lap and over week's validation 
     * @throws ParseException if an error occor in the process of parsing
     */
    private boolean valOverLapOverWeek(ProgramSlot target, HttpServletRequest req) throws ParseException {
        
        ReviewSelectScheduledProgramDelegate del = new ReviewSelectScheduledProgramDelegate();
        List<ProgramSlot> psList = del.searchScheduledProgramSlot(target.getYear(), target.getWeekNum());
        
        Calendar start = Util.DateAddTime(target.getDateOfProgram(), target.getStartTime());
        Calendar end = Util.CalAddTime(start, target.getDuration());
        
        start.setFirstDayOfWeek(Calendar.MONDAY);
        end.setFirstDayOfWeek(Calendar.MONDAY);
        
        if (start.get(Calendar.WEEK_OF_YEAR) != end.get(Calendar.WEEK_OF_YEAR)) {
            req.setAttribute("errorMsg", "Program Slot Over Week!");
            return false;
        }
        for (ProgramSlot ps : psList) {
            Calendar psStart = Util.DateAddTime(ps.getDateOfProgram(), ps.getStartTime());
            Calendar psEnd = Util.CalAddTime(psStart, ps.getDuration());
            if ((end.after(psStart) && end.before(psEnd)) || (start.before(psEnd) && start.after(psStart))) {
                req.setAttribute("errorMsg", "Program Slot Over Lap!");
                return false;
            }
        }
        return true;
    }
    /**
     * 
     * @param insert the data String of insert
     * @return the result if the insert's validation
     */
    private boolean valInsert(String insert) {
        return insert.equals("true") || insert.equals("false");
    }
    /**
     * 
     * @param list data list
     * @return the result if the size of data list is 1
     */
    private boolean valList(List list) {
        return list.size() == 1;
    }

}
