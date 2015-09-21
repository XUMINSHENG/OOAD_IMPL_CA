package sg.edu.nus.iss.phoenix.schedule.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;

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
 
	public List<ProgramSlot> reviewSelectScheduledProgram() {
            List<ProgramSlot> data = null;
            try {
                data = rpdao.loadAll();
            } catch (SQLException ex) {
                Logger.getLogger(ReviewSelectScheduledProgramService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return data; 
	}

        public WeeklySchedule reviewSelectWeeklySchedule(Timestamp dateOfWeek) {
            WeeklySchedule data = null;
            try {
                data = rpdao.getWeeklySchedule(dateOfWeek);
            } catch (SQLException ex) {
                Logger.getLogger(ReviewSelectScheduledProgramService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotFoundException ex) {
                Logger.getLogger(ReviewSelectScheduledProgramService.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            return data;
        }
        
        public List<ProgramSlot> searchScheduledProgramSlot(int year, int week) {
            List<ProgramSlot> data = null;
            try {
                data = rpdao.searchScheduledProgramSlot(year, week);
            } catch (SQLException ex) {
                Logger.getLogger(ReviewSelectScheduledProgramService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotFoundException ex) {
                Logger.getLogger(ReviewSelectScheduledProgramService.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            return data;
        }
}
