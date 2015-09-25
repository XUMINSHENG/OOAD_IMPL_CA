<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<head>
<fmt:setBundle basename="ApplicationResources" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="title.searchpp" /></title>
</head>

	<h2>
		<fmt:message key="title.searchpp" />
	</h2>
	<form action="${pageContext.request.contextPath}/nocturne/searchpp"
		method=post>
		<center>
			<table class="framed">
				<tr>
					<th width="45%"><fmt:message key="caption.name" /></th>
					<th width="55%"><fmt:message key="caption.desc" /></th>
                                        <!--<th width="55%"><fmt:message key="caption.role" /></th>-->
                                </tr>
                                
                                
				<tr>
					<td><fmt:message key="fieldLabel.user" /></td>
                                        <td><input type="text" name="name" size=45 maxlength=45 value="${name}"></td>
				</tr>
				<tr>
					<td><fmt:message key="fieldLabel.username" /></td>
					<td><input type="text" name="description" size=45 maxlength=45 value="${description}"></td>
				</tr>
				<tr>
					<td><fmt:message key="fieldLabel.role" /></td>
					<td><input type="text" name="description" size=45 maxlength=45 value="${description}"></td>
				</tr>
                                
                                
                                <tr>
					<td colspan="2" align="center"><input type="submit" value="Submit"> <input
						type="reset" value="Reset"></td>
				</tr>
			</table>
		</center>

	</form>
	<c:if test="${! empty  searchpplist}">
		<table class="borderAll">
			<tr>
				<th><fmt:message key="label.User.name" /></th>
				<th><fmt:message key="label.User.userId" /></th>
				<th><fmt:message key="label.Role.role" /></th>
			</tr>
			<c:forEach var="user" items="${searchpplist}" varStatus="status">
				<tr class="${status.index%2==0?'even':'odd'}">
					<td class="nowrap">${user.name}</td>
					<td class="nowrap">${user.id}</td>
                                        <td class="nowrap">${Role.role}</td>  
                                </tr>
			</c:forEach>
		</table>
	</c:if>


