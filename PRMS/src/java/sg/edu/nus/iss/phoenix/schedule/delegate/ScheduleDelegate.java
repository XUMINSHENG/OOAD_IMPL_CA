package sg.edu.nus.iss.phoenix.schedule.delegate;

import java.sql.Time;
import sg.edu.nus.iss.phoenix.radioprogram.delegate.*;
import java.util.ArrayList;
import java.util.Date;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RPSearchObject;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;

public class ScheduleDelegate {

	public ArrayList<ProgramSlot> searchPrograms(RPSearchObject rpso) {
//		ProgramSlot rp = new ProgramSlot(rpso.getName());
//		rp.setDescription(rpso.getDescription());
//		ScheduleService service = new ScheduleService();
//		return service.searchProgramSlots(rp);	
            return null;
	}
	
	public ArrayList<ProgramSlot> findRPByCriteria(RPSearchObject rpso) {
//		ProgramSlot rp = new ProgramSlot(rpso.getName());
//		rp.setDescription(rpso.getDescription());
//		ScheduleService service = new ScheduleService();
//		return service.searchProgramSlots(rp);	
            return null;
	}
	
	public ProgramSlot findRP(String rpName) {
//		ProgramSlot rp = new ProgramSlot(rpName);
//		ScheduleService service = new ScheduleService();
//		return (service.searchProgramSlots(rp)).get(0);	
		return null;
	}
	public ArrayList<ProgramSlot> findAllRP() {
//		ScheduleService service = new ScheduleService();
//		return service.findAllRP();
		return null;
	}

	public void processCreateAnnualSchedule(ProgramSlot ps) {
//		ScheduleService service = new ScheduleService();
//		service.processCreate(rp);
	}
        
        public void processCreate(ProgramSlot ps) {
		ScheduleService service = new ScheduleService();
		service.processCreate(ps);
	}
        
	public void processModify(ProgramSlot ps) {
		ScheduleService service = new ScheduleService();
		service.processModify(ps);
		
	}

	public void processDelete(Date dateOfProgram, Time startTime) {
		ScheduleService service = new ScheduleService();
		service.processDelete(dateOfProgram, startTime);
	}
        
        public void processCopy() {
		ScheduleService service = new ScheduleService();
		//service.processCopy();
	}
}
