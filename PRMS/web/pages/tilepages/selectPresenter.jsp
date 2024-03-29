<%-- 
    Document   : selectPresenter
    Created on : Sep 29, 2015, 7:34:19 PM
    Author     : tanuj
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<fmt:setBundle basename="ApplicationResources" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="title.selectPresenter" /></title>
</head>
    <body>
	<h2>
		<fmt:message key="title.selectPresenter" />
	</h2>
	<form action="${pageContext.request.contextPath}/nocturne/selectPresenter"
		method=get>
		<center>
			<table class="framed">
				<tr>
					<th width="45%"><fmt:message key="caption.name" /></th>
					<th width="55%"><fmt:message key="caption.desc" /></th>
				</tr>
				<tr>
                                        <input type="hidden" name="dateOfProgram" value="${param["dateOfProgram"]}">
                                        <input type="hidden" name="startTime" value="${param["startTime"]}">
                                        <input type="hidden" name="program-name" value="${param["program-name"]}">
                                        <input type="hidden" name="presenter-name" value="${param["duration"]}">
                                        <input type="hidden" name="producer-name" value="${param["producer-name"]}">
                                        <input type="hidden" name="presenter-name" value="${param["presenter-name"]}">
                                        <input type="hidden" name="insert" value="${param["insert"]}">
					<td><fmt:message key="label.Presenter.name" /></td>
                                        <td><input type="text" name="name" size=45 maxlength=45 value="${name}"></td>
				</tr>
				<tr>
                                    <td colspan="2" align="center"><input type="submit" value="Submit"> <input
						type="reset" value="Reset"></td>
				</tr>
			</table>
		</center>

	</form>
           
        <table class="borderAll"> 
                                        <thead>
                
                            <tr>
                                 <th colspan="6">
                                   <h3>
                                <c:choose>
                                    <c:when test="${! empty  name}">
                                        Search '<c:out value="${name}"/>' Presenter Results
                                    </c:when>
                                    <c:otherwise>
                                        All Presenters
                                    </c:otherwise>    
                                </c:choose>
                            </h3>
                        </th>
                </tr>
            </thead>
            <tbody>
            <tr>
                <th><fmt:message key="label.Presenter.userId" /></th>
                <th><fmt:message key="label.Presenter.name" /></th>
                <th><fmt:message key="label.Select" /></th>	
            </tr>                    
            <c:choose>
                <c:when test="${! empty  selPresList}">
			<c:forEach var="presenter" items="${selPresList}" varStatus="status">
				<tr class="${status.index%2==0?'even':'odd'}">
					<td class="nowrap">${presenter.userId}</td>
					<td class="nowrap">${presenter.name}</td>
                                        <td>
                                            <c:url var="get" scope="page" value="/nocturne/addeditps">
                                            <c:param name="dateOfProgram" value="${param['dateOfProgram']}"/>
                                            <c:param name="startTime" value="${param['startTime']}"/>
                                            <c:param name="program-name" value="${param['program-name']}"/>
                                            <c:param name="duration" value="${param['duration']}"/>
                                            <c:param name="producer-name" value="${param['producer-name']}"/>
                                            <c:param name="presenter-name" value="${presenter.name}"/>
                                            <c:param name="insert" value="${param['insert']}"/>
                                            
                                            </c:url>
                                            <a href="${get}"><fmt:message key="label.Select"/></a>
                                        </td>
				</tr>
			</c:forEach>
                </c:when>
                <c:otherwise>
                    <tr><td colspan="6"><p style='color:grey;text-align: center;'><i>Presenter Not Found</i></p></td></tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </body>
</html>
