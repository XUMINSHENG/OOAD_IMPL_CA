/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.producer.delegate;

import java.util.ArrayList;
import sg.edu.nus.iss.phoenix.producer.service.ProducerService;
import sg.edu.nus.iss.phoenix.user.entity.Producer;

/**
 *
 * @author tanuj
 */
public class ProducerDelegate {
                    public ArrayList<Producer> FetchProducers() {
		ProducerService service = new ProducerService();
		return service.findAllProducers();
	}
        public ArrayList<Producer> FetchProducersByName(String name)
        {
		ProducerService service = new ProducerService();
		return service.findProducersByName(name);
	}
}
