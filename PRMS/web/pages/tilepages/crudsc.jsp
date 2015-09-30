<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<link href="<c:url value='/css/main.css'/>" rel="stylesheet" type="text/css"/>
<fmt:setBundle basename="ApplicationResources" />
<title> <fmt:message key="title.crudsc"/> </title>
</head>
<body>
        <h1><fmt:message key="label.crudsc"/></h1>
        <c:url var="url" scope="page" value="/nocturne/addeditrp">
        		<c:param name="name" value=""/>
                <c:param name="description" value=""/>
                <c:param name="duration" value=""/>
                <c:param name="insert" value="true"/>
        </c:url>        
        <c:url var="url" scope="page" value="/nocturne/addasc">
                <c:param name="insert" value="true"/>
        </c:url>
        
        <c:url var="addurl" scope="page" value="/nocturne/addeditps">
        	<c:param name="dateOfProgram" value=""/>
                <c:param name="startTime" value=""/>
                <c:param name="duration" value=""/>
                <c:param name="program-name" value=""/>
                <c:param name="producer-name" value=""/>
                <c:param name="presenter-name" value=""/>
                <c:param name="insert" value="true"/>
        </c:url>
        
        <c:url var="cpurl" scope="page" value="/nocturne/copysc">
        	<c:param name="dateOfProgram" value=""/>
                <c:param name="startTime" value=""/>
                <c:param name="duration" value=""/>
                <c:param name="program-name" value=""/>
                <c:param name="producer-name" value=""/>
                <c:param name="presenter-name" value=""/>
                <c:param name="insert" value="false"/>
        </c:url>
        
        <a href="${url}"><fmt:message key="label.crudsc.createAnSc"/></a>
        &nbsp;
        &nbsp;
        <a href="${addurl}"><fmt:message key="label.crudsc.add"/></a>
        &nbsp;
        &nbsp;
        <a href="${url}"><fmt:message key="label.crudsc.copy"/></a>
        <br/><br/>
        
            <form action="${pageContext.request.contextPath}/nocturne/managesc"
		method=post>
		<center>
			<table class="framed">
				<tr>
                                    <th width="30%"><h3><fmt:message key="caption.schedule" /></h3></th>
                                        <th width="30%"></th>
                                        <th></th>
				</tr>
				<tr>
                                    <c:choose>
                                        <c:when test="${! empty yearlist}">
					<td><fmt:message key="fieldLabel.year" />
                                            <select name="year" style="width: 150px;">
                                                <option value="0"> --Please select year-- </option>
                                                <c:forEach var="item" items="${yearlist}" varStatus="loop">
                                                    <option value="${item.getYear()}">
                                                        <c:out value="${item.getYear()}" />
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <fmt:message key="fieldLabel.week" />
                                            <select name="week" style="width:150px;">
                                                <option value="0"> --Please select week-- </option>
                                                <c:forEach begin="1" end="52" varStatus="loop">
                                                    <option value="${loop.index}" >
                                                        <c:out value="${loop.index}" />
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td><input type="submit" value="Select week scheduel"></td>
                                        </c:when>
                                        <c:otherwise>
                                        <td colspan="3"><p style="color:grey;text-align: center;"><i>No Annual Schedule</i></p></td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                        </table>
		</center>
            </form>
        
    <c:if test="${(year != 0) && (week != 0)}">
        <table class="borderAll">
            <thead>
                <tr>
                    <th colspan="6">
                        <h3>
                            <c:out value="${ws.getYear()}"/> Year - <c:out value="${ws.getWeek()}"/> Week Schedule (<c:out value="${ws.getStartDate()}" /> - <c:out value="${ws.getEndDate()}" />)
                        </h3>
                    </th>
                    <th>
                        <c:if test="${! empty ws.getAssignedBy()}">
                            <p>Assigned By: <c:out value="${ws.getAssignedBy()}"/></p>
                        </c:if>
                    </th>
                </tr>
            </thead>
            <tbody>
            <tr>
                <th><fmt:message key="label.crudsc.date"/></th>
                <th><fmt:message key="label.radioprogram.name"/></th>
                <th><fmt:message key="label.crudsc.startTime"/></th>
                <th><fmt:message key="label.crudsc.duration"/></th>
                <th><fmt:message key="label.programslot.presenter"/></th>
                <th><fmt:message key="label.programslot.producer"/></th>
                <th><fmt:message key="label.crudsc.edit"/> <fmt:message key="label.crudsc.delete"/></th>
            </tr>
            <c:choose>
            <c:when test="${! empty ws.getListOfProgramSlot()}">    
            <c:forEach var="pitem" items="${ws.getListOfProgramSlot()}" varStatus="status">
                <tr class="${status.index%2==0?'even':'odd'}">
                    <td class="nowrap"><fmt:formatDate pattern="yyyy-MM-dd" value="${pitem.dateOfProgram}" /></td>
                    <td class="nowrap"><c:out value="${pitem.program.name}" /></td>
                    <td class="nowrap"><fmt:formatDate pattern="hh:mm:ss" value="${pitem.startTime}" /></td>
                    <td class="nowrap"><fmt:formatDate pattern="HH:mm:ss" value="${pitem.duration}" /></td>
                    <td class="nowrap"><c:out value="${pitem.producer.name}" /></td>
                    <td class="nowrap"><c:out value="${pitem.presenter.name}" /></td>
                    <td class="nowrap">
                        <c:url var="updurl" scope="page" value="/nocturne/addeditps">
                            <c:param name="dateOfProgram" value="${pitem.dateOfProgram}"/>
                            <c:param name="startTime" value="${pitem.startTime}"/>
                            <c:param name="duration" value="${pitem.duration}"/>
                            <c:param name="program-name" value="${pitem.program.name}"/>
                            <c:param name="producer-name" value="${pitem.producer.name}"/>
                            <c:param name="presenter-name" value="${pitem.presenter.name}"/>
                            <c:param name="insert" value="false"/>
                        </c:url>
                        <a href="${updurl}"><fmt:message key="label.crudsc.edit"/></a>
                        &nbsp;&nbsp;&nbsp;
                        <c:url var="delurl" scope="page" value="/nocturne/deleteps">
                            <c:param name="dateOfProgram" value="${pitem.dateOfProgram}" />
                            <c:param name="startTime" value="${pitem.startTime}"/>
                        </c:url>
                        <a href="${delurl}"><fmt:message key="label.crudsc.delete"/></a>
                    </td>
                </tr>
            </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td colspan="8"><p style="color:grey;text-align: center"><i>No Schedule</i></p></td></tr>
            </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </c:if>
        
</body>
</html>