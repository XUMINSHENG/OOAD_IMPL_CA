<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<fmt:setBundle basename="ApplicationResources" />

<title><fmt:message key="title.setupps" /></title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/nocturne/enterps" method=post>
		<center>
			<table cellpadding=6 cellspacing=2 border=0>
				<!--Date-->
                                <tr>
					<td><fmt:message key="label.programslot.dateOfProgram" /></td>
					<td><c:if test="${param['insert'] == 'true'}">
							<input type="text" name="name" value="${param['name']}" size=15
								maxlength=20>
							<input type="hidden" name="ins" value="true" />
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="name" value="${param['name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
						</c:if>
                                        </td>
				</tr>
                                
                                <!--Start Time-->
                                <tr>
					<td><fmt:message key="label.programslot.startTime" /></td>
					<td><c:if test="${param['insert'] == 'true'}">
							<input type="text" name="name" value="${param['name']}" size=15
								maxlength=20>
							<input type="hidden" name="ins" value="true" />
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="name" value="${param['name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
						</c:if>
                                        </td>
				</tr>
                                
                                <!--Duration-->
                                <tr>
					<td><fmt:message key="label.programslot.duration" /></td>
					<td><c:if test="${param['insert'] == 'true'}">
							<input type="text" name="name" value="${param['name']}" size=15
								maxlength=20>
							<input type="hidden" name="ins" value="true" />
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="name" value="${param['name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
						</c:if>
                                        </td>
				</tr>
                                
                                <!--Program-->
                                <tr>
					<td><fmt:message key="label.programslot.name" /></td>
					<td><c:if test="${param['insert'] == 'true'}">
							<input type="text" name="name" value="${param['name']}" size=15
								maxlength=20>
							<input type="hidden" name="ins" value="true" />
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="name" value="${param['name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
						</c:if>
                                        </td>
				</tr>
                                
                                <!--Producer-->
                                <tr>
					<td><fmt:message key="label.programslot.producer" /></td>
					<td><c:if test="${param['insert'] == 'true'}">
							<input type="text" name="name" value="${param['name']}" size=15
								maxlength=20>
							<input type="hidden" name="ins" value="true" />
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="name" value="${param['name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
						</c:if>
                                        </td>
				</tr>
                                
                                <!--Presenter-->
                                <tr>
					<td><fmt:message key="label.programslot.presenter" /></td>
					<td><c:if test="${param['insert'] == 'true'}">
							<input type="text" name="name" value="${param['name']}" size=15
								maxlength=20>
							<input type="hidden" name="ins" value="true" />
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="name" value="${param['name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
						</c:if>
                                        </td>
				</tr>
                                
                                
				
			</table>
		</center>
		<input type="submit" value="Submit"> <input type="reset"
			value="Reset">
	</form>

</body>
</html>