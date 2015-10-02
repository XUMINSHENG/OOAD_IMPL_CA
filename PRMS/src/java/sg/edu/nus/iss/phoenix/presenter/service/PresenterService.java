/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.presenter.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.presenter.dao.PresenterDAO;
import sg.edu.nus.iss.phoenix.presenter.dao.impl.PresenterDAOImpl;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;

/**
 *
 * @author tanuj
 */
public class PresenterService {
    public ArrayList<Presenter> findAllPresenters()
    {
        PresenterDAO dao = new PresenterDAOImpl();
        ArrayList<Presenter> currentList = new ArrayList<Presenter>();
        try {
            currentList= (ArrayList<Presenter>)dao.loadAll();
        } catch (SQLException ex) {
            Logger.getLogger(PresenterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currentList;
    }
    public ArrayList<Presenter> findPresentersByName(String name)
    {
        PresenterDAO dao = new PresenterDAOImpl();
        ArrayList<Presenter> currentList = new ArrayList<Presenter>();
        try {
            currentList= (ArrayList<Presenter>)dao.searchByName(name);
        } catch (SQLException ex) {
            Logger.getLogger(PresenterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currentList;
    }
}
