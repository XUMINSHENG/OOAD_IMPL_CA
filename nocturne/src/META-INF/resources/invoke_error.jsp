<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="at.nocturne.core.*, java.util.*" %> 
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>FormMapPerform Error</h1>
        <table>
            <tr>
                <td>
                    Method:
                </td>
                <td>
                    <c:out value="${invoke_method}"/>
                </td>
            </tr>
            <tr>
                <td>
                    Class:
                </td>
                <td>
                    <c:out value="${invoke_class}"/>
                </td>
            </tr>
            <tr>
                <td>
                    Exception:
                </td>
                <td>
                    <c:out value="${invoke_exception}"/>
                </td>
            </tr>            
        </table>
    </body>
</html>
