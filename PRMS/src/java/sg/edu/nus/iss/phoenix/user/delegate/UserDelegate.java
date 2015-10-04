/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.delegate;

import java.sql.SQLException;
import java.util.ArrayList;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.user.delegate.UserDelegate;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;
import sg.edu.nus.iss.phoenix.user.entity.Producer;
import sg.edu.nus.iss.phoenix.user.service.UserService;

/**
 *
 * @author achyut
 */
public class UserDelegate {

        public ArrayList<User> FetchUsers() {
        UserService service = new UserService();
        return service.findAllUsers();

    }

    public void processCreate(User user) throws SQLException, NotFoundException,Exception {
        UserService service = new UserService();
        service.processCreate(user);

    }

    public void processModify(User user) throws Exception{
        UserService service = new UserService();
        service.processModify(user);

    }

    public void processCreate_presenter(Presenter presenter) throws Exception{
        UserService service = new UserService();
        service.processCreate_presenter(presenter);

    }

    public void processModify_presenter(Presenter presenter)throws Exception{ 
        UserService service = new UserService();
        service.processCreate_presenter(presenter);

    }

    public void processCreate_producer(Producer producer) {
        UserService service = new UserService();
        service.processCreate_producer(producer);

    }

    public void processModify_producer(Producer producer) {
        UserService service = new UserService();
        service.processCreate_producer(producer);

    }

    public void processDeassign_programSlot(String name) {

    }

    public void processDeassign(String id) throws Exception{
        UserService service = new UserService();
       
        service.processDeassign(id);

    }
}
