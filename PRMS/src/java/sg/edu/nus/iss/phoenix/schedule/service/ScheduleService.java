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

public class ScheduleService {
	DAOFactoryImpl factory;
	ScheduleDAO scdao;

	public ScheduleService() {
		super();
		// TODO Auto-generated constructor stub
		factory = new DAOFactoryImpl();
		scdao = factory.getScheduleDAO();
	}

	public ArrayList<ProgramSlot> searchProgramSlots(ProgramSlot psso) {
		ArrayList<ProgramSlot> list = new ArrayList<ProgramSlot>();
		try {
			list = (ArrayList<ProgramSlot>) scdao.searchMatching(psso);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<ProgramSlot> findRPByCriteria(ProgramSlot ps) {
		ArrayList<ProgramSlot> currentList = new ArrayList<ProgramSlot>();

		try {
			currentList = (ArrayList<ProgramSlot>) scdao.searchMatching(ps);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	public ArrayList<RadioProgram> findAllRP() {
		ArrayList<RadioProgram> currentList = new ArrayList<RadioProgram>();
//		try {
//			currentList = (ArrayList<RadioProgram>) scdao.loadAll();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return currentList;

	}

	public void processCreate(ProgramSlot ps) {
//		try {
//			scdao.create(rp);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public void processModify(ProgramSlot ps) {
//		
//			try {
//				scdao.save(rp);
//			} catch (NotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
	}

	public void processDelete(Time duration, Date dateOfProgram) {

            try {
                ProgramSlot ps = new ProgramSlot(duration,dateOfProgram);
                scdao.delete(ps);
            } catch (NotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	}

}
