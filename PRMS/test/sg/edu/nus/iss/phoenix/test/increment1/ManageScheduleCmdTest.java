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
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.schedule.controller.ManageScheduleCmd;
import sg.edu.nus.iss.phoenix.schedule.dao.impl.ScheduleDAOImpl;

/**
 *
 * @author XIE JIABAO
 */
public class ManageScheduleCmdTest {
    private ManageScheduleCmd manageScheduleCmd = new ManageScheduleCmd();
    private User user;
    private HttpSession session;
    private HttpServletRequest req;
    private RequestDispatcher rd;
    private HttpServletResponse resp;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
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
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        user = mock(User.class);
        session = mock(HttpSession.class);
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        rd = mock(RequestDispatcher.class);
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void performSuccTest() throws Exception {
        when(user.hasRole("manager"))
                .thenReturn(true);
        
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        when(req.getParameter("year"))
                .thenReturn("2015");
        when(req.getParameter("weekNum"))
                .thenReturn("38");
        when(req.getRequestDispatcher("managesc"))
                .thenReturn(rd);
        // perform test
        String forwardPath;
        try {
            forwardPath = manageScheduleCmd.perform(null, req, resp);
        } catch (Exception ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        verify(req).getParameter("year");
        verify(req).getParameter("weekNum");
        
        verify(req).setAttribute("year", 2015);
        verify(req).setAttribute("current_week", 38);
        verify(rd).forward(req, resp);
        assertEquals("forwardPath", "", forwardPath);
    }
}
