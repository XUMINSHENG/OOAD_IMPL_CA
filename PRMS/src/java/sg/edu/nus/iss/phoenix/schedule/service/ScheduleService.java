package sg.edu.nus.iss.phoenix.schedule.service;

import sg.edu.nus.iss.phoenix.radioprogram.service.*;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

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
			currentList = (ArrayList<ProgramSlot>) scdao.loadAllProgramSlot();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentList;

	}

	public void processCreate(ProgramSlot ps) throws SQLException {
	try {
		scdao.createProgramSlot(ps);
		} 
                catch (SQLException e) {
			throw e;
		}
	}

	public void processModify(ProgramSlot ps) throws NotFoundException, SQLException {
		
			try {
				scdao.saveProgramSlot(ps);
			} catch (NotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				throw e;
			}
		
	}

	public void processDelete(int year, int weekNum, Date dateOfProgram, 
                Time startTime) throws NotFoundException, SQLException {

            try {
                ProgramSlot ps = new ProgramSlot(year, weekNum, dateOfProgram, startTime);
                scdao.deleteProgramSlot(ps);
            } catch (NotFoundException e) {
                throw e;
            } catch (SQLException e) {
                throw e;
            }
	}
        public void processCreateAnnualAndWeeklySchedule(int year_number, 
                int week_number ,String name) 
                throws Exception {	 
            
            UserTransaction utx = null;
            try {
                
                utx = (UserTransaction)InitialContext.doLookup("java:comp/UserTransaction");
                utx.begin();
                
                // create year schedule
                scdao.createAnnualSchedule(year_number,name);
                
                // create all weekly schedule of this year in one shot
                scdao.createWeeklySchedule(year_number,week_number,name);
                
                utx.commit();
            } 
            catch (SQLException e) {
                utx.rollback();
                throw e;
            }
         }

    public ArrayList<ProgramSlot> processCopy(SPSearchObject src, SPSearchObject dest, long diffDays) throws Exception 
    {
            
            ArrayList<ProgramSlot> srcSlots = null;
            ArrayList<ProgramSlot> destSlots = new ArrayList<ProgramSlot>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            UserTransaction utx = null;
            try {
                
                srcSlots=searchProgramSlots(src);
                for(ProgramSlot slot:srcSlots)
                {
                    ProgramSlot newSlot = new ProgramSlot();
                    newSlot.setYear(Integer.parseInt(dest.getYear()));
                    newSlot.setWeekNum(Integer.parseInt(dest.getWeek()));
                    cal.setTime(slot.getDateOfProgram()); 
                    cal.add(Calendar.DATE, (int)diffDays); // Adding days
                    newSlot.setDateOfProgram(sdf.parse(sdf.format(cal.getTime())));
                    destSlots.add(newSlot);
                }
                    
                    utx = (UserTransaction)InitialContext.doLookup("java:comp/UserTransaction");
                    utx.begin();
                    scdao.deleteAllProgramSlotByWeek(Integer.parseInt(dest.getYear()),Integer.parseInt(dest.getWeek()));
                    scdao.copyWeeklySchedule(srcSlots,destSlots);
                
                    utx.commit();
                return searchProgramSlots(dest);
            }  
            catch (SQLException e) {
                utx.rollback();
                throw e;
            }
    }
}
