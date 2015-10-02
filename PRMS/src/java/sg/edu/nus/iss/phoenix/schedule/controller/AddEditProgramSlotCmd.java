/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.controller;

import java.io.IOException;
import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;


/**
 *
 * @author Liu Xinzhuo
 */
@Action("addeditps")
public class AddEditProgramSlotCmd implements Perform {
    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException 
    {
        System.out.println(path);
        // Authorization Check
        User user = (User)req.getSession().getAttribute("user");
        if ((null==user)||!user.hasRole("manager")){
            req.setAttribute("errorMsg", 
                    "You do not have the privileges to perform this operation");
            return "/pages/error.jsp";
        }
        
        // collect data from requset
        
        return "/pages/setupps.jsp";
    }
}
