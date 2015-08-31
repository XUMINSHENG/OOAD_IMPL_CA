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
        <h2>Nocturne Framework is up <%= new java.util.Date() %></h2>
        
        <table border="1"> 
            <tr>
                <th>Mapping</th>
                <th>Class</th>
            </tr>
            
            <c:forEach var="s" items="${mappings.keySet()}">
                <tr>
                    <td><c:out value="${s}"/></td>
                    <td><c:out value="${mappings.get(s)}"/></td> 
                </tr>
            </c:forEach> 
            </table>
        </body>
    </html>
