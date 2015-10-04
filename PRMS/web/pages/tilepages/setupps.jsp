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
            var duration = getQueryString("duration");
            var paras;
            function updateData(){
                        dateOfProgram = document.getElementById("date").value;
                        startTime = document.getElementById("st").value;
                        programName = getQueryString("program-name");
                        producerName = getQueryString("producer-name");
                        presenterName = getQueryString("presenter-name");
                        insert = getQueryString("insert");
                        duration = document.getElementById("duration").value;
                        paras = dateOfProgram+
                        "&startTime="+startTime+"&program-name="+ programName+
                        "&producer-name="+producerName+"&duration="+duration+
                        "&presenter-name="+presenterName+"&insert="+insert;
            }
            
            
            function srp(){           
                        updateData();
                        window.location.href="selectrp?dateOfProgram="+paras;
            }
            function spro(){
                        updateData();
                        window.location.href="selectProducer?dateOfProgram="+paras;
            }
            function spre(){
                        updateData();
                        window.location.href="selectPresenter?dateOfProgram="+paras;
            }
            function val()
            {
                var dateFormate = new RegExp("[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))");
                var durationFormate = new RegExp("(([0-1]?[0-9])|([2][0-3])):([0|3]?[0]):(([0]?[0]))");
                var stFormat = new RegExp("(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9]):(([0-5]?[0-9]))");
                var zeroDuration = new RegExp("[0]?[0]:[0]?[0]:(([0-5]?[0-9]))");
                
                if(document.getElementById("date").value==="")
                {
                    alert("Date Of Program can't be empty!");
                    return;
                }
                if(dateFormate.test(document.getElementById("date").value)!==true)
                {
                    alert("Date Format Error! Date Format should be 'YYYY-MM-DD'");
                    return;
                }
                if(document.getElementById("st").value==="")
                {
                    alert("Start Time can't be empty!");
                    return;
                }
                if(stFormat.test(document.getElementById("st").value)!==true)
                {
                    alert("Time Format Error! Time Format should be 'HH:mm:ss'");
                    return;
                }
                if(document.getElementById("programName").value==="")
                {
                    alert("Program Name can't be empty!");
                    return;
                }
                if(document.getElementById("duration").value==="")
                {
                    alert("Duration can't be empty!");
                    return;
                }
                if(durationFormate.test(document.getElementById("duration").value)!==true)
                {
                    alert("Duration Format Error! Time Format should be 'HH:00:00' or 'HH:30:00'");
                    return;
                }
                if(zeroDuration.test(document.getElementById("duration").value)===true)
                {
                    alert("Duration need to more than 30mins!");
                    return;
                }
                 if(document.getElementById("producerName").value==="")
                {
                    alert("Producer Name can't be empty!");
                    return;
                }
                 if(document.getElementById("presenterName").value==="")
                {
                    alert("Presenter Name can't be empty!");
                    return;
                }
                document.getElementById("detail").submit();
            }
        </script>
</head>
<body>
    <form action="${pageContext.request.contextPath}/nocturne/enterps" method=post id="detail" >
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
							<input type="text" name="name" id = "programName" value="${param['program-name']}" size=15
                                                               maxlength=20  readonly="readonly">
							<input type="hidden" name="ins" value="true" />
                                                        <input type="button" name="selectRP" value="select" onclick="srp();">
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="name" id = "programName" value="${param['program-name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
                                                        <input type="button" name="selectRP" value="select" onclick="srp();">
						</c:if>
                                        </td>
				</tr>
                                <!--Duration-->
                                <tr>
                                        <td><fmt:message key="label.programslot.duration"/></td>
                                        <td><c:if test="${param['insert'] == 'true'}">
							<input type="text" name="duration" id = "duration" value="${param['duration']}" size=15
								maxlength=20>
							<input type="hidden" name="ins" value="true" />
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="duration" id = "duration" value="${param['duration']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
						</c:if>
                                        </td>
                                </tr>
                                <!--Producer-->
                                <tr>
					<td><fmt:message key="label.programslot.producer" /></td>
					<td><c:if test="${param['insert'] == 'true'}">
							<input type="text" name="producer" id = "producerName" value="${param['producer-name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="true" />
                                                        <input type="button" name="selectProducer" value="select" onclick="spro();">
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="producer" id = "producerName" value="${param['producer-name']}" size=15
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
							<input type="text" name="presenter" id = "presenterName" value="${param['presenter-name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="true" />
                                                        <input type="button" name="selectPresenter" value="select" onclick="spre();">
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="presenter" id = "presenterName" value="${param['presenter-name']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
                                                        <input type="button" name="selectPresenter" value="select" onclick="spre();">
						</c:if>
                                        </td>
				</tr>
                                
                                
				
			</table>
		</center>
                                        <input type="button" value="Submit" name="Submit" onclick="val();"> <input type="reset"
			value="Reset">
	</form>

</body>
</html>