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
					<td><fmt:message key="fieldLabel.name" /></td>
                                        <td><input type="text" name="name" size=45 maxlength=45 value="${name}"></td>
				</tr>
				
				<tr>
					<td colspan="2" align="center"><input type="submit" value="Search"> <input
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
                                    <c:when test="${name != null}">
                                        Search '<c:out value="${name}"/>' Program Results
                                    </c:when>
                                    <c:otherwise>
                                        All Scheduled Programs
                                    </c:otherwise>    
                                </c:choose>
                            </h3>
                        </th>
                </tr>
            </thead>
            <tbody>
                <tr>
                        <th><fmt:message key="label.radioprogram.name"/></th>
                        <th><fmt:message key="label.crudsc.date"/></th>
                        <th><fmt:message key="label.crudsc.startTime"/></th>
                        <th><fmt:message key="label.crudsc.duration"/></th>
                        <th><fmt:message key="label.programslot.presenter"/></th>
                        <th><fmt:message key="label.programslot.producer"/></th>
		</tr>
                <c:choose>
                    <c:when test="${! empty  searchrplist}">
                            <c:forEach var="slot" items="${searchrplist}" varStatus="status">
				<tr class="${status.index%2==0?'even':'odd'}">
					<td class="nowrap"><c:out value="${slot.program.name}" /></td>
                                        <td class="nowrap"><fmt:formatDate pattern="yyyy-MM-dd" value="${slot.dateOfProgram}" /></td>
                                        <td class="nowrap"><fmt:formatDate pattern="hh:mm:ss" value="${slot.startTime}" /></td>
                                        <td class="nowrap"><fmt:formatDate pattern="HH:mm:ss" value="${slot.duration}" /></td>
                                        <td class="nowrap"><c:out value="${slot.producer.name}" /></td>
                                        <td class="nowrap"><c:out value="${slot.presenter.name}" /></td>
                                </tr>
                            </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr><td colspan="6"><p style='color:grey'><i>No Scheduled Program</i></p></td></tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
	

</body>
</html>