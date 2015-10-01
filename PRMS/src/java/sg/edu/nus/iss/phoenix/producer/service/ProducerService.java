/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.producer.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.producer.dao.ProducerDAO;
import sg.edu.nus.iss.phoenix.producer.dao.impl.ProducerDAOImpl;
import sg.edu.nus.iss.phoenix.user.entity.Producer;

/**
 *
 * @author tanuj
 */
public class ProducerService {
        public ArrayList<Producer> findAllProducers()
    {
        ProducerDAO dao = new ProducerDAOImpl();
        ArrayList<Producer> currentList = new ArrayList<Producer>();
        try {
            currentList= (ArrayList<Producer>)dao.loadAll();
        } catch (SQLException ex) {
            Logger.getLogger(ProducerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currentList;
    }
    public ArrayList<Producer> findProducersByName(String name)
    {
        ProducerDAO dao = new ProducerDAOImpl(){};
        ArrayList<Producer> currentList = new ArrayList<Producer>();
        try {
            currentList= (ArrayList<Producer>)dao.searchByName(name);
        } catch (SQLException ex) {
            Logger.getLogger(ProducerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currentList;
    }
}
