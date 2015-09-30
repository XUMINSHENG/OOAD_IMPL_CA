<%-- 
    Document   : addasc
    Created on : Sep 28, 2015, 4:27:56 PM
    Author     : AVISHEK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Annual Schedule</title>
    </head>
    <body>
        
        <h1>Enter The Year </h1>
        <form action="${pageContext.request.contextPath}/nocturne/addasc" method="POST" >
            <input type="text" name="year" /> ${requestScope.msg}
            <p> 
                <input type="submit" name="Submit" value="Submit year" />   
            </p>
        </form>
    </body>
</html>
