<%-- 
    Document   : setuppps
    Created on : 21 Sep, 2015, 15:18:23 PM
    Author     : Liu Xinzhuo
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
<title><fmt:message key="title.setupps" /></title>
<script type="text/javascript">
            function getQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r !== null) return unescape(r[2]); return null;
            }
            var dateOfProgram = getQueryString("dateOfProgram");
            var startTime = getQueryString("startTime");
            var programName = getQueryString("program-name");
            var producerName = getQueryString("producer-name");
            var presenterName = getQueryString("presenter-name");
            var insert = getQueryString("insert");
            function srp(){           
                        dateOfProgram = document.getElementById("date").value;
                        startTime = document.getElementById("st").value;
                        programName = getQueryString("program-name");
                        producerName = getQueryString("producer-name");
                        presenterName = getQueryString("presenter-name");
                        insert = getQueryString("insert");
                        window.location.href="selectrp?dateOfProgram="+dateOfProgram+
                        "&startTime="+startTime+"&program-name="+ programName+
                        "&producer-name="+producerName+"&presenter-name="+presenterName
                        +"&insert="+insert;
            }
            function spro(){
                        dateOfProgram = document.getElementById("date").value;
                        startTime = document.getElementById("st").value;
                        programName = getQueryString("program-name");
                        producerName = getQueryString("producer-name");
                        presenterName = getQueryString("presenter-name");
                        insert = getQueryString("insert");  
                        window.location.href="selectProducer?dateOfProgram="+dateOfProgram+
                        "&startTime="+startTime+"&program-name="+ programName+
                        "&producer-name="+producerName+"&presenter-name="+presenterName
                        +"&insert="+insert;
            }
            function spre(){
                        dateOfProgram = document.getElementById("date").value;
                        startTime = document.getElementById("st").value;
                        programName = getQueryString("program-name");
                        producerName = getQueryString("producer-name");
                        presenterName = getQueryString("presenter-name");
                        insert = getQueryString("insert");
                        window.location.href="selectPresenter?dateOfProgram="+dateOfProgram+
                        "&startTime="+startTime+"&program-name="+ programName+
                        "&producer-name="+producerName+"&presenter-name="+presenterName
                        +"&insert="+insert;
            }
        </script>
</head>
<body>
	<form action="${pageContext.request.contextPath}/nocturne/enterps" method=post>
		<center>
			<table cellpadding=6 cellspacing=2 border=0>
				<!--Date-->
                                <tr>
					<td><fmt:message key="label.programslot.dateOfProgram" /></td>
					<td><c:if test="${param['insert'] == 'true'}">
                                                <input type="text" name="dateOfProgram" id="date" value="${param['dateOfProgram']}" size=15
								maxlength=20>
							<input type="hidden" name="ins" value="true" />
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="dateOfProgram" id="date" value="${param['dateOfProgram']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
						</c:if>
                                        </td>
				</tr>
                                
                                <!--Start Time-->
                                <tr>
					<td><fmt:message key="label.programslot.startTime" /></td>
					<td><c:if test="${param['insert'] == 'true'}">
                                                <input type="text" name="startTime" id="st" value="${param['startTime']}" size=15
								maxlength=20 >
							<input type="hidden" name="ins" value="true" />
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="startTime" id="st" value="${param['startTime']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
						</c:if>
                                        </td>
				</tr>
                                
                                <!--Program-->
                                <tr>
					<td><fmt:message key="label.programslot.name" /></td>
					<td><c:if test="${param['insert'] == 'true'}">
							<input type="text" name="name" value="${param['program-name']}" size=15
                                                               maxlength=20  readonly="readonly">
							<input type="hidden" name="ins" value="true" />
                                                        <input type="button" name="selectRP" value="select" onclick="srp();">
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="name" value="${param['program-name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
                                                        <input type="button" name="selectRP" value="select" onclick="srp();">
						</c:if>
                                        </td>
				</tr>
                                
                                <!--Producer-->
                                <tr>
					<td><fmt:message key="label.programslot.producer" /></td>
					<td><c:if test="${param['insert'] == 'true'}">
							<input type="text" name="producer" value="${param['producer-name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="true" />
                                                        <input type="button" name="selectProducer" value="select" onclick="spro();">
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="producer" value="${param['producer-name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
                                                        <input type="button" name="selectProducer" value="select" onclick="spro();">
						</c:if>
                                        </td>
				</tr>
                                
                                <!--Presenter-->
                                <tr>
					<td><fmt:message key="label.programslot.presenter" /></td>
					<td><c:if test="${param['insert'] == 'true'}">
							<input type="text" name="presenter" value="${param['presenter-name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="true" />
                                                        <input type="button" name="selectPresenter" value="select" onclick="spre();">
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="presenter" value="${param['presenter-name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
                                                        <input type="button" name="selectPresenter" value="select" onclick="spre();">
						</c:if>
                                        </td>
				</tr>
                                
                                
				
			</table>
		</center>
		<input type="submit" value="Submit" name="Submit"> <input type="reset"
			value="Reset">
	</form>

</body>
</html>