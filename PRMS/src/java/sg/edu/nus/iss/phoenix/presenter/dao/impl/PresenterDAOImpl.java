package sg.edu.nus.iss.phoenix.presenter.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import sg.edu.nus.iss.phoenix.core.dao.DBConstants;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.presenter.dao.PresenterDAO;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;

/**
 * User Data Access Object (DAO). This class contains all database handling that
 * is needed to permanently store and retrieve User object instances.
 */
public class PresenterDAOImpl implements PresenterDAO {

    private static final String DELIMITER = ":";
    private static final Logger logger = Logger.getLogger(PresenterDAOImpl.class.getName());

    
    private DataSource phoenix;
    Connection connection;

    public PresenterDAOImpl() {
        super();
        // TODO Auto-generated constructor stub

        try {
            this.phoenix = (DataSource) InitialContext.doLookup(DBConstants.dataSourceName);
        } catch (NamingException ex) {
            Logger.getLogger(PresenterDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
//        connection = openConnection();
    }

    @Override
    public Presenter createValueObject() {
        return new Presenter();
    }

    @Override
    public Presenter getObject(String id) throws NotFoundException, SQLException {
        Presenter valueObject = createValueObject();
        valueObject.setUserId(id);
        load(valueObject);
        return valueObject;
    }

    @Override
    public void load(Presenter valueObject) throws NotFoundException, SQLException {
        String sql = "SELECT * FROM user WHERE (`id` = ? ) ";
        PreparedStatement stmt = null;
        connection = openConnection();

        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, valueObject.getUserId());

            singleQuery(stmt, valueObject);

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
    }

    protected void singleQuery(PreparedStatement stmt, Presenter valueObject)
            throws NotFoundException, SQLException {

        ResultSet result = null;

        try {
            this.connection = openConnection();
            result = stmt.executeQuery();

            if (result.next()) {

                valueObject.setUserId(result.getString("id"));
                valueObject.setName(result.getString("name"));
                valueObject.setIsActive(result.getString("isActive"));

            } else {
                // System.out.println("User Object Not Found!");
                throw new NotFoundException("Presenter Object Not Found!");
            }
        } finally {
            if (result != null) {
                result.close();
            }
            closeConnection();
        }
    }

    @Override
    public List<Presenter> loadAll() throws SQLException {
        List<Presenter> searchResults =null;
        try {
            this.connection = openConnection();
            
            String sql = "SELECT * FROM Presenter where isActive='Y'";
            searchResults = listQuery(this.connection
                .prepareStatement(sql));
            System.out.println("exited loadAll()");
        } finally {
            closeConnection();
        }
        
        return searchResults;
    }

    /**
     * databaseQuery-method. This method is a helper method for internal use. It
     * will execute all database queries that will return multiple rows. The
     * resultset will be converted to the List of valueObjects. If no rows were
     * found, an empty List will be returned.
     *
     * @param stmt This parameter contains the SQL statement to be excuted.
     */
    protected List<Presenter> listQuery(PreparedStatement stmt) throws SQLException {

        ArrayList<Presenter> searchResults = new ArrayList<Presenter>();
        ResultSet result = null;
        connection = openConnection();

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                Presenter temp = createValueObject();
                temp.setUserId(result.getString("user-id"));
                temp.setName(result.getString("name"));
                temp.setIsActive(result.getString("isActive"));
                searchResults.add(temp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }

        return (List<Presenter>) searchResults;
    }

    @Override
    public void create(Presenter valueObject) throws SQLException {
        String sql = "";
        PreparedStatement stmt = null;
        connection = openConnection();
        String yes = "Y"; //By default, have it as 'Y'

        try {
            sql = "INSERT INTO presenter ( name, `user-id`, isActive) VALUES (?, ?, ?) ";
            stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, valueObject.getName());
            stmt.setString(2, valueObject.getUserId());
            stmt.setString(3, yes);

            int rowcount = databaseUpdate(stmt);
            if (rowcount != 1) {
                // System.out.println("PrimaryKey Error when updating DB!");
                throw new SQLException("PrimaryKey Error when updating DB!");
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
    }

    protected int databaseUpdate(PreparedStatement stmt) throws SQLException {

        int result = stmt.executeUpdate();

        return result;
    }

    @Override
    public void save(Presenter valueObject) throws NotFoundException, SQLException {

        String yes = "Y";
        String sql = "UPDATE presenter SET name = ?, isActive =?  WHERE (`user-id` = ? ) ";
        //String sql = "UPDATE `program-slot` SET `program-name` = ?, `producer-name` = ?, `presenter-name` = ? WHERE (`dateOfProgram` = ? ) AND (`startTime` = ?); ";

        PreparedStatement stmt = null;
        connection = openConnection();
        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, valueObject.getName());
            stmt.setString(2, yes);
            stmt.setString(3, valueObject.getUserId());

            int rowcount = databaseUpdate(stmt);
            if (rowcount == 0) {

                sql = "INSERT INTO presenter ( name, `user-id`, isActive) VALUES (?, ?, ?) ";
                stmt = this.connection.prepareStatement(sql);
                stmt.setString(1, valueObject.getName());
                stmt.setString(2, valueObject.getUserId());
                stmt.setString(3, yes);

                int count = databaseUpdate(stmt);
                if (count == 0) {
                    throw new NotFoundException(
                            "Presenter could not be saved! (Duplicate PrimaryKey)");
                }

            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
    }

    @Override
    public void delete(Presenter valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Presenter> searchMatching(Presenter valueObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Presenter searchMatching(String uid) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Connection openConnection() {
        Connection conn = null;
        try {
            Class.forName(DBConstants.COM_MYSQL_JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            conn = phoenix.getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    private void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    protected int databaseUpdate(PreparedStatement stmt) throws SQLException {
//
//		int result = stmt.executeUpdate();
//
//		return result;
//	}
    @Override
    public void deassign(String id) throws NotFoundException, SQLException {
        String sql = "";
        PreparedStatement stmt = null;
        connection = openConnection();
        try {
            sql = "UPDATE presenter SET isActive= ?  WHERE (`user-id` = ? ) ";
            stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, "N");
            stmt.setString(2, id);
            int rowcount = databaseUpdate(stmt);
            if (rowcount != 1) {
                // System.out.println("PrimaryKey Error when updating DB!");
                throw new SQLException("PrimaryKey Error when updating DB!");
            }

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
    }

    @Override
    public void reassign(String id) throws NotFoundException, SQLException {
        String sql = "";
        PreparedStatement stmt = null;
        connection = openConnection();
        try {
            sql = "UPDATE presenter SET isActive= ?  WHERE (`user-id` = ? ) ";
            stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, "Y");
            stmt.setString(2, id);
            int rowcount = databaseUpdate(stmt);
            if (rowcount != 1) {
                // System.out.println("PrimaryKey Error when updating DB!");
                throw new SQLException("PrimaryKey Error when updating DB!");
            }

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
    }

    @Override
    public List<Presenter> searchByName(String name) throws SQLException {
        String sql = "SELECT * FROM Presenter where isActive='Y' and name LIKE '%" + name + "%'";
        connection = openConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        List<Presenter> searchResults = listQuery(stmt);
        System.out.println("exited loadAll()");
        closeConnection();
        return searchResults;
    }
    
    public String checkIfActive(String id) throws NotFoundException, SQLException {
        String sql = "SELECT isActive FROM presenter WHERE (`user-id` = ? ) ";
        PreparedStatement stmt = null;
        connection = openConnection();
        ResultSet result = null;
        String isActive = "N";

        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, id);
            result = stmt.executeQuery();
            String flag = result.toString();
            if (flag == "Y") {
                isActive = "Y";
            } else {
                isActive = "N";
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
        return isActive;

    }

}
