/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.test;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
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
import javax.servlet.http.HttpSession;
import org.junit.Before;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.dao.impl.ScheduleDAOImpl;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;
import sg.edu.nus.iss.phoenix.user.entity.Producer;
import sg.edu.nus.iss.phoenix.util.Util;

/**
 *
 * @author Xu Minsheng
 */
public class DeleteProgramSlotCmdTest {
    
    // Action Class is stateless
    private DeleteProgramSlotCmd deleteProgramSlotCmd = new DeleteProgramSlotCmd();
    private User user;
    private HttpSession session;
    private HttpServletRequest req;
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
    public void Setup(){
        user = mock(User.class);
        session = mock(HttpSession.class);
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
    }

    @Test
    public void performSuccTest() throws Exception {

        
        when(user.hasRole("manager"))
                .thenReturn(true);

        
        when(session.getAttribute("user"))
                .thenReturn(user);
        
        
        when(req.getSession())
                .thenReturn(session);
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-09-15");
        when(req.getParameter("startTime"))
                .thenReturn("07:30:00");

        // perform test
        String forwardPath;
        try {
            forwardPath = deleteProgramSlotCmd.perform(null, req, resp);
        } catch (Exception ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");

        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);

        verify(req).setAttribute(stringCaptor.capture(), listCaptor.capture());
        
        List<ProgramSlot> expected = new ArrayList();
        ProgramSlot expProgramSlot = new ProgramSlot();
        expProgramSlot.setDateOfProgram(Util.stringToDate("2015-09-15"));
        expProgramSlot.setStartTime(Util.stringToTime("19:30:00"));
        expProgramSlot.setDuration(Util.stringToTime("00:30:00"));
        
//        // RadioProgram
//        RadioProgram expRadioProgram = new RadioProgram();
//        expRadioProgram.setAll("news", 
//                "full news broadcasted four times a day", 
//                Util.stringToTime("00:30:00"));
//        expProgramSlot.setProgram(expRadioProgram);
//        
//        // Producer
//        Producer expProducer = new Producer();
//        expProducer.setName("wally, the bludger");
//        expProgramSlot.setProducer(expProducer);
//        
//        // Presenter
//        Presenter expPresenter = new Presenter();
//        expProducer.setName("dilbert, the hero");
//        expProgramSlot.setPresenter(expPresenter);
 
        // add to list
        expected.add(expProgramSlot);
        assertEquals("forwardPath", "/pages/crudsc.jsp", forwardPath);
        assertEquals("ProgramSlotsData", "pss", stringCaptor.getValue());
        assertEquals("Remaining Slot" , expected, listCaptor.getValue());
        
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
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-09-15");
        when(req.getParameter("startTime"))
                .thenReturn("07:30:00");

        // perform test
        String forwardPath;
        try {
            forwardPath = deleteProgramSlotCmd.perform(null, req, resp);
        } catch (Exception ex) {
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
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-09-15");
        when(req.getParameter("startTime"))
                .thenReturn("07:30:00");

        // perform test
        String forwardPath;
        try {
            forwardPath = deleteProgramSlotCmd.perform(null, req, resp);
        } catch (Exception ex) {
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
    public void performEmptyDateTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        
        when(session.getAttribute("user"))
                .thenReturn(user);

        when(req.getSession())
                .thenReturn(session);

        // empty date
        when(req.getParameter("dateOfProgram"))
                .thenReturn("");
        when(req.getParameter("startTime"))
                .thenReturn("07:30:00");

        // perform test
        String forwardPath;
        try {
            forwardPath = deleteProgramSlotCmd.perform(null, req, resp);
        } catch (Exception ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        verify(req).getParameter("dateOfProgram");

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
    public void performInvalidDateTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        
        when(session.getAttribute("user"))
                .thenReturn(user);

        when(req.getSession())
                .thenReturn(session);

        // incorrect date
        when(req.getParameter("dateOfProgram"))
                .thenReturn("a-02-30");
        when(req.getParameter("startTime"))
                .thenReturn("07:30:00");

        // perform test
        String forwardPath;
        try {
            forwardPath = deleteProgramSlotCmd.perform(null, req, resp);
        } catch (Exception ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        verify(req).getParameter("dateOfProgram");

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
    public void performEmptyTimeTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        
        when(session.getAttribute("user"))
                .thenReturn(user);

        when(req.getSession())
                .thenReturn(session);

        // empty time
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-09-15");
        when(req.getParameter("startTime"))
                .thenReturn("");

        // perform test
        String forwardPath;
        try {
            forwardPath = deleteProgramSlotCmd.perform(null, req, resp);
        } catch (Exception ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");

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
    public void performInvalidTimeTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        
        when(session.getAttribute("user"))
                .thenReturn(user);

        when(req.getSession())
                .thenReturn(session);

        // incorrect time
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-02-30");
        when(req.getParameter("startTime"))
                .thenReturn("a7:30:00");

        // perform test
        String forwardPath;
        try {
            forwardPath = deleteProgramSlotCmd.perform(null, req, resp);
        } catch (Exception ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");

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
    public void performDataNotFoundTest() throws Exception {
        
        when(user.hasRole("manager"))
                .thenReturn(true);
        
        when(session.getAttribute("user"))
                .thenReturn(user);

        when(req.getSession())
                .thenReturn(session);

        // no such data
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-02-01");
        when(req.getParameter("startTime"))
                .thenReturn("07:30:00");

        // perform test
        String forwardPath;
        try {
            forwardPath = deleteProgramSlotCmd.perform(null, req, resp);
        } catch (Exception ex) {
            fail("When calling perform");
            return;
        }
        
        // verify test
        verify(req).getSession();
        verify(session).getAttribute("user");
        verify(user).hasRole("manager");
        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(req).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                new NotFoundException(
                        "Object could not be deleted! (PrimaryKey not found)").
                        getMessage(), 
                valueCaptor.getValue());
    }
    
}
