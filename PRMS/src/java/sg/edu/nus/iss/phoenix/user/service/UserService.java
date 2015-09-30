/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.service;

import java.sql.SQLException;
import java.util.ArrayList;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.user.delegate.UserDelegate;
import sg.edu.nus.iss.phoenix.user.service.UserService;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.presenter.dao.PresenterDAO;
import sg.edu.nus.iss.phoenix.producer.dao.ProducerDAO;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;
import sg.edu.nus.iss.phoenix.user.entity.Producer;

/**
 *
 * @author achyut
 */
public class UserService {

    DAOFactoryImpl factory;
    UserDao usrdao;
    PresenterDAO presdao;
    ProducerDAO proddao;

    public UserService() {
        super();
        // TODO Auto-generated constructor stub
        factory = new DAOFactoryImpl();
        usrdao = factory.getUserDAO();
        presdao = factory.getPresenterDAO();
        proddao = factory.getProducerDAO();
    }

    public ArrayList<User> findAllUsers() {
        ArrayList<User> currentList = new ArrayList<User>();
        try {
            currentList = (ArrayList<User>) usrdao.loadAll();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return currentList;

    }

    public void processCreate(User user) throws SQLException, NotFoundException {

        String id = user.getId();
        ArrayList<Role> a_role = user.getRoles();
        String s_role = "";
        Presenter presenter = null;
        Producer producer = null;
        try {
            usrdao.create(user);
            presenter = presdao.getObject(id);
            producer = proddao.getObject(id);
            for (int i = 0; i < a_role.size(); i++) {
                s_role = a_role.get((i)).getRole().toString();

                if (s_role.equals("presenter")) {
                    presdao.create(presenter);
                }
                if (s_role.equals("producer")) {
                   proddao.create(producer);
                }

            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("User already exusting with a 'N' flag");
            for (int i = 0; i < a_role.size(); i++) {
                s_role = a_role.get((i)).getRole().toString();
                usrdao.reassign(user);
                if (s_role.equals("presenter")) {
                    try{
                    
                    presdao.reassign(id);
                    }catch(SQLException exception0){
                        //when the presenter doesnt exist in the presenter table, create first
                        presenter = presdao.getObject(id);
                        presdao.create(presenter);
                    }
                }
                if (s_role.equals("producer")) {
                    try{
                    proddao.reassign(id);
                    }catch(SQLException exception1){
                        //when the producer doesnt exist in the producer table, create first
                        producer = proddao.getObject(id);                        
                        proddao.create(producer);
                        
                    }
                }

            }

            e.printStackTrace();
        } catch (NotFoundException e) {
            System.out.println("HERE 1");
            e.printStackTrace();
        }
    }

    public void processModify(User user) {

        String id = user.getId();
        int flagpres =0;
        int flagprod=0;

        try {
            usrdao.save(user);
            Presenter presenter = presdao.getObject(id);
            Producer producer = proddao.getObject(id);
            ArrayList<Role> a_role = user.getRoles();
            String s_role = "";
           
            for (int i = 0; i < a_role.size(); i++) {
                s_role = a_role.get((i)).getRole().toString();

                if (s_role.equals("presenter")) {
                    System.out.println("PRESENTER IF");
                    flagpres =1;
                    presdao.save(presenter);
                    
                    
                }
                if (s_role.equals("producer")) {
                    System.out.println("producer IF");
                    flagprod =1;
                   proddao.save(producer);
                }

            }
            if(flagpres == 0){
                presdao.deassign(id);
            }
            if(flagprod ==0){
                proddao.deassign(id);
            }

            
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void processDeassign(String id) {

        try {
            //User user = new User(name);
            User user = usrdao.getObject(id);

            ArrayList<Role> a_role = user.getRoles();
            String s_role = "";

            for (int i = 0; i < a_role.size(); i++) {
                s_role = a_role.get((i)).getRole().toString();

                if (s_role.equals("presenter")) {
                    presdao.deassign(id);
                }
                if (s_role.equals("producer")) {
                    proddao.deassign(id);
                }

            }

            usrdao.deassign(user);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void processCreate_presenter(Presenter pres) {
        try {
            presdao.create(pres);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void processModify_presenter(Presenter pres) {

        try {
            presdao.save(pres);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void processCreate_producer(Producer prod) {
        try {
            proddao.create(prod);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void processModify_producer(Producer prod) {

        try {
            proddao.save(prod);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
