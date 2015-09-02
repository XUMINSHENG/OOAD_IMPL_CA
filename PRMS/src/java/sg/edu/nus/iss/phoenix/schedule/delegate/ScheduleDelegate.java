package sg.edu.nus.iss.phoenix.schedule.delegate;

import sg.edu.nus.iss.phoenix.radioprogram.delegate.*;
import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RPSearchObject;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;

public class ScheduleDelegate {

	public ArrayList<RadioProgram> searchPrograms(RPSearchObject rpso) {
		RadioProgram rp = new RadioProgram(rpso.getName());
		rp.setDescription(rpso.getDescription());
		ScheduleService service = new ScheduleService();
		return service.searchPrograms(rp);	
	}
	
	public ArrayList<RadioProgram> findRPByCriteria(RPSearchObject rpso) {
		RadioProgram rp = new RadioProgram(rpso.getName());
		rp.setDescription(rpso.getDescription());
		ScheduleService service = new ScheduleService();
		return service.searchPrograms(rp);	
	}
	
	public RadioProgram findRP(String rpName) {
		RadioProgram rp = new RadioProgram(rpName);
		ScheduleService service = new ScheduleService();
		return (service.searchPrograms(rp)).get(0);	
		
	}
	public ArrayList<RadioProgram> findAllRP() {
		ScheduleService service = new ScheduleService();
		return service.findAllRP();
		
	}

	public void processCreateAnnualSchedule(RadioProgram rp) {
		ScheduleService service = new ScheduleService();
		service.processCreate(rp);
	}
        
        public void processCreate(RadioProgram rp) {
		ScheduleService service = new ScheduleService();
		service.processCreate(rp);
	}
        
	public void processModify(RadioProgram rp) {
		ScheduleService service = new ScheduleService();
		service.processModify(rp);
		
	}

	public void processDelete(String name) {
		ScheduleService service = new ScheduleService();
		service.processDelete(name);
	}
        
        public void processCopy(String name) {
		ScheduleService service = new ScheduleService();
		service.processDelete(name);
	}
}
