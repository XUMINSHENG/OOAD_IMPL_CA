<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<fmt:setBundle basename="ApplicationResources" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="title.selectrp" /></title>
</head>
<body>
	<h2>
		<fmt:message key="title.selectrp" />
	</h2>
	<form action="${pageContext.request.contextPath}/nocturne/selectrp"
		method=post>
		<center>
			<table class="framed">
				<tr>
					<th width="45%"><fmt:message key="caption.name" /></th>
					<th width="55%"><fmt:message key="caption.desc" /></th>
				</tr>
				<tr>
					<td><fmt:message key="fieldLabel.name" /></td>
                                        <td><input type="text" name="name" size=45 maxlength=45 value="${name}"></td>
				</tr>
				<tr>
					<td><fmt:message key="fieldLabel.description" /></td>
					<td><input type="text" name="description" size=45 maxlength=45 value="${description}"></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="Submit"> <input
						type="reset" value="Reset"></td>
				</tr>
			</table>
		</center>

	</form>
	<c:if test="${! empty  searchrplist}">
		<table class="borderAll">
			<tr>
				<th><fmt:message key="label.radioprogram.name" /></th>
				<th><fmt:message key="label.radioprogram.description" /></th>
				<th><fmt:message key="label.radioprogram.duration" /></th>
                                <th><fmt:message key="label.Select" /></th>
			</tr>
			<c:forEach var="rprogram" items="${searchrplist}" varStatus="status">
				<tr class="${status.index%2==0?'even':'odd'}">
					<td class="nowrap">${rprogram.name}</td>
					<td class="nowrap">${rprogram.description}</td>
					<td class="nowrap">${rprogram.typicalDuration}</td>
                                        <td>
                                                <c:url var="get" scope="page" value="/nocturne/addeditps">
                                                <c:param name="dateOfProgram" value="${param['dateOfProgram']}"/>
                                                <c:param name="startTime" value="${param['startTime']}"/>
                                                <c:param name="program-name" value="${rprogram.name}"/>
                                                <c:param name="producer-name" value="${param['producer-name']}"/>
                                                <c:param name="presenter-name" value="${param['presenter-name']}"/>
                                                <c:param name="insert" value="${param['insert']}"/>
                                                </c:url>
                                                <a href="${get}"><fmt:message key="label.Select"/></a>
                                        </td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>