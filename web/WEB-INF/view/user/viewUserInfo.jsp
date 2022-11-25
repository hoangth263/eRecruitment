<%-- 
    Document   : view
    Created on : Oct 5, 2022, 4:27:07 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">
        <title>JSP Page</title>

    </head>
    <body>
        <main class=" user-profile">
            <h2>User profile</h2>
            <div class="container">
                <div class="row">
                        <div class="user-content">
                            <h5 class="user-content-item">
                                Email: ${user.email}
                            </h5>
                            <h5 class="user-content-item">
                                Name: ${user.name}
                            </h5>
                            <h5 class="user-content-item">
                                Phone Number: ${user.phone}
                            </h5>
                            <h5 class="user-content-item">
                                Address: ${user.address}
                            </h5>
                            <c:if test="${user.roleId!=5}">
                                <h5 class="user-content-item">
                                    Role: <c:forEach items="${listRole}" var="role">
                                        <c:if test="${user.roleId==role.id}">${role.name}</c:if>
                                    </c:forEach>
                                </h5>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </main>


        <div class="account-management">
            <h3>
                User Application
            </h3>

            <table class="table table-striped" border="1" cellspacing="0" cellpadding="4">      
                <c:choose>
                    <c:when test="${not empty listUserEmail}">
                        <thead>
                            <tr>
                                <th>No.</th><th>Job Name</th>
                                <th>File Upload</th>
                                <th>Exam Score</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <c:forEach var="can" items="${listUserEmail}" varStatus="loop">
                            <tr>
                                <td style="text-align: center;"><fmt:formatNumber value="${loop.count}" pattern="000" /></td>
                                <td>${can.jobname.job_name}</td>
                                <td>${can.cv}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${can.isStatus <2}">
                                            Not Yet
                                        </c:when>
                                        <c:when test="${can.score!= null && can.isStatus >= 2}">
                                            ${can.score}
                                        </c:when>
                                        <c:otherwise>
                                            Error Data
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <c:choose>
                                    <c:when test="${ can.score<4 && can.isStatus >= 2}">
                                        <td>
                                            <c:choose>
                                                <c:when test="${can.isStatus==0}">
                                                    Hasn't Accepted
                                                </c:when>
                                                <c:when test="${can.isStatus==1}">
                                                    Accepted
                                                </c:when>
                                                <c:when test="${can.isStatus==2}">
                                                    <strong style="color: red">Failed</strong>
                                                </c:when>
                                                <c:when test="${can.isStatus==3}">
                                                    Has Scheduled
                                                </c:when>
                                                <c:when test="${can.isStatus==4}">
                                                    Has Been Interviewed
                                                </c:when>
                                                <c:when test="${can.isStatus==5}">
                                                    Hired
                                                </c:when>
                                            </c:choose>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>
                                            <c:choose>
                                                <c:when test="${can.isStatus==0}">
                                                    Hasn't Accepted
                                                </c:when>
                                                <c:when test="${can.isStatus==1}">
                                                    Accepted
                                                </c:when>
                                                <c:when test="${can.isStatus==2}">
                                                    <strong style="color: #4aba76">Passed</strong>
                                                </c:when>
                                                <c:when test="${can.isStatus==3}">
                                                    Has Scheduled
                                                </c:when>
                                                <c:when test="${can.isStatus==4}">
                                                    Has Been Interviewed
                                                </c:when>
                                                <c:when test="${can.isStatus==5}">
                                                    Hired
                                                </c:when>
                                            </c:choose>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>

                        </c:forEach>

                        </tbody>
                    </c:when>
                    <c:otherwise>
                        <c:out value="NO DATA FOUND"/>
                    </c:otherwise>
                </c:choose>
            </table>
        </div>
    </body>
</html>