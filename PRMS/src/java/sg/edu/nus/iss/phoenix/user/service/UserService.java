/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.service;
import java.sql.SQLException;
import java.util.ArrayList;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;

/**
 * 
 * @author tanuj
 */
public class UserService {
    
    DAOFactoryImpl factory;
	UserDao usrdao;

	public UserService() {
		super();
		// TODO Auto-generated constructor stub
		factory = new DAOFactoryImpl();
		usrdao = factory.getUserDAO();
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
        	public ArrayList<User> findAllPresenterProducer() {
		ArrayList<User> currentList = new ArrayList<User>();
		try {
			currentList = (ArrayList<User>) usrdao.getListofPresenterProducer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentList;

	}
        
        public void processCreate(User user) {
		try {
			usrdao.create(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void processModify(User user) {
		
			try {
				usrdao.save(user);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
        
        public void processDelete(String name){
            
            try{
                User user = new User(name);
                usrdao.delete(user);
            }catch(NotFoundException e){
                e.printStackTrace();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }


    
}