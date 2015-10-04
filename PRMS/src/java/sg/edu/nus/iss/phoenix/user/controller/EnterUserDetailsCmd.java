/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.user.delegate.UserDelegate;
import sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDaoImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;
import sg.edu.nus.iss.phoenix.user.entity.Producer;

/**
 *
 * @author achyut
 */
@Action("enteruser")
public class EnterUserDetailsCmd implements Perform {

    private static final String DELIMITER = ":";

    @Override
    public String perform(String string, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String name = request.getParameter("name");
        String[] s_roles = request.getParameterValues("rolelist");
        String address = request.getParameter("address");
        String password = request.getParameter("password");
        String joiningdate = request.getParameter("joiningdate");
     
        User user1 = (User) request.getSession().getAttribute("user");
//        name = request.getParameter("name");
        if ((("").equals(name)) || Arrays.equals(s_roles, null) || ("").equals(address) || ("").equals(password) || ("").equals(joiningdate)) {
            request.setAttribute("errorMsg",
                    "One or more input fields is missing, please fill all the details while creating a user!");
            return "/pages/error.jsp";
        }

        UserDelegate del = new UserDelegate();
        User user = new User();
        Presenter presenter = new Presenter();
        Producer producer = new Producer();

        
        user.setName(name);
        String id = name;//from name, id needs to be populated
        String[] id_arr = id.trim().split(",");
        if (id_arr.length > 1) {
            id = id_arr[0];
            user.setId(id);
        } else {
            user.setId(id);
        }

        
        String roleList = "";
        int count = 0;
        for (String Roles : s_roles) {
            count++;
            //roleList.add(Roles);
            if (count > 1) {
                roleList = roleList + ":";
                roleList = roleList + Roles;
            } else {
                roleList = roleList + Roles;
            }
        }
        //logger.log(Level.WARNING, "s_roles:", s_roles);

        //user.setRoles(createRoles(request.getParameter("rolelist")));
        user.setRoles(createRoles(roleList));
        user.setAddress(address);
        user.setPassword(password);
        user.setJoiningDate(joiningdate);
        String ins = (String) request.getParameter("ins");
        Logger.getLogger(getClass().getName()).log(Level.INFO,
                "Insert Flag: " + ins);

        //presenter.setName(request.getParameter("name"));
        presenter.setUserId(id);
        presenter.setIsActive("Y");

        //producer.setName(request.getParameter("name"));
        producer.setUserId(id);
        producer.setIsActive("Y");

        if (ins.equalsIgnoreCase("true")) {
            try {
                del.processCreate(user);
            //  del.processCreate_presenter(presenter);
                //  del.processCreate_producer(producer);
            } catch (SQLException ex) {
                 request.setAttribute("errorMsg",
                    "Duplicate user, already exists in the DB!");
            return "/pages/error.jsp";
                //Logger.getLogger(EnterUserDetailsCmd.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotFoundException ex) {
                Logger.getLogger(EnterUserDetailsCmd.class.getName()).log(Level.SEVERE, null, ex);
            }catch(Exception e){
                e.printStackTrace();
            }
        } else {
            try {
                System.out.println("inside modify");
                del.processModify(user);
            } catch (Exception ex) {
                Logger.getLogger(EnterUserDetailsCmd.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        UserDelegate delegate = new UserDelegate();
        List<User> data = delegate.FetchUsers();

        request.setAttribute("rps", data);
        return "/pages/cruduser.jsp";

    }

    private ArrayList<Role> createRoles(final String roles) {

        ArrayList<Role> roleList = new ArrayList<Role>();
        String[] _r = roles.trim().split(DELIMITER);
        for (String r : _r) {
            roleList.add(new Role(r.trim()));
        }
        return (roleList);
    }

}
