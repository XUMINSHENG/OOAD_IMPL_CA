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
				
                                <c:choose>
                                    <c:when test="${! empty yearlist}">
                                        <tr>
                                            <td><fmt:message key="fieldLabel.year" />
                                                <select name="year" id="year" style="width: 150px;" onchange="changeYear()">
                                                    <option value="0"> --Please select year-- </option>
                                                    <c:forEach var="item" items="${yearlist}" varStatus="loop">
                                                        <c:choose>
                                                            <c:when test="${ year == item.getYear() }">
                                                                <option value="${item.getYear()}" selected>
                                                                    <c:out value="${item.getYear()}" />
                                                                </option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${item.getYear()}">
                                                                    <c:out value="${item.getYear()}" />
                                                                </option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="hidden" name="current_week" id="current_week" value="${current_week}">
                                                <div id="select_week">
                                                
                                                </div>
                                            </td>
                                            <td><input type="submit" id="submit_btn" value="Select week scheduel"></td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="3"><h3 style="color:grey;text-align: center;"><i>No Annual Schedule</i></h3></td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                        </table>
		</center>
            </form>
        
    <c:if test="${(year != 0) && (week != 0)}">
        <table class="borderAll">
            <thead>
                <tr>
                    <th colspan="6">
                        <h3>
                            <c:out value="${ws.getYear()}"/> Year - <c:out value="${ws.getWeek()}"/> Week Schedule (<fmt:formatDate pattern="yyyy-MM-dd" value="${ws.getStartDate()}" /> - <fmt:formatDate pattern="yyyy-MM-dd" value="${ws.getEndDate()}" />)
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
                    <td class="nowrap"><fmt:formatDate pattern="HH:mm:ss" value="${pitem.startTime}" /></td>
                    <td class="nowrap"><fmt:formatDate pattern="HH:mm:ss" value="${pitem.duration}" /></td>
                    <td class="nowrap"><c:out value="${pitem.producer.name}" /></td>
                    <td class="nowrap"><c:out value="${pitem.presenter.name}" /></td>
                    <td class="nowrap">
                        <c:url var="updurl" scope="page" value="/nocturne/addeditps">
                            <c:param name="dateOfProgram" value="${pitem.dateOfProgram}"/>
                            <c:param name="startTime" value="${pitem.startTime}"/>
                            <c:param name="duration" value="${pitem.duration}"/>
                            <c:param name="program-name" value="${pitem.program.name}"/>
                            <c:param name="presenter-name" value="${pitem.presenter.name}"/>
                            <c:param name="producer-name" value="${pitem.producer.name}"/>
                            <c:param name="insert" value="false"/>
                        </c:url>
                        <a href="${updurl}"><fmt:message key="label.crudsc.edit"/></a>
                        &nbsp;&nbsp;&nbsp;
                        <c:url var="delurl" scope="page" value="/nocturne/deleteps">
                            <c:param name="year" value="${pitem.year}" />
                            <c:param name="weekNum" value="${pitem.weekNum}"/>
                            <c:param name="dateOfProgram" value="${pitem.dateOfProgram}" />
                            <c:param name="startTime" value="${pitem.startTime}"/>
                        </c:url>
                        <a href="${delurl}"><fmt:message key="label.crudsc.delete"/></a>
                    </td>
                </tr>
            </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td colspan="8"><h3 style="color:grey;text-align: center"><i>No Schedule</i></h3></td></tr>
            </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </c:if>
                                        <script>
                                            window.onload = function(){
                                                changeYear();
                                            };
                                            
                                            function changeYear(){
                                                var e = document.getElementById("year");
                                                var year = e.options[e.selectedIndex].value;
                                                if(year == 0){
                                                    document.getElementById("select_week").innerHTML = "";
                                                    document.getElementById("submit_btn").style.display = "none";
                                                }else{
                                                    var firstDate = new Date(year,0,1);
                                                    var lastDate = new Date(year,11,31);
                                                    console.log(Math.ceil((lastDate.getTime() - firstDate.getTime())/ 86400000));
                                                    var weeknum = Math.round((((lastDate - firstDate)/ 86400000) + 1)/7);
                                                    console.log(weeknum);
                                                    var weekHtml = '';
                                                    weekHtml += '<fmt:message key="fieldLabel.week" />';
                                                    weekHtml += '<select name="weekNum" id="weekNum" style="width:150px;" onchange="changeWeek()"><option value="0"> --Please select week-- </option>';
                                                    var i;
                                                    var cur = document.getElementById("current_week").value;
                                                    console.log(weeknum);
                                                    for( i = 1; i <= weeknum; i++ ) {
                                                        if( cur == i )
                                                            weekHtml += '<option value="' + i + '" selected>' + i + '</option>';
                                                        else
                                                            weekHtml += '<option value="' + i + '" >' + i + '</option>';
                                                    }
                                                    weekHtml += '</select>';
                                                
                                                    var e1 = document.getElementById("select_week");
                                                    e1.innerHTML = weekHtml;
                                                    document.getElementById("submit_btn").style.display = "inline-block";
                                                }    
                                            }
                                            
                                            function changeWeek(){
                                                var e = document.getElementById("weekNum");
                                                var week = e.options[e.selectedIndex].value;
                                                document.getElementById("current_week").value = week;
                                                console.log(week);
                                            }
                                            
                                        </script>
</body>
</html>