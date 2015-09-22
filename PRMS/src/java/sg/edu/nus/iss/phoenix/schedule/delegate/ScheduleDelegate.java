package sg.edu.nus.iss.phoenix.schedule.delegate;

import java.sql.SQLException;
import java.sql.Time;
import sg.edu.nus.iss.phoenix.radioprogram.delegate.*;
import java.util.ArrayList;
import java.util.Date;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RPSearchObject;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.SPSearchObject;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;

public class ScheduleDelegate {

	public ArrayList<ProgramSlot> searchPrograms(SPSearchObject spso) {
//		ProgramSlot rp = new ProgramSlot(rpso.getName());
//		rp.setDescription(rpso.getDescription());
//		ScheduleService service = new ScheduleService();
//		return service.searchProgramSlots(rp);	
            ScheduleService service = new ScheduleService();
            return service.searchProgramSlots(spso);
	}
	
	public ArrayList<ProgramSlot> findSPByCriteria(SPSearchObject spso) {
//                ProgramSlot rp = new ProgramSlot(rpso.getName());
		ScheduleService service = new ScheduleService();
		return service.searchProgramSlots(spso);	
	}
	
	public ProgramSlot findRP(String rpName) {
//		ProgramSlot rp = new ProgramSlot(rpName);
//		ScheduleService service = new ScheduleService();
//		return (service.searchProgramSlots(rp)).get(0);	
		return null;
	}
	public ArrayList<ProgramSlot> findAllSP() {
		ScheduleService service = new ScheduleService();
		return service.findAllSP();
	}

	public void processCreateAnnualSchedule(ProgramSlot ps) {
//		ScheduleService service = new ScheduleService();
//		service.processCreate(rp);
	}
        
        public void processCreate(ProgramSlot ps) throws SQLException {
		ScheduleService service = new ScheduleService();
		service.processCreate(ps);
	}
        
	public void processModify(ProgramSlot ps) throws NotFoundException, SQLException {
		ScheduleService service = new ScheduleService();
		service.processModify(ps);
		
	}

	public void processDelete(Date dateOfProgram, Time startTime) throws NotFoundException, SQLException {
		ScheduleService service = new ScheduleService();
		service.processDelete(dateOfProgram, startTime);
	}
        
        public void processCopy() {
		ScheduleService service = new ScheduleService();
		//service.processCopy();
	}
}
