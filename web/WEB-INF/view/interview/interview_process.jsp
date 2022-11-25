<%-- 
    Document   : interview_process
    Created on : Oct 14, 2022, 10:05:18 AM
    Author     : Thien's
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Interview Process</title>
    </head>
    <body>
        <div class="container">
            <c:if test="${not empty interview}">
                <table class="table table-striped">
                    <thead>
                    <th>Name</th>
                    <th>Job Name</th>
                    <th>Interview's Date</th>
                    <th>Location</th>
                    <th>Status</th>
                    </thead>
                    <tbody>
                        <c:forEach var="i" items="${interview}" varStatus="loop">
                            <tr>
                                <td>${i.can_name}</td>
                                <td>${job_name[loop.count-1]}</td>
                                <td>
                                    <fmt:formatDate value="${i.date}" pattern="dd-MM-yyyy HH:mm"/>
                                </td>
                                <td>${i.location}</td>
                                <c:if test="${i.status=='Expired'}">
                                    <td style="color: red">${i.status}</td>
                                </c:if>
                                <c:if test="${i.status=='Has Interviewed'}">
                                    <td style="color: #007fff">${i.status}</td>
                                </c:if>
                                <c:if test="${i.status=='Hasn\'t Interviewed'}">
                                    <td style="color: #EDBB0E">${i.status}</td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty interview}">
                <h2>There's no interview's schedule!</h2>
            </c:if>
        </div>
    </body>
</html>
