package sg.edu.nus.iss.phoenix.producer.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
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
import sg.edu.nus.iss.phoenix.producer.dao.ProducerDAO;
import sg.edu.nus.iss.phoenix.user.entity.Producer;

/**
 * User Data Access Object (DAO). This class contains all database handling that
 * is needed to permanently store and retrieve User object instances.
 */
public class ProducerDAOImpl implements ProducerDAO {

    private final static String dataSourceName = "jdbc/phoenix";
    private DataSource phoenix;
    Connection connection;

    public ProducerDAOImpl() {
        super();
        // TODO Auto-generated constructor stub
        try {
            this.phoenix = (DataSource) InitialContext.doLookup(dataSourceName);
        } catch (NamingException ex) {
            Logger.getLogger(ProducerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
      //  connection = openConnection();
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

    @Override
    public Producer createValueObject() {
        return new Producer();
    }

    @Override
    public Producer getObject(String id) throws NotFoundException, SQLException {
        Producer valueObject = createValueObject();
        valueObject.setUserId(id);
        load(valueObject);
        return valueObject;
    }

    @Override
    public void load(Producer valueObject) throws NotFoundException, SQLException {
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

    protected void singleQuery(PreparedStatement stmt, Producer valueObject)
            throws NotFoundException, SQLException {

        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            if (result.next()) {

                valueObject.setUserId(result.getString("id"));
                valueObject.setName(result.getString("name"));
                valueObject.setIsActive(result.getString("isActive"));

            } else {
                // System.out.println("User Object Not Found!");
                throw new NotFoundException("Producer Object Not Found!");
            }
        } finally {
            if (result != null) {
                result.close();
            }
            closeConnection();
        }
    }

    @Override
    public List<Producer> loadAll() throws SQLException {
        String sql = "SELECT * FROM Producer where isActive='Y'";
        List<Producer> searchResults = listQuery(this.connection
                .prepareStatement(sql));
        System.out.println("exited loadAll()");
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
    protected List<Producer> listQuery(PreparedStatement stmt) throws SQLException {

        ArrayList<Producer> searchResults = new ArrayList<Producer>();
        ResultSet result = null;

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                Producer temp = createValueObject();
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

        return (List<Producer>) searchResults;
    }

    @Override
    public void create(Producer valueObject) throws SQLException {
        String sql = "";
        PreparedStatement stmt = null;
        connection = openConnection();
        String yes = "Y"; //By default, have it as 'Y'

        try {
            sql = "INSERT INTO producer ( name, `user-id`, isActive) VALUES (?, ?, ?) ";
            stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, valueObject.getName());
            stmt.setString(2, valueObject.getUserId());
            stmt.setString(3, valueObject.getIsActive());

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
    public void save(Producer valueObject) throws NotFoundException, SQLException {

        String yes = "Y";
        String sql = "UPDATE producer SET name = ?, isActive =?  WHERE (`user-id` = ? ) ";
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
                sql = "INSERT INTO producer ( name, `user-id`, isActive) VALUES (?, ?, ?) ";
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
    public void delete(Producer valueObject) throws NotFoundException, SQLException {
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
    public List<Producer> searchMatching(Producer valueObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Producer searchMatching(String uid) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deassign(String id) throws NotFoundException, SQLException {
        String sql = "";
        PreparedStatement stmt = null;
        connection = openConnection();
        try {
            sql = "UPDATE producer SET isActive= ?  WHERE (`user-id` = ? ) ";
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
            sql = "UPDATE producer SET isActive= ?  WHERE (`user-id` = ? ) ";
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
    public List<Producer> searchByName(String name) throws SQLException {
        String sql = "SELECT * FROM Producer where isActive='Y' and name LIKE '%" + name + "%'";
        List<Producer> searchResults = listQuery(this.connection
                .prepareStatement(sql));
        System.out.println("exited loadAll()");
        return searchResults;
    }

}
