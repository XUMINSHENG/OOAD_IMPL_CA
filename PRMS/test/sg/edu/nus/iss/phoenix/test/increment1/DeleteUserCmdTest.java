/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.test.increment1;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.user.controller.DeleteUserCmd;
import sg.edu.nus.iss.phoenix.user.controller.EnterUserDetailsCmd;
import sg.edu.nus.iss.phoenix.user.delegate.UserDelegate;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;
import sg.edu.nus.iss.phoenix.user.entity.Producer;
import sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDaoImpl;
import sg.edu.nus.iss.phoenix.presenter.dao.impl.PresenterDAOImpl;
import sg.edu.nus.iss.phoenix.producer.dao.impl.ProducerDAOImpl;

/**
 *
 * @author Suteja
 */
public class DeleteUserCmdTest {

    private DeleteUserCmd addUser = new DeleteUserCmd();
    private EnterUserDetailsCmd enterUser = new EnterUserDetailsCmd();
    private User user;
    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher rd;
    private String userID;
    private Object connection;
    private UserDaoImpl udimpl = new UserDaoImpl();
    private PresenterDAOImpl presimpl = new PresenterDAOImpl();
    private ProducerDAOImpl prodimpl = new ProducerDAOImpl();

    public DeleteUserCmdTest() {
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
            
            UserTransaction utx = mock(UserTransaction.class);
            ic.createSubcontext("java:");
            ic.createSubcontext("java:comp");
            ic.bind("java:comp/UserTransaction",utx);
            
        } catch (NamingException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage().toString());
        }
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        user = mock(User.class);
        session = mock(HttpSession.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        rd = mock(RequestDispatcher.class);

    }

    @After
    public void tearDown() throws NotFoundException, SQLException {
       

    }

    public void deleteUser(User user_test) throws Exception {
         UserDelegate del = new UserDelegate();
         
         
         del.processDeassign(user_test.getId());
         
    }
    //CreateUser - to help in testing of deletion
    public void createUser(User user_test) throws Exception {
        
        UserDelegate del = new UserDelegate();
       
        Presenter presenter = new Presenter();
        Producer producer = new Producer();
        
        del.processCreate(user_test);
        
    }
    private ArrayList<Role> createRoles(final String roles) {
        ArrayList<Role> roleList = new ArrayList<Role>();
        String[] _r = roles.trim().split(":");
        for (String r : _r) {
            roleList.add(new Role(r.trim()));
        }
        return (roleList);
    }
     
    
   @Test
    public void Test1_CheckPageNavigation() throws Exception {
        //Step 1: Create a user
        User user_test = new User();
        String name="User1User";
        String address="clementi";
        String date = "2012-09-09";
        String roleList = "presenter";
        String id = "User1User";
        String password="password";
        
        user_test.setAddress(address);
        user_test.setId(id);
        user_test.setJoiningDate(date);
        user_test.setName(name);
        user_test.setPassword(password);
        user_test.setRoles(createRoles(roleList));        
        createUser(user_test);
        
        String[] role = new String[1];
        role[0] = "presenter";
        DeleteUserCmd del = new DeleteUserCmd();
        
        when(user.hasRole("admin")).thenReturn(true);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("name"))
                .thenReturn(name);
        when(request.getParameterValues("rolelist"))
                .thenReturn(role); 
        when(request.getParameter("address"))
                .thenReturn(address);
        when(request.getParameter("password"))
                .thenReturn(password);
        when(request.getParameter("joiningdate"))
                .thenReturn(date);
        when(request.getParameter("ins"))
                .thenReturn("true");
      
         
        String forwardPath;
        try {
            forwardPath = del.perform(null, request, response);
        } catch (Exception ex) {
            fail("When calling delete perform");
            return;
        }

        
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
        verify(request).setAttribute(nameCaptor.capture(), valueCaptor.capture());

        assertEquals("forwardPath", "/pages/cruduser.jsp", forwardPath);

        
    }
    
    @Test
    public void Test2_CheckUserFlagValue() throws Exception {
        
        User user_test = new User();
        String name="User_Userflag";
        String address="clementi";
        String date = "2012-09-09";
        String roleList = "presenter";
        String id = "User_Userflag";
        String password="password";
        
        
        user_test.setAddress(address);
        user_test.setId(id);
        user_test.setJoiningDate(date);
        user_test.setName(name);
        user_test.setPassword(password);
        user_test.setRoles(createRoles(roleList));        
        createUser(user_test);
        
        String[] role = new String[1];
        role[0] = "presenter";
        DeleteUserCmd del = new DeleteUserCmd();
        
        when(user.hasRole("admin")).thenReturn(true);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("name"))
                .thenReturn(name);
        when(request.getParameterValues("rolelist"))
                .thenReturn(role); 
        when(request.getParameter("address"))
                .thenReturn(address);
        when(request.getParameter("password"))
                .thenReturn(password);
        when(request.getParameter("joiningdate"))
                .thenReturn(date);
        when(request.getParameter("ins"))
                .thenReturn("true");
      
        String forwardPath;
        try {
            forwardPath = del.perform(null, request, response);
        } catch (Exception ex) {
            fail("When calling delete perform");
            return;
        }
        
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
        verify(request).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("N", udimpl.checkIfActive(user_test));

    }
    
    @Test
    public void Test3_CheckPresenterFlagValue() throws Exception {
        
        User user_test = new User();
        String name="User_Presflag";
        String address="clementi";
        String date = "2012-09-09";
        String roleList = "presenter";
        String id = "User_Presflag";
        String password="password";
        
        
        user_test.setAddress(address);
        user_test.setId(id);
        user_test.setJoiningDate(date);
        user_test.setName(name);
        user_test.setPassword(password);
        user_test.setRoles(createRoles(roleList));        
        createUser(user_test);
        
        String[] role = new String[1];
        role[0] = "presenter";
        DeleteUserCmd del = new DeleteUserCmd();
        
        when(user.hasRole("admin")).thenReturn(true);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("name"))
                .thenReturn(name);
        when(request.getParameterValues("rolelist"))
                .thenReturn(role); 
        when(request.getParameter("address"))
                .thenReturn(address);
        when(request.getParameter("password"))
                .thenReturn(password);
        when(request.getParameter("joiningdate"))
                .thenReturn(date);
        when(request.getParameter("ins"))
                .thenReturn("true");
      
        String forwardPath;
        try {
            forwardPath = del.perform(null, request, response);
        } catch (Exception ex) {
            fail("When calling delete perform");
            return;
        }
        
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
        verify(request).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("N", presimpl.checkIfActive(user_test.getId()));

    }
    
    
    
    @Test
    public void Test4_CheckProducerFlagValue() throws Exception {
        
        User user_test = new User();
        String name="User_Prodflag";
        String address="clementi";
        String date = "2012-09-09";
        String roleList = "producer";
        String id = "User_Prodflag";
        String password="password";
        
        
        user_test.setAddress(address);
        user_test.setId(id);
        user_test.setJoiningDate(date);
        user_test.setName(name);
        user_test.setPassword(password);
        user_test.setRoles(createRoles(roleList));        
        createUser(user_test);
        
        String[] role = new String[1];
        role[0] = "producer";
        DeleteUserCmd del = new DeleteUserCmd();
        
        when(user.hasRole("admin")).thenReturn(true);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("name"))
                .thenReturn(name);
        when(request.getParameterValues("rolelist"))
                .thenReturn(role); 
        when(request.getParameter("address"))
                .thenReturn(address);
        when(request.getParameter("password"))
                .thenReturn(password);
        when(request.getParameter("joiningdate"))
                .thenReturn(date);
        when(request.getParameter("ins"))
                .thenReturn("true");
      
        String forwardPath;
        try {
            forwardPath = del.perform(null, request, response);
        } catch (Exception ex) {
            fail("When calling delete perform");
            return;
        }
        
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
        verify(request).setAttribute(nameCaptor.capture(), valueCaptor.capture());
        
        assertEquals("N", prodimpl.checkIfActive(user_test.getId()));

    }
}


 