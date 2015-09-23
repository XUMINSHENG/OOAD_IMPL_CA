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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.user.delegate.SearchPPDelegate;

/**
 *
 * @author achyut
 */

@Action("searchpp")
public class SearchPPCmd implements Perform {

    @Override
    public String perform(String string, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        
        SearchPPDelegate del = new SearchPPDelegate();
        List<User> data = del.FetchPresnterProducers();
        req.setAttribute("searchpplist", data);
     //  user.setName(request.getParameter("name"));
        
        return "/pages/searchpp.jsp";
        
        
    }
}