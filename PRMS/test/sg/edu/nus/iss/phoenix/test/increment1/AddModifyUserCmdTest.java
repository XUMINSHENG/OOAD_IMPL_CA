/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.test.increment1;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import static org.junit.Assert.*;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.user.dao.impl.UserDAOImpl;
import sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDaoImpl;
import sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDaoImpl;

/**
 *
 * @author Suteja
 */
public class AddModifyUserCmdTest {

    private AddModifyUserCmd addUser = new AddModifyUserCmd();
    private EnterUserDetailsCmd enterUser = new EnterUserDetailsCmd();
    private User user;
    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String userID;

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
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
//        try {
//            Runtime.getRuntime().exec("cmd /c start loadDefaultDb.bat");
//        } catch (IOException ex) {
//            Logger.getLogger(AddModifyUserCmdTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @After
    public void tearDown() throws NotFoundException, SQLException {
//        UserDaoImpl ud = new UserDaoImpl();
//        ud.delete(user);

    }

    /**
     * Test of perform method, of class AddModifyUserCmd.
     */
    @Test
    public void testPerform() throws Exception {
//        System.out.println("perform");
//        String string = "";
//        HttpServletRequest request = null;
//        HttpServletResponse response = null;
//        AddModifyUserCmd instance = new AddModifyUserCmd();
//        String expResult = "";
//        String result = instance.perform(string, request, response);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

//    @Test
//    public void Test2() throws Exception {
//
//        String[] role = new String[1];
//        role[0] = "presenter";
//
//        userID = "Supper";
//
//        when(user.hasRole("admin"))
//                .thenReturn(true);
//        when(session.getAttribute("user"))
//                .thenReturn(user);
//        when(request.getSession())
//                .thenReturn(session);
//        when(request.getParameter("name"))
//                .thenReturn("Supper");
//        when(request.getParameterValues("rolelist"))
//                .thenReturn(role);
//        when(request.getParameter("address"))
//                .thenReturn("clementi");
//        when(request.getParameter("password"))
//                .thenReturn("Supper");
//        when(request.getParameter("joiningdate"))
//                .thenReturn("2015-01-02");
//        when(request.getParameter("ins"))
//                .thenReturn("true");
//
//        String forwardPath;
//        try {
//            forwardPath = enterUser.perform(null, request, response);
//        } catch (Exception ex) {
//            fail("When calling perform");
//            return;
//        }
//
//        verify(request).getSession();
//        verify(session).getAttribute("user");
//        verify(request).getParameter("name");
//        verify(request).getParameter("rolelist");
//        verify(request).getParameter("address");
//        verify(request).getParameter("password");
//        verify(request).getParameter("joiningdate");
//        verify(request).getParameter("ins");
//
//        User local_user = new User();
//        String roleList = "";
//        roleList = "presenter";
//        local_user.setAddress("clementi");
//        local_user.setName("Supper");
//        local_user.setJoiningDate("2015-01-02");
//        local_user.setPassword("Supper");
//        local_user.setRoles(createRoles(roleList));
//
//        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
//        verify(request).setAttribute(stringCaptor.capture(), listCaptor.capture());
//
//        List<User> expected = new ArrayList();
//        User expUser = new User();
//        expUser.setName("Supper");
//        expUser.setAddress("clementi");
//        expUser.setRoles(createRoles(roleList));
//        expUser.setPassword("Supper");
//        expUser.setJoiningDate("2015-01-02");
//
//        assertEquals("forwardPath", "/pages/cruduser.jsp", forwardPath);
//        assertEquals("UserData", "rps", stringCaptor.getValue());
//        assertEquals("User", expected, listCaptor.getValue());
//
//    }

    @Test
    public void Test3_IncompleteInputFields() throws Exception {
        
        String[] role = new String[1];
        role[0] = "presenter";
        
        when(user.hasRole("admin")).thenReturn(true);       
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("name"))
                .thenReturn("gtyuio");
       when(request.getParameterValues("rolelist"))
           .thenReturn(role);
        when(request.getParameter("address"))
                .thenReturn("");
        when(request.getParameter("password"))
                .thenReturn("");
        when(request.getParameter("joiningdate"))
                .thenReturn("");
        when(request.getParameter("ins"))
                .thenReturn("true");

       String forwardPath;
        try {
            forwardPath = enterUser.perform(null, request, response);
        } catch (Exception ex) {
            fail("When calling perform");
            return;
        }

//        verify(request).getSession();
//        verify(session).getAttribute("user");
//        verify(request).getParameter("name");
////        verify(request).getParameter("rolelist");
//        verify(request).getParameter("address");
//        verify(request).getParameter("password");
//        verify(request).getParameter("joiningdate");
//        verify(request).getParameter("ins");
//
//        User local_user = new User();
//        String roleList = "";
//        roleList = "presenter";
//        local_user.setAddress("");
//        local_user.setName("Gundi");
//        local_user.setJoiningDate("");
//        local_user.setPassword("");
//        local_user.setRoles(createRoles(roleList));
//
//        
//        List<User> expected = new ArrayList();
//        User expUser = new User();
//        expUser.setName("Gundi");
//        expUser.setAddress("");
//        expUser.setRoles(createRoles(roleList));
//        expUser.setPassword("");
//        expUser.setJoiningDate("");
//
         ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);        
        verify(request).setAttribute(nameCaptor.capture(), valueCaptor.capture());
//        
         assertEquals("forwardPath", "/pages/error.jsp", forwardPath);
        assertEquals("varName", "errorMsg", nameCaptor.getValue());
        assertEquals("errorMsg" , 
                "One or more input fields is missing, please fill all the details while creating a user!", 
                valueCaptor.getValue());

    }

    private ArrayList<Role> createRoles(final String roles) {
        ArrayList<Role> roleList = new ArrayList<Role>();
        String[] _r = roles.trim().split(":");
        for (String r : _r) {
            roleList.add(new Role(r.trim()));
        }
        return (roleList);
    }

}