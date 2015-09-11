/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.test;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
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
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.dao.impl.ScheduleDAOImpl;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;
import sg.edu.nus.iss.phoenix.user.entity.Producer;
import sg.edu.nus.iss.phoenix.util.Util;
/**
 *
 * @author A0134434m
 */
public class DeleteProgramSlotCmdTest {
    
    //private static Connection connection;

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

    @Test
    public void deleteProgramSlot() throws Exception {

        DeleteProgramSlotCmd deleteProgramSlotCmd = new DeleteProgramSlotCmd();

        RequestDispatcher rd = mock(RequestDispatcher.class);                

        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter("dateOfProgram"))
                .thenReturn("2015-09-15");
        when(req.getParameter("startTime"))
                .thenReturn("07:30:00");
        
        when(req.getRequestDispatcher("/pages/crudsc.jsp"))
                .thenReturn(rd);

        HttpServletResponse resp = mock(HttpServletResponse.class);

        try {
            deleteProgramSlotCmd.perform(null, req, resp);
            
        } catch (Exception ex) {
            fail("When calling perform");
            return;
        }

        verify(req).getParameter("dateOfProgram");
        verify(req).getParameter("startTime");
        //verify(rd).forward(req, resp);

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
        
        assertEquals("ProgramSlotsData", "pss", stringCaptor.getValue());
        assertEquals("Remaining Slot" , expected, listCaptor.getValue());
        
    }

}
