/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.test.increment1;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
import sg.edu.nus.iss.phoenix.schedule.controller.DeleteProgramSlotCmd;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.dao.impl.ScheduleDAOImpl;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;
import sg.edu.nus.iss.phoenix.user.entity.Producer;
import sg.edu.nus.iss.phoenix.util.Util;
import sg.edu.nus.iss.phoenix.schedule.controller.EnterProgramSlotDetailsCmd;

/**
 *
 * @author Liu Xinzhuo
 */
public class EnterProgramSlotDetailsCmdTest {

    private EnterProgramSlotDetailsCmd EnterProgramSlotDetailsCmd = new EnterProgramSlotDetailsCmd();
    private User user;
    private HttpSession session;
    private HttpServletRequest req;
    private RequestDispatcher rd;
    private HttpServletResponse resp;

    @BeforeClass
    public static void before() {

        // rcarver - setup the jndi context and the datasource
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
    public static void after() {

    }

    @Before
    public void Setup() {
        user = mock(User.class);
        session = mock(HttpSession.class);
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        rd = mock(RequestDispatcher.class);
    }

    /**
     *
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Test
    public void performAddSuccTest() throws ServletException, IOException {
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("22:00:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");
        when(req.getRequestDispatcher("managesc"))
                .thenReturn(rd);
        
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");
        
        verify(req).setAttribute("year",2015);
        verify(req).setAttribute("current_week",40);
        verify(rd).forward(req, resp);
        assertEquals("forwardPath", "", forwardPath);
    }    
    
    @Test
    public void performModifySuccTest() throws ServletException, IOException {
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-02");
        when(req.getParameter("startTime"))
                .thenReturn("00:30:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("false");
        when(req.getRequestDispatcher("managesc"))
                .thenReturn(rd);
        
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");
        
        verify(req).setAttribute("year",2015);
        verify(req).setAttribute("current_week",40);
        verify(rd).forward(req, resp);
        assertEquals("forwardPath", "", forwardPath);
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
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "You do not have the privileges to perform this operation", 
                valueCaptor.getValue());
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
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "You do not have the privileges to perform this operation", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performEmptyDateOfProgramTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("");
        when(req.getParameter("startTime"))
                .thenReturn("01:00:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Null Input Error!", 
                valueCaptor.getValue());
    }
    
    
    
    @Test
    public void performInvalidDateOfProgramTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-13-61");
        when(req.getParameter("startTime"))
                .thenReturn("01:00:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Invalid input", 
                valueCaptor.getValue());
    }

    @Test
    public void performFormatErrDateOfProgramTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("abcd-ef-gh");
        when(req.getParameter("startTime"))
                .thenReturn("01:00:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Invalid input", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performEmptyStartTimeTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Null Input Error!", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performInvalidStartTimeTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("25:90:100");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Invalid input", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performFormatErrStartTimeTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("ab-ce-df");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Invalid input", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performEmptyDurationTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("01:00:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Null Input Error!", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performInvalidDurationTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("01:00:00");
        when(req.getParameter("name"))
                .thenReturn("");
        when(req.getParameter("duration"))
                .thenReturn("99:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Null Input Error!", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performFormatErrDurationTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("01:00:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:50:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Duration Format Error! It should be 'HH:30:00'or 'HH:00:00'! ", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performEmptyProgramNameTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("01:00:00");
        when(req.getParameter("name"))
                .thenReturn("");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Null Input Error!", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performInvalidProgramNameTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("01:00:00");
        when(req.getParameter("name"))
                .thenReturn("XXXXXXAAAAAA");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Invalid input", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performEmptyProducerTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("01:00:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Null Input Error!", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performInvalidProduceTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("01:00:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wallyXXXXXX");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Invalid input", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performEmptyPresenterTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("01:00:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Null Input Error!", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performInvalidPresenterTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("01:00:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbertXXXXX");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Invalid input", 
                valueCaptor.getValue());
    }
    @Test
    public void performEmptyInsertTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("01:00:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Null Input Error!", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performInvalidInsertTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("01:00:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("5959");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Invalid input", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performOverLapTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-02");
        when(req.getParameter("startTime"))
                .thenReturn("00:50:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Program Slot Over Lap!", 
                valueCaptor.getValue());
    }
    
    @Test
    public void performOverWeekTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        when(req.getSession())
                .thenReturn(session);
        
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-10-04");
        when(req.getParameter("startTime"))
                .thenReturn("23:50:00");
        when(req.getParameter("name"))
                .thenReturn("news");
        when(req.getParameter("duration"))
                .thenReturn("00:30:00");
        when(req.getParameter("producer"))
                .thenReturn("wally, the bludger");
        when(req.getParameter("presenter"))
                .thenReturn("dilbert, the hero");
        when(req.getParameter("ins"))
                .thenReturn("true");

        // perform test
        String forwardPath;
        try {
            forwardPath = EnterProgramSlotDetailsCmd.perform(null, req, resp);
        } catch (IOException | ServletException ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        verify(req).getParameter("name");
        verify(req).getParameter("duration");
        verify(req).getParameter("producer");
        verify(req).getParameter("presenter");
        verify(req).getParameter("ins");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "Program Slot Over Week!", 
                valueCaptor.getValue());
    }
    
}
