/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.NotSupportedException;
import javax.transaction.UserTransaction;
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
    UserTransaction utx = null;

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

    public void processCreate(User user) throws SQLException, NotFoundException, Exception {

        String id = user.getId();
        ArrayList<Role> a_role = user.getRoles();
        String s_role = "";
        Presenter presenter = null;
        Producer producer = null;
        utx = (UserTransaction) InitialContext.doLookup("java:comp/UserTransaction");
        try {
            utx.begin();
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
            utx.commit();

        } catch (SQLException e) {
            utx.rollback();
            utx = (UserTransaction) InitialContext.doLookup("java:comp/UserTransaction");
            utx.begin();
            if (("Y").equals(usrdao.checkIfActive(user))) {
                System.out.println("Implement the code of the error ");
                throw e;

            }
            if (("N").equals(usrdao.checkIfActive(user))) {
                System.out.println("User already existing with a 'N' flag");
                for (int i = 0; i < a_role.size(); i++) {
                    s_role = a_role.get((i)).getRole().toString();
                    usrdao.reassign(user);
                    if (s_role.equals("presenter")) {
                        try {

                            presdao.reassign(id);
                            utx.commit();
                        } catch (SQLException exception0) {
                            utx.rollback();
                            utx = (UserTransaction) InitialContext.doLookup("java:comp/UserTransaction");
                            utx.begin();
                            //when the presenter doesnt exist in the presenter table, create first
                            presenter = presdao.getObject(id);
                            presdao.create(presenter);
                            utx.commit();
                        }
                    }
                    if (s_role.equals("producer")) {
                        try {
                            proddao.reassign(id);
                            utx.commit();
                        } catch (SQLException exception1) {
                            utx.rollback();
                            utx = (UserTransaction) InitialContext.doLookup("java:comp/UserTransaction");
                            utx.begin();
                            //when the producer doesnt exist in the producer table, create first
                            producer = proddao.getObject(id);
                            proddao.create(producer);
                            utx.commit();

                        }
                    }

                }
            }

            e.printStackTrace();
        } catch (NotFoundException e) {
            System.out.println("HERE 1");
            e.printStackTrace();
            utx.rollback();
        }
    }

    public void processModify(User user) throws Exception {

        String id = user.getId();
        int flagpres = 0;
        int flagprod = 0;
        utx = (UserTransaction) InitialContext.doLookup("java:comp/UserTransaction");
        try {
            utx.begin();
            usrdao.save(user);
            Presenter presenter = presdao.getObject(id);
            Producer producer = proddao.getObject(id);
            ArrayList<Role> a_role = user.getRoles();
            String s_role = "";

            for (int i = 0; i < a_role.size(); i++) {
                s_role = a_role.get((i)).getRole().toString();

                if (s_role.equals("presenter")) {
                    System.out.println("PRESENTER IF");
                    flagpres = 1;
                    presdao.save(presenter);

                }
                if (s_role.equals("producer")) {
                    System.out.println("producer IF");
                    flagprod = 1;
                    proddao.save(producer);
                }

            }
            if (flagpres == 0) {
                presdao.deassign(id);
            }
            if (flagprod == 0) {
                proddao.deassign(id);
            }
            
            utx.commit();

        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            utx.rollback();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            utx.rollback();
        }

    }

    public void processDeassign(String id) throws Exception {

        try {
            //User user = new User(name);
            User user = usrdao.getObject(id);
            utx = (UserTransaction)InitialContext.doLookup("java:comp/UserTransaction");

            ArrayList<Role> a_role = user.getRoles();
            String s_role = "";

            utx.begin();
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
            utx.commit();
        } catch (NotFoundException e) {
            e.printStackTrace();
            utx.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            utx.rollback();
        }
    }

    public void processCreate_presenter(Presenter pres) throws Exception {
        
        utx = (UserTransaction)InitialContext.doLookup("java:comp/UserTransaction");
        utx.begin();
        try {
            presdao.create(pres);
            utx.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            utx.rollback();
        }
    }

    public void processModify_presenter(Presenter pres) throws Exception{

        utx = (UserTransaction)InitialContext.doLookup("java:comp/UserTransaction");
        utx.begin();
        try {
            presdao.save(pres);
            utx.commit();
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            utx.rollback();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            utx.rollback();
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
