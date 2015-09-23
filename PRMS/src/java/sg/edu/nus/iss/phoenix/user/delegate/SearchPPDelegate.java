/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.delegate;
import java.util.ArrayList;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.user.delegate.SearchPPDelegate;
import sg.edu.nus.iss.phoenix.user.service.UserService;

/**
 *
 * @author achyut
 */
public class SearchPPDelegate {
           
            public ArrayList<User> FetchPresnterProducers() {
		UserService service = new UserService();
		return service.findAllPresenterProducer();
		
	}

	
}