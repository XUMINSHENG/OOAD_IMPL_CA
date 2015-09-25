<%-- 
    Document   : setupuser
    Created on : 19 Sep, 2015, 4:28:58 PM
    Author     : achyut
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

        <fmt:setBundle basename="ApplicationResources" />

        <title><fmt:message key="title.setupuser" /></title>
    </head>
    
    <body>
        <form action="${pageContext.request.contextPath}/nocturne/enteruser" method=post>
		<center>
			<table cellpadding=4 cellspacing=2 border=0>
				<!-- <tr>
					<th width="30%"><fmt:message key="label.cruduser.name" /></th>
					<th width="45%"><fmt:message key="label.cruduser.role" /></th>
					<th width="25%"><fmt:message key="label.cruduser.address" /></th>
				</tr> -->
				<tr>
					<td><fmt:message key="label.cruduser.name" /></td>
					<td><c:if test="${param['insert'] == 'true'}">
							<input type="text" name="name" value="${param['name']}" size=15
								maxlength=20>
							<input type="hidden" name="ins" value="true" />
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="name" value="${param['name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
						</c:if></td>
				</tr>
				<tr>
					<td><fmt:message key="label.cruduser.role" /></td>
<!--					<td><input type="text" name="role"
						value="${param['role']}" size=15 maxlength=20></td>-->
                                        <td><select multiple name="rolelist">
                                                <option value="presenter">Presenter</option>
                                                <option value="producer">Producer</option>
                                                
                                            </select>
				</tr>
				<tr>
					<td><fmt:message key="label.cruduser.address" /></td>
					<td><input type="text" name="address"
						value="${param['address']}" size=15 maxlength=20></td>
				</tr>
                                 <tr>
					<td><fmt:message key="label.cruduser.password" /></td>
					<td><input type="password" name="password"
						value="${param['role']}" size=15 maxlength=20></td>
				</tr>
                                 <tr>
					<td><fmt:message key="label.cruduser.joiningdate" /></td>
					<td><input type="text" name="joiningdate"
						value="${param['joiningdate']}" size=15 maxlength=20>YYYY-MM-DD</td>
				</tr>
			</table>
                        <input type="submit" value="Submit"> 
                        <input type="reset" value="Reset">
		</center>
		
	</form>

    </body>
</html>
