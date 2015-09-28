package sg.edu.nus.iss.phoenix.schedule.service;

import sg.edu.nus.iss.phoenix.radioprogram.service.*;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.SPSearchObject;

public class ScheduleService {
	DAOFactoryImpl factory;
	ScheduleDAO scdao;

	public ScheduleService() {
		super();
		// TODO Auto-generated constructor stub
		factory = new DAOFactoryImpl();
		scdao = factory.getScheduleDAO();
	}

	public ArrayList<ProgramSlot> searchProgramSlots(SPSearchObject spso) {
		ArrayList<ProgramSlot> list = new ArrayList<ProgramSlot>();
		try {
			list = (ArrayList<ProgramSlot>) scdao.searchMatching(spso);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<ProgramSlot> findRPByCriteria(SPSearchObject ps) {
		ArrayList<ProgramSlot> currentList = new ArrayList<ProgramSlot>();

//		try {
//			currentList = (ArrayList<ProgramSlot>) scdao.searchMatching(ps);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return currentList;

	}

	public RadioProgram findRP(String rpName) {
		RadioProgram currentrp = new RadioProgram();
//		currentrp.setName(rpName);
//		try {
//			currentrp = ((ArrayList<RadioProgram>) scdao
//					.searchMatching(currentrp)).get(0);
//			return currentrp;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return currentrp;

	}

	public ArrayList<ProgramSlot> findAllSP() {
		ArrayList<ProgramSlot> currentList = new ArrayList<ProgramSlot>();
		try {
			currentList = (ArrayList<ProgramSlot>) scdao.loadAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentList;

	}

	public void processCreate(ProgramSlot ps) throws SQLException {
	try {
		scdao.create(ps);
		} 
                catch (SQLException e) {
			throw e;
		}
	}

	public void processModify(ProgramSlot ps) throws NotFoundException, SQLException {
		
			try {
				scdao.save(ps);
			} catch (NotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				throw e;
			}
		
	}

	public void processDelete(int year, int weekNum, Date dateOfProgram, Time startTime) throws NotFoundException, SQLException {

            try {
                ProgramSlot ps = new ProgramSlot(year, weekNum, dateOfProgram, startTime);
                scdao.delete(ps);
            } catch (NotFoundException e) {
                throw e;
            } catch (SQLException e) {
                throw e;
            }
	}
        public void processCreateAnnualSchedule(int year_number,String name) throws SQLException {	 
	  try {
		scdao.createAnnualSchedule(year_number,name);
	 	} 
                catch (SQLException e) {
	 		e.printStackTrace();
                         throw e;
		 }
         }
         public void processCreateWeeklySchedule(int year_number,int week_number,String name) throws SQLException {	 
	 try {
		scdao.createWeeklySchedule(year_number,week_number,name);
	 	} 
                catch (SQLException e) {
	 		e.printStackTrace();
                        throw e;
                }
         }
}
