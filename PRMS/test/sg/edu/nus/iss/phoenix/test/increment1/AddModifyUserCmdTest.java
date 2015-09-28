/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.test.increment1;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sg.edu.nus.iss.phoenix.user.controller.AddModifyUserCmd;
import static org.junit.Assert.*;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.schedule.dao.impl.ScheduleDAOImpl;

/**
 *
 * @author achyut
 */
public class AddModifyUserCmdTest {
    
    private AddModifyUserCmd addUser = new AddModifyUserCmd();
    private User user;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    
    public AddModifyUserCmdTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        try {
            // Create initial context
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES, 
                "org.apache.naming");            
            InitialContext ic = new InitialContext();
            ic.createSubcontext("jdbc");
           
            // Construct DataSource
            MysqlDataSource ds = new MysqlDataSource();
            ds.setURL("jdbc:mysql://localhost:3306/phoenix");
            ds.setUser("phoenix");
            ds.setPassword("password");
            
            ic.bind("jdbc/phoenix", ds);
            
        } catch (NamingException ex) {
            Logger.getLogger(ScheduleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of perform method, of class AddModifyUserCmd.
     */
    @Test
    public void testPerform() throws Exception {
        System.out.println("perform");
        String string = "";
        HttpServletRequest hsr = null;
        HttpServletResponse hsr1 = null;
        AddModifyUserCmd instance = new AddModifyUserCmd();
        String expResult = "";
        String result = instance.perform(string, hsr, hsr1);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
