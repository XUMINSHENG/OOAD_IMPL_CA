/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.presenter.delegate;

import java.util.ArrayList;
import sg.edu.nus.iss.phoenix.presenter.service.PresenterService;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;

/**
 *
 * @author tanuj
 */
public class PresenterDelegate {
        public ArrayList<Presenter> FetchPresenters()
        {
		PresenterService service = new PresenterService();
		return service.findAllPresenters();
	}
        public ArrayList<Presenter> FetchPresentersByName(String name)
        {
		PresenterService service = new PresenterService();
		return service.findPresentersByName(name);
	}
}
