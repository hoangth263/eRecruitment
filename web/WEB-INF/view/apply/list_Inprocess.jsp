<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href='https://css.gg/software-download.css' rel='stylesheet'>
<link href='https://css.gg/remove-r.css' rel='stylesheet'>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Css Styles -->
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
        <link rel="stylesheet" href="css/nice-select.css" type="text/css">
        <link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
        <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
        <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
        <link rel="stylesheet" href="css/style.css" type="text/css">
    </head>
    <body>
    <center>
        <c:if test="${not empty Reject}">
            <div class="alert alert-success alert-dismissible">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <strong>${Reject}!</strong>
            </div>
        </c:if>
        <c:if test="${not empty Accept}">
            <div class="alert alert-success alert-dismissible">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <strong>${Accept}!</strong> 
            </div>
        </c:if>
        <h1>Inprocess Applications</h1> <br>
        <nav class="header__menu">
            <ul>
                <li><a href="<c:url value="apply?op=sortByStatusASCInprocess"/>"> Sort by Status </a>
                    <ul class="header__menu__dropdown">
                        <li><a href="apply?op=sortByStatusASCInprocess">Status Ascending</a></li>
                        <li><a href="apply?op=sortByStatusDESCInprocess">Status Descending</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
        <br><br>
        <table class="table table-striped" border="1" cellspacing="0" cellpadding="4">   
            <c:choose>
                <c:when test="${not empty listInprocess  }">
                    <thead>
                        <tr>
                            <th>No.</th><th>Can_id</th><th>Job Name</th>
                            <th>Email</th><th>File Upload</th>
                            <th>Exam Score</th>
                            <!--                    <th>Interview Score</th>-->
                            <th>Status</th><th style="text-align: center">Operations</th>    
                        </tr>
                    </thead>
                    <c:forEach var="can" items="${listInprocess}" varStatus="loop">

                        <tr>
                            <td style="text-align: left;"><fmt:formatNumber value="${loop.count}" pattern="" /></td>
                            <td>${can.id}</td>
                            <td>${can.jobname.job_name}</td>
                            <td>${can.email}</td>
                            <td>${can.cv}</td>
                            <td>
                                <!--Score-->
                                <c:choose>
                                    <c:when test="${can.score != null && can.isStatus >= 2 && can.isStatus <5}">
                                        ${can.score}
                                    </c:when>
                                    <c:when test="${can.isStatus == 1}">
                                        Not Available 
                                    </c:when>
                                    <c:otherwise>
                                        Error Value
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:choose>
                                    <c:when test="${can.isStatus==1}">
                                        Accepted
                                    </c:when>
                                    <c:when test="${can.isStatus==2}">
                                        Tested
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
                            <td style="text-align: center">
                                <a class="gg-software-download" style="margin-top:20px; display:inline-block;color: orange" href="apply?op=downloadFile&fileName=${can.cv}"></a> |
                                <a class="gg-remove-r" style="margin-top:20px; display:inline-block; color: red" href="apply?op=rejectFileInprocess&can_id=${can.id}&email=${can.email}&job_name=${can.jobname.job_name}"></a> 
                            </td>
                        </tr>

                    </c:forEach>
                    </tbody>
                </c:when>
                <c:otherwise>
                    <c:out value="NO DATA FOUND"/>
                </c:otherwise>
            </c:choose>
        </table><br>
        <br>
    </center>
</body>
</html>
