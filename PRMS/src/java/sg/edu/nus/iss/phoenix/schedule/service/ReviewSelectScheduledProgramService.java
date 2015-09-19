package sg.edu.nus.iss.phoenix.schedule.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

public class ReviewSelectScheduledProgramService {
	DAOFactoryImpl factory;
	ScheduleDAO rpdao;

	public ReviewSelectScheduledProgramService() {
		super();
		// TODO Auto-generated constructor stub
		factory = new DAOFactoryImpl();
		rpdao = factory.getScheduleDAO();
	}
        
        public List<AnnualSchedule> reviewSelectAnnualSchedule() {
            List<AnnualSchedule> data = null;
            try {
                data = rpdao.loadAllAnnualSchedule();
            } catch (SQLException ex) {
                Logger.getLogger(ReviewSelectScheduledProgramService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return data; 
        }
 
	public List<ProgramSlot> reviewSelectScheduledProgram(int yearIn, int weekIn) {
            List<ProgramSlot> data = null;
            try {
                data = rpdao.loadAll();
            } catch (SQLException ex) {
                Logger.getLogger(ReviewSelectScheduledProgramService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return data; 
	}

}
