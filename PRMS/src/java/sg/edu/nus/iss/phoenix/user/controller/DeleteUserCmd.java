/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.user.delegate.UserDelegate;

/**
 *
 * @author achyut
 */
@Action("deleteuser")
public class DeleteUserCmd implements Perform {

    @Override
    public String perform(String string, HttpServletRequest hsr, HttpServletResponse hsr1) throws IOException, ServletException {

        UserDelegate del = new UserDelegate();
        List<User> data1 = del.FetchUsers();

        String name = hsr.getParameter("name");
        String id = null;
        if (name.contains(",")) {
            id = name.substring(0, name.indexOf(','));
            //name = id;
        } else {
            id = name;
        }

        try {
            del.processDeassign(id);
        } catch (Exception ex) {
            Logger.getLogger(DeleteUserCmd.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<User> data = del.FetchUsers();
        hsr.setAttribute("rps", data);
        return "/pages/cruduser.jsp";
    }

}
