package sg.edu.nus.iss.phoenix.presenter.dao.impl;

import sg.edu.nus.iss.phoenix.authenticate.dao.impl.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.presenter.dao.PresenterDAO;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;

/**
 * User Data Access Object (DAO). This class contains all database handling that
 * is needed to permanently store and retrieve User object instances.
 */
public class PresenterDAOImpl implements PresenterDAO {

    private static final String DELIMITER = ":";
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    Connection connection;

    public PresenterDAOImpl() {
        super();
        // TODO Auto-generated constructor stub
        connection = openConnection();
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

        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, valueObject.getUserId());

            singleQuery(stmt, valueObject);

        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    protected void singleQuery(PreparedStatement stmt, Presenter valueObject)
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
                throw new NotFoundException("Presenter Object Not Found!");
            }
        } finally {
            if (result != null) {
                result.close();
            }
        }
    }

    @Override
    public List<Presenter> loadAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Presenter valueObject) throws SQLException {
        String sql = "";
        PreparedStatement stmt = null;
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
                if(count == 0){
                    throw new NotFoundException(
                      "Presenter could not be saved! (Duplicate PrimaryKey)");
                }

               
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
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
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/phoenix", "phoenix",
                    "password");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
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
        }
    }
    
     @Override
    public void reassign(String id) throws NotFoundException, SQLException {
        String sql = "";
        PreparedStatement stmt = null;
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
        }
    }

}
