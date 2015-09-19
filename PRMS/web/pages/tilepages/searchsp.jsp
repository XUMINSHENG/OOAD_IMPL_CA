<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<fmt:setBundle basename="ApplicationResources" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="title.searchsp" /></title>
</head>
<body>
	<h2>
		<fmt:message key="title.searchsp" />
	</h2>
	<form action="${pageContext.request.contextPath}/nocturne/searchsp"
		method=post>
		<center>
			<table class="framed">
				<tr>
					<th width="45%"><fmt:message key="caption.name" /></th>
					<th width="55%"><fmt:message key="caption.desc" /></th>
				</tr>
				<tr>
					<td><fmt:message key="fieldLabel.year" /></td>
                                        <td>
                                            <select naem="year" style="width: 300px;">
                                                <c:forEach var="item" items="${searchrplist}">
                                                    <option value="<c:out value="${item}" />">
                                                        <c:out value="${item.name}" />
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>
				</tr>
				<tr>
					<td><fmt:message key="fieldLabel.week" /></td>
					<td>
                                            <select name="week" style="width:300px;">
                                                <c:forEach begin="1" end="32" varStatus="loop">
                                                    <option value="<c:out value="${loop.index}" />" >
                                                        <c:out value="${loop.index}" />
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>
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
			</tr>
			<c:forEach var="rprogram" items="${searchrplist}" varStatus="status">
				<tr class="${status.index%2==0?'even':'odd'}">
					<td class="nowrap">${rprogram.name}</td>
					<td class="nowrap">${rprogram.description}</td>
					<td class="nowrap">${rprogram.typicalDuration}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>