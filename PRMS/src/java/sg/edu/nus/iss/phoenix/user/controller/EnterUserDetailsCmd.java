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
import java.sql.Time;
import java.util.ArrayList;
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
        
        UserDelegate del = new UserDelegate();
        User user = new User();
        Presenter presenter = new Presenter();
        Producer producer = new Producer();
        
        
        user.setName(request.getParameter("name"));        
        String id= request.getParameter("name");//from name, id needs to be populated
        String[] id_arr = id.trim().split(",");
        if(id_arr.length>1){
            id = id_arr[0];
            user.setId(id);
        }else{
            user.setId(id);
        }
        
        String[] s_roles = request.getParameterValues("rolelist");
        String roleList= "";
        int count=0;
        for(String Roles : s_roles ){
            count++;
            //roleList.add(Roles);
            if(count>1){
                roleList=roleList+":";
                roleList=roleList+Roles;
            }else{
            roleList=roleList+Roles;
            }
        }
        //logger.log(Level.WARNING, "s_roles:", s_roles);
       
         
        //user.setRoles(createRoles(request.getParameter("rolelist")));
        
        
        user.setRoles(createRoles(roleList));
        user.setAddress(request.getParameter("address"));   
        user.setPassword(request.getParameter("password"));
        user.setJoiningDate(request.getParameter("joiningdate"));
        String ins = (String) request.getParameter("ins");
        Logger.getLogger(getClass().getName()).log(Level.INFO,
                        "Insert Flag: " + ins);
        
        presenter.setName(request.getParameter("name"));
        presenter.setUserId(id);
        presenter.setIsActive("Y");
        
        producer.setName(request.getParameter("name"));
        producer.setUserId(id);
        producer.setIsActive("Y");
        
        if (ins.equalsIgnoreCase("true")) {
              del.processCreate(user);
            //  del.processCreate_presenter(presenter);
            //  del.processCreate_producer(producer);
        } else {
            System.out.println("inside modify");
               del.processModify(user);
        }
        
        
        UserDelegate delegate = new UserDelegate();
        List<User> data = delegate.FetchUsers();
        
        request.setAttribute("rps", data);
        return "/pages/cruduser.jsp";
    
    }
    
    
    private ArrayList<Role> createRoles(final String roles) {
        
		ArrayList<Role> roleList = new ArrayList<Role>();
		String[] _r = roles.trim().split(DELIMITER);
		for (String r: _r)
			roleList.add(new Role(r.trim()));
		return (roleList);
	}
  
    
}