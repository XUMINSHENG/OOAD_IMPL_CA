/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.user.delegate.UserDelegate;

/**
 *
 * @author achyut
 */

@Action("addmodifyuser")
public class EnterUserDetailsCmd implements Perform {

    @Override
    public String perform(String string, HttpServletRequest hsr, HttpServletResponse hsr1) throws IOException, ServletException {
        
        UserDelegate del = new UserDelegate();
        User user = new User();
        user.setName(hsr.getParameter("name"));
 //       user.setRoles(hsr.getParameter("role"));
        user.setAddress(hsr.getParameter("address"));
        String dur = hsr.getParameter("joiningdate");
//        System.out.println(user.toString());
//        Time t = Time.valueOf(dur);
//        user.setTypicalDuration(t);
        String ins = (String) hsr.getParameter("ins");
        Logger.getLogger(getClass().getName()).log(Level.INFO,
                        "Insert Flag: " + ins);
        if (ins.equalsIgnoreCase("true")) {
                //.processCreate(user);
        } else {
                //del.processModify(user);
        }
        
//        ReviewSelectProgramDelegate rsdel = new ReviewSelectProgramDelegate();
//        List<RadioProgram> data = rsdel.reviewSelectRadioProgram();
//        hsr.setAttribute("rps", data);
        return "/pages/cruduser.jsp";
    }
    
}
