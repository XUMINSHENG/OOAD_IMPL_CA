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
import sg.edu.nus.iss.phoenix.util.Util;

/**
 *
 * @author boonkui
 */
@Action("enterps")
public class EnterProgramSlotDetailsCmd implements Perform {
    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ScheduleDelegate del = new ScheduleDelegate();
        ProgramSlot ps = new ProgramSlot();
        try {
            ps.setDateOfProgram(Util.stringToDate(req.getParameter("dateOfProgram")));
        } catch (ParseException ex) {
            Logger.getLogger(EnterProgramSlotDetailsCmd.class.getName()).log(Level.SEVERE, null, ex);
        }
        ps.setStartTime(Util.stringToTime(req.getParameter("startTime")));
        ps.setDuration(Util.stringToTime(req.getParameter("duration")));
        RadioProgram trp = new RadioProgram();
        trp.setName("TestRadio");
        ps.setProgram(trp);
        Presenter presenter = new Presenter();
        presenter.setName("TestPresenter");
        ps.setPresenter(presenter);
        Producer producer = new Producer();
        producer.setName("TestProducer");
        ps.setProducer(producer);
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
        return "/pages/crudps.jsp";
    }
}
