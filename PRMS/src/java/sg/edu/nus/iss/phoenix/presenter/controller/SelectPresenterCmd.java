/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.presenter.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.presenter.delegate.PresenterDelegate;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;

/**
 *
 * @author tanuj
 */
@Action("selectPresenter")
public class SelectPresenterCmd implements Perform {

    @Override
    public String perform(String string, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PresenterDelegate del = new PresenterDelegate();
        
        ArrayList<Presenter> data = null;
        String name = req.getParameter("name");
        if (name != null && !name.isEmpty())
            data = del.FetchPresentersByName(name);
        else 
            data = del.FetchPresenters();
        req.setAttribute("name", name);
        req.setAttribute("selPresList", data);
        return "/pages/selectPresenter.jsp";
    }
    
}
