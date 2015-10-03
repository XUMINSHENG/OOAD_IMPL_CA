/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.test.increment1;

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
import javax.servlet.RequestDispatcher;
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
     */
    @Test
    public void performAddSuccTest() {
        
    }
}
