package sg.edu.nus.iss.phoenix.authenticate.dao.impl;

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

import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.dao.DBConstants;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;

/**
 * User Data Access Object (DAO). This class contains all database handling that
 * is needed to permanently store and retrieve User object instances.
 */
public class UserDaoImpl implements UserDao {

    private static final String DELIMITER = ":";
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    private final static String dataSourceName = "jdbc/phoenix";
    private DataSource phoenix;
    Connection connection;

    /**
     * DAO Implementation Constructor. Used to initialize the data source which
     * contains the Phoenix resource pool.
     */
    public UserDaoImpl() {
        super();
        // TODO Auto-generated constructor stub
        try {
            this.phoenix = (DataSource) InitialContext.doLookup(dataSourceName);
        } catch (NamingException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * createValueObject-method. Creates a new object for the User class.
     *
     * @return new User object.
     */
    @Override
    public User createValueObject() {
        return new User();
    }

    /**
     * getObject-method. Creates a new User object and sets all its attributes.
     *
     * @param id
     * @return User object
     * @throws NotFoundException
     * @throws SQLException
     */
    @Override
    public User getObject(String id) throws NotFoundException, SQLException {

        User valueObject = createValueObject();
        valueObject.setId(id);
        load(valueObject);
        return valueObject;
    }

    /**
     * load-method. Get a Users from the user table
     *
     * @param valueObject
     * @throws NotFoundException
     * @throws SQLException
     *
     */
    @Override
    public void load(User valueObject) throws NotFoundException, SQLException {

        String sql = "SELECT * FROM user WHERE (id = ? ) ";
        PreparedStatement stmt = null;
        connection = openConnection();

        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, valueObject.getId());

            singleQuery(stmt, valueObject);

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
    }

    /**
     * loadAll-method. Get all the Users from the user table.
     *
     * @return List of users
     * @throws SQLException
     *
     */
    @Override
    public List<User> loadAll() throws SQLException {

        connection = openConnection();
        String sql = "SELECT * FROM user where isActive='Y' ORDER BY id ASC ";
        List<User> searchResults = listQuery(this.connection
                .prepareStatement(sql));
        System.out.println("exited loadAll()");
        return searchResults;
    }

    /**
     * create-method. Used to add a new User object into the user table.
     *
     * @param valueObject
     * @throws SQLException
     *
     */
    @Override
    public synchronized void create(User valueObject) throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        connection = openConnection();
        try {
            sql = "INSERT INTO user ( id, password, name,address,role, joining_date, isActive) VALUES (?, ?, ?, ?,?,?,?) ";
            stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, valueObject.getId());
            stmt.setString(2, valueObject.getPassword());
            stmt.setString(3, valueObject.getName());
            stmt.setString(4, valueObject.getAddress());
            ArrayList<Role> a_role = valueObject.getRoles();
            String s_role = "";
            for (int i = 0; i < a_role.size(); i++) {
                if (i > 0) {
                    s_role = s_role + ":";
                    s_role = s_role + a_role.get((i)).getRole().toString();

                } else {
                    s_role = s_role + a_role.get((i)).getRole().toString();

                }
            }

            stmt.setString(5, s_role);

            stmt.setString(6, valueObject.getJoiningDate());
            stmt.setString(7, "Y");
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

    /**
     * save-method. Used to update the User object in the user table.
     *
     * @param valueObject
     * @throws NotFoundException
     * @throws SQLException
     *
     */
    @Override
    public void save(User valueObject) throws NotFoundException, SQLException {
        String sql = "UPDATE user SET role = ?, address =?,  password = ?,  joining_date = ?  WHERE (id = ? ) ";

        PreparedStatement stmt = null;
        connection = openConnection();
        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setString(2, valueObject.getAddress());
            stmt.setString(3, valueObject.getPassword());
            stmt.setString(4, valueObject.getJoiningDate());
            ArrayList<Role> a_role = valueObject.getRoles();
            String s_role = "";
            for (int i = 0; i < a_role.size(); i++) {
                if (i > 0) {
                    s_role = s_role + ":";
                    s_role = s_role + a_role.get((i)).getRole().toString();

                } else {
                    s_role = s_role + a_role.get((i)).getRole().toString();

                }
            }

            stmt.setString(5, valueObject.getId());
            stmt.setString(1, s_role);

            int rowcount = databaseUpdate(stmt);
            if (rowcount == 0) {

                throw new NotFoundException(
                        "Object could not be saved! (PrimaryKey not found)");
            }
            if (rowcount > 1) {

                throw new SQLException(
                        "PrimaryKey Error when updating DB! (Many objects were affected!)");
            }

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
    }

    /**
     * delete-method. Used to delete an User object in the user table.
     *
     * @param valueObject
     * @throws NotFoundException
     * @throws SQLException
     *
     */
    @Override
    public void delete(User valueObject) throws NotFoundException, SQLException {

        String sql = "DELETE FROM user WHERE (id = ? ) ";
        PreparedStatement stmt = null;
        connection = openConnection();

        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, valueObject.getId());

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
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
    }

    /**
     * deleteAll-method. Used to delete all the User objects in the user table.
     * @throws SQLException
     *
     */
    @Override
    public void deleteAll() throws SQLException {

        String sql = "DELETE FROM user";
        PreparedStatement stmt = null;
        connection = openConnection();

        try {
            stmt = this.connection.prepareStatement(sql);
            int rowcount = databaseUpdate(stmt);
            System.out.println("Deleted rows :" + rowcount);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
    }

   /**
     * countAll-method. Used to get the total numbers of Users from the user table.
     * @return number of users(int)
     * @throws SQLException
     *
     */
    @Override
    public int countAll() throws SQLException {

        String sql = "SELECT count(*) FROM user";
        PreparedStatement stmt = null;
        connection = openConnection();
        ResultSet result = null;
        int allRows = 0;

        try {
            stmt = this.connection.prepareStatement(sql);
            result = stmt.executeQuery();

            if (result.next()) {
                allRows = result.getInt(1);
            }
        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
        return allRows;
    }

    /**
     * searchMatching-method. Used to get the matching user object for the given id.
     * @param uid
     * @return User Object
     * @throws SQLException
     */
    @Override
    public User searchMatching(String uid) throws SQLException {
        try {
            return (getObject(uid));
        } catch (NotFoundException ex) {
            logger.log(Level.WARNING, "Fail to find user: {0}", uid);
        }
        return (null);
    }
    
    /**
     * searchMatching-method. Used to get the matching user object for the given id.
     * @param valueObject
     * @return User Object
     * @throws SQLException
     */

    @Override
    public List<User> searchMatching(User valueObject) throws SQLException {

        List<User> searchResults;

        boolean first = true;
        StringBuffer sql = new StringBuffer("SELECT * FROM user WHERE 1=1 ");

        if (valueObject.getId() != "") {
            if (first) {
                first = false;
            }
            sql.append("AND id = ").append(valueObject.getId()).append(" ");
        }

        if (valueObject.getPassword() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND password LIKE '").append(valueObject.getPassword())
                    .append("%' ");
        }

        if (valueObject.getName() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND name LIKE '").append(valueObject.getName())
                    .append("%' ");
        }

        if (valueObject.getRoles().get(0).getRole() != null) {
            if (first) {
                first = false;
            }
            sql.append("AND role LIKE '")
                    .append(valueObject.getRoles().get(0).getRole())
                    .append("%' ");
        }

        sql.append("ORDER BY id ASC ");

        // Prevent accidential full table results.
        // Use loadAll if all rows must be returned.
        if (first) {
            searchResults = new ArrayList<User>();
        } else {
            searchResults = listQuery(this.connection.prepareStatement(sql
                    .toString()));
        }

        return searchResults;
    }

    /**
     * databaseUpdate-method. This method is a helper method for internal use.
     * It will execute all database handling that will change the information in
     * tables. SELECT queries will not be executed here however. The return
     * value indicates how many rows were affected. This method will also make
     * sure that if cache is used, it will reset when data changes.
     *
     * @param stmt This parameter contains the SQL statement to be executed.
     * @return
     * @throws java.sql.SQLException
     */
    protected int databaseUpdate(PreparedStatement stmt) throws SQLException {

        int result = stmt.executeUpdate();

        return result;
    }

    /**
     * databaseQuery-method. This method is a helper method for internal use. It
     * will execute all database queries that will return only one row. The
     * resultset will be converted to valueObject. If no rows were found,
     * NotFoundException will be thrown.
     *
     * @param stmt This parameter contains the SQL statement to be excuted.
     * @param valueObject Class-instance where resulting data will be stored.
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException
     * @throws java.sql.SQLException
     */
    protected void singleQuery(PreparedStatement stmt, User valueObject)
            throws NotFoundException, SQLException {

        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            if (result.next()) {

                valueObject.setId(result.getString("id"));
                valueObject.setPassword(result.getString("password"));
                valueObject.setName(result.getString("name"));
                valueObject.setAddress(result.getString("address"));
                valueObject.setRoles(createRoles(result.getString("role")));
                valueObject.setJoiningDate(result.getDate("joining_date").toString());

            } else {

                throw new NotFoundException("User Object Not Found!");
            }
        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
    }

    /**
     * databaseQuery-method. This method is a helper method for internal use. It
     * will execute all database queries that will return multiple rows. The
     * resultset will be converted to the List of valueObjects. If no rows were
     * found, an empty List will be returned.
     *
     * @param stmt This parameter contains the SQL statement to be executed.
     * @return
     * @throws java.sql.SQLException
     */
    protected List<User> listQuery(PreparedStatement stmt) throws SQLException {

        ArrayList<User> searchResults = new ArrayList<User>();
        ResultSet result = null;

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                User temp = createValueObject();
                temp.setId(result.getString("id"));
                temp.setPassword(result.getString("password"));
                temp.setName(result.getString("name"));
                temp.setAddress(result.getString("address"));
                temp.setRoles(createRoles(result.getString("role")));

                searchResults.add(temp);
            }

        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }

        return (List<User>) searchResults;
    }

    /**
     * createRoles-method. Used Get the List of Role object from roles string 
     * @param roles
     * @return ArrayList of Role object
     */
    private ArrayList<Role> createRoles(final String roles) {
        ArrayList<Role> roleList = new ArrayList<Role>();
        String[] _r = roles.trim().split(DELIMITER);
        for (String r : _r) {
            roleList.add(new Role(r.trim()));
        }
        return (roleList);
    }

    /**
     * openConnection_method. Used to create a new connection to MySQL Database
     * @return Connection
     */
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
    
     /**
     * closeConnection_method. Used to close a connection to MySQL Database
     * 
     */

    private void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * deassign-method. Used to assign a user as in-active in the User table.
     * @param user
     * @throws NotFoundException
     * @throws SQLException
     */
    @Override
    public void deassign(User user) throws NotFoundException, SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        connection = openConnection();
        try {
            sql = "UPDATE user SET isActive= ?  WHERE (id = ? ) ";
            stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, "N");
            stmt.setString(2, user.getId());
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

     /**
     * reassign-method. Used to assign a user as active in the User table.
     * @param user
     * @throws NotFoundException
     * @throws SQLException
     */
    @Override
    public void reassign(User user) throws NotFoundException, SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        connection = openConnection();
        try {
            sql = "UPDATE user SET isActive= ? , role=? WHERE (id = ? ) ";
            stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, "Y");
            stmt.setString(3, user.getId());

            ArrayList<Role> a_role = user.getRoles();
            String s_role = "";
            for (int i = 0; i < a_role.size(); i++) {
                if (i > 0) {
                    s_role = s_role + ":";
                    s_role = s_role + a_role.get((i)).getRole().toString();

                } else {
                    s_role = s_role + a_role.get((i)).getRole().toString();

                }
            }

            stmt.setString(2, s_role);
            int rowcount = databaseUpdate(stmt);
            if (rowcount != 1) {

                throw new SQLException("PrimaryKey Error when updating DB!");
            }

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
    }
}
