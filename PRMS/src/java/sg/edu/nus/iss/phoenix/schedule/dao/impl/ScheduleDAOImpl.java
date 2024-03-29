package sg.edu.nus.iss.phoenix.schedule.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import sg.edu.nus.iss.phoenix.core.dao.DBConstants;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.SPSearchObject;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;
import sg.edu.nus.iss.phoenix.user.entity.Producer;
import sg.edu.nus.iss.phoenix.util.Util;

/**
 * ProgramSlot Data Access Object (DAO). This claere and retrieve ProgramSlot object
 * instances.
 */
public class ScheduleDAOImpl implements ScheduleDAO {

        
        private DataSource phoenix;
        private Connection connection;
        
        public ScheduleDAOImpl() {
            try {
                this.phoenix = (DataSource)InitialContext.doLookup(DBConstants.dataSourceName);
            } catch (NamingException ex) {
                Logger.getLogger(ScheduleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

	/* (non-Javadoc)
	 * @see sg.edu.nus.iss.phoenix.radioprogram.dao.impl.ProgramDAO#createValueObject()
	 */
       
	@Override
	public ProgramSlot createProgramSlotVO() {
            
		return new ProgramSlot();
	}

	/* (non-Javadoc)
	 * @see sg.edu.nus.iss.phoenix.radioprogram.dao.impl.ProgramDAO#getObject(java.lang.String)
	 */
	@Override
	public ProgramSlot getObject(Time duration, Date dateOfProgram) throws NotFoundException,
			SQLException {

		ProgramSlot valueObject = createProgramSlotVO();
                valueObject.setDuration(duration);
                valueObject.setDateOfProgram(dateOfProgram);
		loadProgramSlot(valueObject);
		return valueObject;
	}

	/* (non-Javadoc)
	 * @see sg.edu.nus.iss.phoenix.radioprogram.dao.impl.ProgramDAO#load(sg.edu.nus.iss.phoenix.radioprogram.entity.ProgramSlot)
	 */
	@Override
	public void loadProgramSlot(ProgramSlot valueObject) throws NotFoundException,
			SQLException {

		if (valueObject.getDuration()== null || valueObject.getDateOfProgram() == null) {
			// System.out.println("Can not select without Primary-Key!");
			throw new NotFoundException("Can not select without Primary-Key!");
		}

		String sql = "SELECT * FROM `program-slot` WHERE (`duration` = ? ) AND (`dateOfProgram` = ?); ";
		PreparedStatement stmt = null;
		openConnection();
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, valueObject.getDuration().toString());
                        stmt.setString(2, valueObject.getDateOfProgram().toString());
                        
			singleQuery(stmt, valueObject);

		} finally {
			if (stmt != null)
				stmt.close();
			closeConnection();
		}
	}

	/* (non-Javadoc)
	 * @see sg.edu.nus.iss.phoenix.radioprogram.dao.impl.ProgramDAO#loadAll()
	 */
	@Override
	public List<ProgramSlot> loadAllProgramSlot() throws SQLException {
		openConnection();
		String sql = "SELECT * FROM `program-slot` ORDER BY `dateOfProgram`, `startTime` ASC; ";
		List<ProgramSlot> searchResults = listQuery(connection
				.prepareStatement(sql));
		closeConnection();
		System.out.println("record size"+searchResults.size());
		return searchResults;
	}

	/* (non-Javadoc)
	 * @see sg.edu.nus.iss.phoenix.radioprogram.dao.impl.ProgramDAO#create(sg.edu.nus.iss.phoenix.radioprogram.entity.ProgramSlot)
	 */
	@Override
	public synchronized void createProgramSlot(ProgramSlot valueObject)
			throws SQLException {

		String sql = "";
		PreparedStatement stmt = null;
		openConnection();
		try {
                    
			sql = "INSERT INTO `program-slot`"
                                + "(`year`,`weeknum`,`dateOfProgram`,`startTime`, `duration`, `program-name`, `producer-name`, `presenter-name`) "
                                + "VALUES (?,?,?,?,?,?,?,?); ";
			stmt = connection.prepareStatement(sql);
                        stmt.setInt(1, valueObject.getYear());
                        stmt.setInt(2, valueObject.getWeekNum());
			stmt.setString(3, Util.dateToString(valueObject.getDateOfProgram()));
			stmt.setTime(4, valueObject.getStartTime());
			stmt.setTime(5, valueObject.getDuration());
                        stmt.setString(6, valueObject.getProgram().getName());
			stmt.setString(7, valueObject.getProducer().getName());
                        stmt.setString(8, valueObject.getPresenter().getName());
                        
			int rowcount = databaseUpdate(stmt);
			if (rowcount != 1) {
				// System.out.println("PrimaryKey Error when updating DB!");
				throw new SQLException("PrimaryKey Error when updating DB!");
			}
                } catch(SQLException ex){
                    ex.printStackTrace();
                    throw ex;
		} finally {
			if (stmt != null)
				stmt.close();
			closeConnection();
		}
	}

	/* (non-Javadoc)
	 * @see sg.edu.nus.iss.phoenix.radioprogram.dao.impl.ProgramDAO#save(sg.edu.nus.iss.phoenix.radioprogram.entity.ProgramSlot)
	 */
	@Override
	public void saveProgramSlot(ProgramSlot valueObject) throws NotFoundException,
			SQLException {

		String sql = "UPDATE `program-slot` SET `program-name` = ?, `producer-name` = ?, `presenter-name` = ?,`duration` = ? "
                        + "WHERE (`year` = ? ) "
                        + "AND (`weekNum` = ? ) "
                        + "AND (`dateOfProgram` = ? ) "
                        + "AND (`startTime` = ?); ";
		PreparedStatement stmt = null;
		openConnection();
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, valueObject.getProgram().getName());
			stmt.setString(2, valueObject.getProducer().getName());
			stmt.setString(3, valueObject.getPresenter().getName());
                        stmt.setTime(4, valueObject.getDuration());
                        stmt.setInt(5, valueObject.getYear());
                        stmt.setInt(6, valueObject.getWeekNum());
			stmt.setString(7, Util.dateToString(valueObject.getDateOfProgram()));
                        stmt.setTime(8, valueObject.getStartTime());
                        
			int rowcount = databaseUpdate(stmt);
			if (rowcount == 0) {
				// System.out.println("Object could not be saved! (PrimaryKey not found)");
				throw new NotFoundException(
						"Object could not be saved! (PrimaryKey not found)");
			}
			if (rowcount > 1) {
				// System.out.println("PrimaryKey Error when updating DB! (Many objects were affected!)");
				throw new SQLException(
						"PrimaryKey Error when updating DB! (Many objects were affected!)");
			}
		} 
                catch(Exception ex){
                    ex.printStackTrace();
		} 
                finally {
			if (stmt != null)
				stmt.close();
			closeConnection();
		}
	}

	/* (non-Javadoc)
	 * @see sg.edu.nus.iss.phoenix.radioprogram.dao.impl.ProgramDAO#delete(sg.edu.nus.iss.phoenix.radioprogram.entity.ProgramSlot)
	 */
	@Override
	public void deleteProgramSlot(ProgramSlot valueObject) throws NotFoundException,
			SQLException {

		if (valueObject.getDateOfProgram() == null || valueObject.getStartTime()== null) {
			// System.out.println("Can not select without Primary-Key!");
			throw new NotFoundException("Can not select without Primary-Key!");
		}

		String sql = "DELETE FROM `program-slot` "
                        + "WHERE (`year` = ? ) "
                        + "AND (`weekNum` = ? ) "
                        + "AND (`dateOfProgram` = ? ) "
                        + "AND (`startTime` = ?); ";
		PreparedStatement stmt = null;
		openConnection();
		try {
			stmt = connection.prepareStatement(sql);
                        stmt.setInt(1, valueObject.getYear());
                        stmt.setInt(2, valueObject.getWeekNum());
			stmt.setString(3, Util.dateToString(valueObject.getDateOfProgram()));
                        stmt.setTime(4, valueObject.getStartTime());

			int rowcount = databaseUpdate(stmt);
			if (rowcount == 0) {
				// System.out.println("Object could not be deleted (PrimaryKey not found)");
				throw new NotFoundException(
						"Object could not be deleted! (PrimaryKey not found)");
			}
			if (rowcount > 1) {
				// System.out.println("PrimaryKey Error when updating DB! (Many objects were deleted!)");
				throw new SQLException(
						"PrimaryKey Error when updating DB! (Many objects were deleted!)");
			}
		} finally {
			if (stmt != null)
				stmt.close();
			closeConnection();
		}
	}

	/* (non-Javadoc)
	 * @see sg.edu.nus.iss.phoenix.radioprogram.dao.impl.ProgramDAO#deleteAll(java.sql.Connection)
	 */
	@Override
	public void deleteAllProgramSlot(Connection conn) throws SQLException {

		String sql = "DELETE FROM `program-slot`";
		PreparedStatement stmt = null;
		openConnection();
		try {
			stmt = connection.prepareStatement(sql);
			int rowcount = databaseUpdate(stmt);
			System.out.println(""+rowcount);
		} finally {
			if (stmt != null)
				stmt.close();
			closeConnection();
			
		}
	}

	/* (non-Javadoc)
	 * @see sg.edu.nus.iss.phoenix.radioprogram.dao.impl.ProgramDAO#countAll()
	 */
	@Override
	public int countAllProgramSlot() throws SQLException {

		String sql = "SELECT count(*) FROM `radio-program`";
		PreparedStatement stmt = null;
		ResultSet result = null;
		int allRows = 0;
		openConnection();
		try {
			stmt = connection.prepareStatement(sql);
			result = stmt.executeQuery();

			if (result.next())
				allRows = result.getInt(1);
		} finally {
			if (result != null)
				result.close();
			if (stmt != null)
				stmt.close();
			closeConnection();
		}
		return allRows;
	}

	/* (non-Javadoc)
	 * @see sg.edu.nus.iss.phoenix.radioprogram.dao.impl.ProgramDAO#searchMatching(sg.edu.nus.iss.phoenix.radioprogram.entity.ProgramSlot)
	 */
	@Override
	public List<ProgramSlot> searchMatching(SPSearchObject valueObject) throws SQLException {

		List<ProgramSlot> searchResults = new ArrayList<ProgramSlot>();
		openConnection();
		boolean first = true;
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM `program-slot` WHERE  1=1 ");

		if (valueObject.getName() != null) {
			if (first) {
				first = false;
			}
			sql.append("AND `program-name` LIKE '").append(valueObject.getName())
					.append("%' ");
		}
		if (valueObject.getYear()!= null) {
			if (first) {
				first = false;
			}
			sql.append("AND `year` = '").append(Integer.parseInt(valueObject.getYear()))
					.append("%' ");
		}
		if (valueObject.getWeek()!= null) {
			if (first) {
				first = false;
			}
			sql.append("AND `weeknum` = '").append(Integer.parseInt(valueObject.getWeek()))
					.append("%' ");
		}

		sql.append("ORDER BY `dateOfProgram` ASC ");

		// Prevent accidential full table results.
		// Use loadAll if all rows must be returned.
		if (first)
			searchResults = new ArrayList<ProgramSlot>();
		else
			searchResults = listQuery(connection.prepareStatement(sql
					.toString()));
		closeConnection();
		return searchResults;
	}

	/**
	 * databaseUpdate-method. This method is a helper method for internal use.
	 * It will execute all database handling that will change the information in
	 * tables. SELECT queries will not be executed here however. The return
	 * value indicates how many rows were affected. This method will also make
	 * sure that if cache is used, it will reset when data changes.
	 * 
	 * @param stmt
	 *            This parameter contains the SQL statement to be executed.
	 */
	protected int databaseUpdate(PreparedStatement stmt) throws SQLException {

		int result = stmt.executeUpdate();

		return result;
	}

	/**
	 * databaseQuery-method. This method is a helper method for internal use. It
	 * will execute all database queries that will return only one row. The
	 * result set will be converted to valueObject. If no rows were found,
	 * NotFoundException will be thrown.
	 * 
	 * @param stmt
	 *            This parameter contains the SQL statement to be executed.
	 * @param valueObject
	 *            Class-instance where resulting data will be stored.
	 */
	protected void singleQuery(PreparedStatement stmt, ProgramSlot valueObject)
			throws NotFoundException, SQLException {

//		ResultSet result = null;
//		openConnection();
//		try {
//			result = stmt.executeQuery();
//
//			if (result.next()) {
//
//				valueObject.setName(result.getString("name"));
//				valueObject.setDescription(result.getString("desc"));
//				valueObject.setTypicalDuration(result
//						.getTime("typicalDuration"));
//
//			} else {
//				// System.out.println("ProgramSlot Object Not Found!");
//				throw new NotFoundException("ProgramSlot Object Not Found!");
//			}
//		} finally {
//			if (result != null)
//				result.close();
//			if (stmt != null)
//				stmt.close();
//			closeConnection();
//		}
	}

	/**
	 * databaseQuery-method. This method is a helper method for internal use. It
	 * will execute all database queries that will return multiple rows. The
	 * result set will be converted to the List of valueObjects. If no rows were
	 * found, an empty List will be returned.
	 * 
	 * @param stmt
	 *            This parameter contains the SQL statement to be executed.
	 */
	protected List<ProgramSlot> listQuery(PreparedStatement stmt) throws SQLException {
                System.out.println("listquery1");
		ArrayList<ProgramSlot> searchResults = new ArrayList<ProgramSlot>();
		ResultSet result = null;
		openConnection();
		try {
			result = stmt.executeQuery();

			while (result.next()) {
				ProgramSlot temp = createProgramSlotVO();
                                
                                temp.setProgram(new RadioProgram(result.getString("program-name")));
				temp.setYear(result.getInt("year"));
                                temp.setWeekNum(result.getInt("weekNum"));
                                temp.setDateOfProgram(result.getDate("dateOfProgram"));
				temp.setStartTime(result.getTime("startTime"));
                                temp.setDuration(result.getTime("duration"));
                                temp.setProducer(new Producer(result.getString("producer-name")));
                                temp.setPresenter(new Presenter(result.getString("presenter-name")));

                                searchResults.add(temp);
			}

		} finally {
			if (result != null)
				result.close();
			if (stmt != null)
				stmt.close();
			closeConnection();
		}
                System.out.println("listquery2");
		return (List<ProgramSlot>) searchResults;
	}

	private void openConnection() {
		try {
			Class.forName(DBConstants.COM_MYSQL_JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
                        this.connection = phoenix.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void closeConnection() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        
        protected void annualSingleQuery(PreparedStatement stmt, AnnualSchedule valueObject)
			throws NotFoundException, SQLException {

		ResultSet result = null;
		openConnection();
		try {
			result = stmt.executeQuery();

			if (result.next()) {

				valueObject.setYear(result.getInt("year"));
				valueObject.setAssignedBy(result.getString("assignedBy"));
				
			} else {
				// System.out.println("ProgramSlot Object Not Found!");
				throw new NotFoundException("AnnualSchedule Object Not Found!");
			}
		} finally {
			if (result != null)
				result.close();
			if (stmt != null)
				stmt.close();
			closeConnection();
		}
	}
        
        protected List<AnnualSchedule> annualListQuery(PreparedStatement stmt) 
                throws SQLException{
            ArrayList<AnnualSchedule> searchResults = new ArrayList<AnnualSchedule>();
            ResultSet result = null;
            openConnection();
            try {
                result = stmt.executeQuery();

                while (result.next()) {
                    AnnualSchedule temp = createAnnualScheduleVO();
                    temp.setYear(Integer.parseInt(result.getString("year")));
                    temp.setAssignedBy(result.getString("assingedBy"));
                    
//                    String sql = "SELECT * FROM `weekly-schedule` WHERE `year` = ? ORDER BY `weeknum` DESC;";
//                    PreparedStatement stm = null;
//                    stm = connection.prepareStatement(sql);
//                    stm.setInt(1,temp.getYear());
//                    List<WeeklySchedule> weekList = weeklyListQuery(stm);
//                    if(!weekList.isEmpty()){
//                        temp.setListOfWeeklySchedule(weekList);
//                    }
                    
                    searchResults.add(temp);
                }

            } finally {
                if (result != null)
                        result.close();
                if (stmt != null)
                        stmt.close();
                closeConnection();
            }

            return (List<AnnualSchedule>) searchResults;
        }
        
        @Override
        public AnnualSchedule createAnnualScheduleVO(){
                return new AnnualSchedule();
        }
        
        @Override
        public WeeklySchedule createWeeklyScheduleVO(){
            return new WeeklySchedule();
        }

        @Override
        public void createAnnualSchedule(int year,String name) 
                throws SQLException  {
            String  sql = "INSERT INTO `annual-schedule` (`year`, `assingedBy`) VALUES (?,?); ";
            PreparedStatement stmt=null;
            openConnection();
            try{
                stmt = this.connection.prepareStatement(sql);
                stmt.setInt(1,year);
                stmt.setString(2,name);

                int rowcount = databaseUpdate(stmt);

            }finally {
                if (stmt != null)
                    stmt.close();
                closeConnection();
            }
        }

        @Override
        public void createWeeklySchedule(int year_number,int max_week_number,
                String name) throws SQLException{

            String  sql = "INSERT INTO `weekly-schedule` (`year`, `weeknum`,`assignedBy`) VALUES (?,?,?); ";
            PreparedStatement stmt=null;
            openConnection();
            try {
                for (int i=1;i<=max_week_number;i++){
                    stmt = this.connection.prepareStatement(sql);
                    stmt.setInt(1,year_number);
                    stmt.setInt(2,i);
                    stmt.setString(3,name);

                    int rowcount = databaseUpdate(stmt);
                }
                
            } finally {
                if (stmt != null)
                    stmt.close();
                closeConnection();
            }
        }
    

    @Override
    public void loadAnnualSchedule(AnnualSchedule valueObject) throws NotFoundException, SQLException {
        if (valueObject.getYear() == 0 || valueObject.getAssignedBy() == null) {
			// System.out.println("Can not select without Primary-Key!");
			throw new NotFoundException("Can not select without Primary-Key!");
		}

		String sql = "SELECT * FROM `annual-schedule` WHERE (`year` = ? ) AND (`assignedBy` = ?); ";
		PreparedStatement stmt = null;
		openConnection();
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, Integer.toString(valueObject.getYear()));
                        stmt.setString(2, valueObject.getAssignedBy());
                        
			annualSingleQuery(stmt, valueObject);

		} finally {
			if (stmt != null)
				stmt.close();
			closeConnection();
		}
    }

    @Override
    public List<AnnualSchedule> loadAllAnnualSchedule() throws SQLException {
        openConnection();
        String sql = "SELECT * FROM `annual-schedule` ORDER BY `year` ASC; ";
	List<AnnualSchedule> searchResults = annualListQuery(connection
                .prepareStatement(sql));
	closeConnection();
	System.out.println("record size"+searchResults.size());
        return searchResults;
    }
    
    protected void weeklySingleQuery(PreparedStatement stmt, WeeklySchedule valueObject)
		throws NotFoundException, SQLException {
        System.out.println("mid1 weekschedule");
	ResultSet result = null;
	openConnection();
	try {
		result = stmt.executeQuery();

		if (result.next()) {

			valueObject.setYear(result.getInt("year"));
                        valueObject.setWeek(result.getInt("weeknum"));
			valueObject.setAssignedBy(result.getString("assignedBy"));
			System.out.println("mid2 weekschedule");
                        String sql = "SELECT * FROM `program-slot` WHERE `year` = ? AND `weeknum` = ? ORDER BY `dateOfProgram` DESC;";
                        PreparedStatement stm = null;
                        stm = connection.prepareStatement(sql);
                        stm.setInt(1,valueObject.getYear());
                        stm.setInt(2,valueObject.getWeek());
                        List<ProgramSlot> slotList = listQuery(stm);
                        System.out.println(slotList.size());
                        if(!slotList.isEmpty()){
                            valueObject.setListOfProgramSlot(slotList);
                        }
		} else {
				// System.out.println("ProgramSlot Object Not Found!");
                    System.out.println("not found");
				throw new NotFoundException("WeeklySchedule Object Not Found!");
			}
                
                        System.out.println("mid3 weekschedule");
	} finally {
		if (result != null)
			result.close();
		if (stmt != null)
			stmt.close();
		closeConnection();
	}
    }
        
        protected List<WeeklySchedule> weeklyListQuery(PreparedStatement stmt) 
                throws SQLException{
            ArrayList<WeeklySchedule> searchResults = new ArrayList<WeeklySchedule>();
            ResultSet result = null;
            openConnection();
            try {
                result = stmt.executeQuery();

                while (result.next()) {
                    WeeklySchedule temp = createWeeklyScheduleVO();

                    temp.setYear(result.getInt("year"));
                    temp.setWeek(result.getInt("weeknum"));
                    temp.setAssignedBy(result.getString("assignedBy"));

                    searchResults.add(temp);
                }

            } finally {
                    if (result != null)
                            result.close();
                    if (stmt != null)
                            stmt.close();
                    closeConnection();
            }
            return (List<WeeklySchedule>) searchResults;
        }

    
    @Override
    public List<ProgramSlot> searchScheduledProgramSlot(int year, int week) 
            throws NotFoundException, SQLException {
        String sql = "SELECT * FROM `program-slot` "
                + "WHERE `year` = ? "
                + "AND `weeknum` = ? "
                + "ORDER BY `year`, `weeknum`, `dateOfProgram` DESC; ";
        PreparedStatement stmt = null;
        List<ProgramSlot> searchResults = null;
        openConnection();
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, year);
            stmt.setInt(2, week);
            searchResults = listQuery(stmt);
        
        }   catch (SQLException ex) {
                Logger.getLogger(ScheduleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null)
		stmt.close();
        }
        
        closeConnection();
        return searchResults;
    }

    @Override
    public WeeklySchedule getWeeklySchedule(int year, int week) throws NotFoundException, SQLException {
        String sql = "SELECT * FROM `weekly-schedule` WHERE (`year` = ? and `weeknum` = ?); ";
	PreparedStatement stmt = null;
        WeeklySchedule valueObject = new WeeklySchedule(year,week);
        System.out.println("start weekschedule");
	openConnection();
	try {
		stmt = connection.prepareStatement(sql);
		stmt.setInt(1, year);
                stmt.setInt(2, week);
                System.out.println("mid weekschedule");        
		weeklySingleQuery(stmt, valueObject);
                
	} catch(NotFoundException e){
//                return null;               
        } finally {
            if (stmt != null)
		stmt.close();
        }
        
//        valueObject.setListOfProgramSlot(searchScheduledProgramSlot(dateOfWeek));
	closeConnection();
        System.out.println("return weekschedule");
        return valueObject;
    }

    @Override
    public void loadWeeklySchedule(WeeklySchedule valueObject) throws NotFoundException, SQLException {
        if (valueObject.getYear() == 0 || valueObject.getWeek() == 0 ) {
			// System.out.println("Can not select without Primary-Key!");
			throw new NotFoundException("Can not select without Primary-Key!");
		}

	String sql = "SELECT * FROM `weekly-schedule` WHERE (`year` = ? ) AND (`weeknum` = ?); ";
	PreparedStatement stmt = null;
	openConnection();
	try {
		stmt = connection.prepareStatement(sql);
		stmt.setInt(1, valueObject.getYear());
                stmt.setInt(2, valueObject.getWeek());
                        
		weeklySingleQuery(stmt, valueObject);

	} finally {
            if (stmt != null)
		stmt.close();
        }
        sql = "SELECT * FROM `program-slot` ORDER BY `dateOfProgram`, `startTime` ASC; ";
	List<ProgramSlot> searchResults = listQuery(connection
			.prepareStatement(sql));
        
	closeConnection();
    }

    @Override
    public List<WeeklySchedule> loadAllWeeklySchedule() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AnnualSchedule getAnnualSchedule(int year) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void copyWeeklySchedule(ArrayList<ProgramSlot> srcSlots, ArrayList<ProgramSlot> destSlots) throws SQLException 
    {
            String  sql = "INSERT INTO `program-slot`"
                                + "(`year`,`weeknum`,`dateOfProgram`,`startTime`, `duration`, `program-name`, `producer-name`, `presenter-name`) "
                                + "VALUES (?,?,?,?,?,?,?,?); ";

            PreparedStatement stmt=null;
            openConnection();
            try {
                for (int i=0;i<srcSlots.size();i++){
                    stmt = this.connection.prepareStatement(sql);
                    stmt.setInt(1, destSlots.get(i).getYear());
                    stmt.setInt(2, destSlots.get(i).getWeekNum());
                    stmt.setString(3, Util.dateToString(destSlots.get(i).getDateOfProgram()));
                    stmt.setTime(4, srcSlots.get(i).getStartTime());
                    stmt.setTime(5, srcSlots.get(i).getDuration());
                    stmt.setString(6, srcSlots.get(i).getProgram().getName());
                    stmt.setString(7, srcSlots.get(i).getProducer().getName());
                    stmt.setString(8, srcSlots.get(i).getPresenter().getName());

                    int rowcount = databaseUpdate(stmt);
                }
                
            } finally {
                if (stmt != null)
                    stmt.close();
                closeConnection();
            }
    }

    @Override
    public void deleteAllProgramSlotByWeek(int year, int weeknum) throws SQLException {
            String sql = "DELETE FROM `program-slot` "
                        + "WHERE (`year` = ? ) "
                        + "AND (`weekNum` = ? ) ";

            PreparedStatement stmt=null;
            openConnection();
            try {
                    stmt = this.connection.prepareStatement(sql);
                    stmt.setInt(1, year);
                    stmt.setInt(2, weeknum);

                    int rowcount = databaseUpdate(stmt);
                
            } finally {
                if (stmt != null)
                    stmt.close();
                closeConnection();
            }
    }

}
