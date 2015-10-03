<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
    <head>
        <fmt:setBundle basename="ApplicationResources" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title><fmt:message key="title.copysched" /></title>
    </head>
    <body>
	<h2>
            <fmt:message key="title.copysched" />
	</h2>
            <form id="copy_sched_form" name="copy_sched_form" action="${pageContext.request.contextPath}/nocturne/copysched"
		method=post>
            <center>
                <table class="framed">
                    <tr>
                        <th width="10%"><fmt:message key="caption.name" /></th>
                        <th width="10%">Input</th>
                        <th width="10%">Action</th>
                        <th width="5%">Week Num</th>
                        <th width="5%">Week Start Date</th>
                        <th width="5%">Week End Date</th>
                    </tr>
                    <tr>
                        <td><fmt:message key="fieldLabel.SrcDate" /></td>
                        <td>
                            <input type="text" name="src_date_val" value="${src_date_val}">
                        </td>
                        <td>                                            
                            <input type="submit" name="display_src_sched" value="DisplaySrcSchedule"/>    
                        </td>
                        <td>
                            <label for="src_date_weeknum"> ${src_date_weeknum}</label>
                        </td>
                        <td>
                            <label for="src_date_weekstart">${src_date_weekstart}</label>
                        </td>
                        <td>
                            <label for="src_date_weekend">${src_date_weekend}</label>
                        </td>
                    </tr>
                    <tr>
                        <td><fmt:message key="fieldLabel.DestDate" /></td>
                        <td>
                            <input type="text" name="dest_date_val" value="${dest_date_val}">
                        </td>
                        <td>
                            <input type="submit" name="display_dest_sched" value="DisplayDestSchedule"/>    
                        </td>
                        <td>
                            <label for="dest_date_weeknum">${dest_date_weeknum}</label>
                        </td>
                        <td>
                            <label for="dest_date_weekstart">${dest_date_weekstart}</label>
                        </td>
                        <td>
                            <label for="dest_date_weekend">${dest_date_weekend}</label>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" align="center"><input type="submit" name="copy_sched" value="CopySchedule"/> 
                            <input type="submit" name="reset" value="Reset">
                        </td>
                    </tr>
                    <c:if test="${! empty  lbl_status}">
                        <tr>
                            <td>
                                <p style="color:red">Error(s)</p>
                            </td>
                            <td colspan="3">
                                <p style="color:red">
                                    <c:forEach var="lbl" items="${lbl_status}" varStatus="status">
                                        <c:out value="${lbl}"/>
                                        <br>
                                    </c:forEach>
                                </p>
                            </td>
                        </tr>
                    </c:if>
                    <input type="hidden" id="copy_user_resp" name="copy_user_resp" value="">
                    <input type="hidden" id="copy_sched_is_sel" name="copy_sched_is_sel" value=${copy_sched_is_sel}>
                </table>
            </center>
	</form>        
        <c:if test="${is_prompt == true}">
            <script>
                var result=confirm('Destination Weekly Schedule is not Empty.Copy and Overwrite it?');
                if (result) 
                {
                    document.getElementById("copy_user_resp").value="yes";
                    document.forms["copy_sched_form"].submit();
                } 
                else
                {
                    document.getElementById("copy_user_resp").value="no";
                    document.forms["copy_sched_form"].submit();
                }
            </script>
        </c:if>
        <c:if test="${actionSrc == 'copyYes'}">
            <p style="color:green"><b>Successfully Copied from Source(week ${src_date_weeknum}) to Destination(week ${dest_date_weeknum}) Weekly Schedule</b></p>
        </c:if>
        <c:if test="${actionSrc == 'copyNo'}">
            <p style="color:orange"><b>Copy has been Successfully Canceled</b></p>
        </c:if>
        <c:choose>
            <c:when test="${actionSrc == 'src'}">
                <c:choose>
                    <c:when test="${! empty  splist}">                
                        <p><b>Source Weekly Schedule: (Week ${src_date_weeknum}) ${src_date_weekstart} to ${src_date_weekend}</b></p>
                    </c:when>
                    <c:otherwise>
                        <p><b>Source Weekly Schedule</b></p>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${! empty  splist}">                
                        <p><b>Destination Weekly Schedule: (Week ${dest_date_weeknum}) ${dest_date_weekstart} to ${dest_date_weekend}</b></p>                    
                    </c:when>
                    <c:otherwise>
                        <p><b>Destination Weekly Schedule</b></p>
                    </c:otherwise>
                </c:choose>
            </c:otherwise> 
        </c:choose>
                        
        <table class="borderAll">
            <tr>
                <th><fmt:message key="label.programslot.name" /></th>
                <th><fmt:message key="label.programslot.dateOfProgram" /></th>
                <th><fmt:message key="label.programslot.startTime" /></th>
                <th><fmt:message key="label.programslot.duration" /></th>
                <th><fmt:message key="label.programslot.presenter" /></th>
                <th><fmt:message key="label.programslot.producer" /></th>
            </tr>
            <c:choose>
                <c:when test="${! empty  splist}">
                    <c:forEach var="slot" items="${splist}" varStatus="status">
                        <tr class="${status.index%2==0?'even':'odd'}">
                            <td class="nowrap"><c:out value="${slot.program.name}" /></td>
                            <td class="nowrap"><fmt:formatDate pattern="yyyy-MM-dd" value="${slot.dateOfProgram}" /></td>
                            <td class="nowrap"><fmt:formatDate pattern="hh:mm:ss" value="${slot.startTime}" /></td>
                            <td class="nowrap"><fmt:formatDate pattern="HH:mm:ss" value="${slot.duration}" /></td>
                            <td class="nowrap"><c:out value="${slot.presenter.name}" /></td>
                            <td class="nowrap"><c:out value="${slot.producer.name}" /></td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="6"><p style='color:grey;text-align: center;'><i>No Scheduled Programs</i></p></td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </table>
    </body>
</html>