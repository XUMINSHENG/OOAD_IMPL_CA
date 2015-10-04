/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.test.increment1;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.util.ArrayList;
import java.util.List;
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
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.schedule.controller.ManageScheduleCmd;
import sg.edu.nus.iss.phoenix.schedule.dao.impl.ScheduleDAOImpl;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;

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
        when(req.getParameter("current_week"))
                .thenReturn("38");
        when(req.getAttribute("year"))
                .thenReturn("2015");
        when(req.getAttribute("current_week"))
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
        verify(req).getParameter("current_week");
        
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);
        
        
        verify(req, atLeast(1)).setAttribute(stringCaptor.capture(), valueCaptor.capture());
        verify(req, atLeast(1)).setAttribute("year", 2015);
        verify(req, atLeast(1)).setAttribute("current_week", 38);
        
        List<String> stringList = stringCaptor.getAllValues();
        String weeklyName = stringList.get(0);
        List<Object> valueList = valueCaptor.getAllValues();
        
        List<AnnualSchedule> expectedList = new ArrayList<AnnualSchedule>();
        AnnualSchedule expAnnualSchedule = new AnnualSchedule(2015);
        expAnnualSchedule.setAssignedBy("dilbert");
        expectedList.add(expAnnualSchedule);
        
        WeeklySchedule expWeeklySchedule = new WeeklySchedule(2015,38);
        
        assertEquals("forwardPath", "/pages/crudsc.jsp", forwardPath);
        assertEquals("WeeklySchedule Name", "ws", weeklyName);
        assertEquals("WeeklySchedule", expWeeklySchedule, (WeeklySchedule)valueList.get(0));
        assertEquals("AnnualSchedule Name", "yearlist", stringCaptor.getValue());
        assertEquals("AnnualSchedule List" , expectedList, valueCaptor.getValue());
        
    }
    
    @Test
    public void performEmptyYearTest(){
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        
        when(session.getAttribute("user"))
                .thenReturn(user);

        when(req.getSession())
                .thenReturn(session);

        // empty year
        when(req.getParameter("year"))
                .thenReturn("");
        when(req.getParameter("current_week"))
                .thenReturn("38");
        
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
      
        assertEquals("forwardPath", "/pages/crudsc.jsp", forwardPath);
    }
    
    @Test
    public void performInvlidYearTest(){
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        
        when(session.getAttribute("user"))
                .thenReturn(user);

        when(req.getSession())
                .thenReturn(session);

        // incorrect year
        when(req.getParameter("year"))
                .thenReturn("abcd");
        when(req.getParameter("current_week"))
                .thenReturn("38");

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
        
        assertEquals("forwardPath", "/pages/crudsc.jsp", forwardPath);
    }
    
    @Test
    public void performEmptyWeekTest(){
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        
        when(session.getAttribute("user"))
                .thenReturn(user);

        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("year"))
                .thenReturn("2015");
        // empty week
        when(req.getParameter("current_week"))
                .thenReturn("");
        
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
        verify(req).getParameter("current_week");
        
        assertEquals("forwardPath", "/pages/crudsc.jsp", forwardPath);
    }
    
    @Test
    public void performInvaidWeekTest(){
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        
        when(session.getAttribute("user"))
                .thenReturn(user);

        when(req.getSession())
                .thenReturn(session);

        when(req.getParameter("year"))
                .thenReturn("2015");
        // incorrect week
        when(req.getParameter("current_week"))
                .thenReturn("ab");
        
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
        verify(req).getParameter("current_week");
        
        assertEquals("forwardPath", "/pages/crudsc.jsp", forwardPath);
    }
    
    @Test
    public void performNotLogInTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        
        // user do not login
        when(session.getAttribute("user"))
                .thenReturn(null);

        when(req.getSession())
                .thenReturn(session);
        
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

//        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
//
//        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
//        assertEquals("varName", "errorMsg", nameCaptor.getValue());
//        assertEquals("errorMsg" , 
//                "You do not have the privileges to perform this operation", 
//                valueCaptor.getValue());
    }
    
    @Test
    public void performNoPrivilegeTest() throws Exception {
        // user do not have to role
        when(user.hasRole("manager"))
                .thenReturn(false);
        
        when(session.getAttribute("user"))
                .thenReturn(user);

        when(req.getSession())
                .thenReturn(session);

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

//        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
//
//        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
//        assertEquals("varName", "errorMsg", nameCaptor.getValue());
//        assertEquals("errorMsg" , 
//                "You do not have the privileges to perform this operation", 
//                valueCaptor.getValue());
    }
}
