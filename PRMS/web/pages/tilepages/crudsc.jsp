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
        <a href="${url}"><fmt:message key="label.crudsc.createAnSc"/></a>
        &nbsp;
        &nbsp;
        <a href="${url}"><fmt:message key="label.crudsc.add"/></a>
        <br/><br/>
        <table class="borderAll">
            <tr>
                <th><fmt:message key="label.crudsc.date"/></th>
                <th><fmt:message key="label.crudsc.startTime"/></th>
                <th><fmt:message key="label.crudsc.duration"/></th>
                <th><fmt:message key="label.crudsc.edit"/> <fmt:message key="label.crudsc.delete"/></th>
            </tr>
            <c:forEach var="crudsc" items="${pss}" varStatus="status">
                <tr class="${status.index%2==0?'even':'odd'}">
                    <td class="nowrap"><fmt:formatDate pattern="yyyy-MM-dd" value="${crudsc.dateOfProgram}" /></td>
                    <td class="nowrap"><fmt:formatDate pattern="hh:mm:ss a" value="${crudsc.startTime}" /></td>
                    <td class="nowrap"><fmt:formatDate pattern="HH:mm:ss" value="${crudsc.duration}" /></td>
                    <td class="nowrap">
                        <c:url var="updurl" scope="page" value="/nocturne/addeditrp">
                            <c:param name="dateOfProgram" value="${crudsc.dateOfProgram}" />
                            <c:param name="startTime" value="${crudsc.startTime}"/>
                            <c:param name="insert" value="false"/>
                        </c:url>
                        <a href="${updurl}"><fmt:message key="label.crudsc.edit"/></a>
                        &nbsp;&nbsp;&nbsp;
                        <c:url var="delurl" scope="page" value="/nocturne/deleteps">
                            <c:param name="dateOfProgram" value="${crudsc.dateOfProgram}" />
                            <c:param name="startTime" value="${crudsc.startTime}"/>
                        </c:url>
                        <a href="${delurl}"><fmt:message key="label.crudsc.delete"/></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
</body>
</html>