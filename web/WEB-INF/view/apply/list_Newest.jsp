<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href='https://css.gg/software-download.css' rel='stylesheet'>
<link href='https://css.gg/check-r.css' rel='stylesheet'>
<link href='https://css.gg/remove-r.css' rel='stylesheet'>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <h1>Latest Applications</h1> <br>
        <c:choose>
            <c:when test="${not empty list0  }">
                <table class="table table-striped" border="1" cellspacing="0" cellpadding="4">
                    <nav class="header__menu">
                        <ul>
                            <li><a href="<c:url value="apply?op=sortByCanASCNewest"/>"> Sort by Can Id </a>
                                <ul class="header__menu__dropdown">
                                    <li><a href="apply?op=sortByCanASCNewest">Can Id Ascending</a></li>
                                    <li><a href="apply?op=sortByCanDESCNewest">Can Id Descending</a></li>
                                </ul>
                            </li>
                        </ul>
                    </nav>

                    <thead>
                        <tr>
                            <th>No.</th><th>Can_id</th><th>Job Name</th>
                            <th>Email</th><th>File Upload</th>

                            <!--                    <th>Interview Score</th>-->
                            <!--<th>Status</th>-->
                            <th style="text-align: center">Operations</th>    
                        </tr>
                    </thead>
                    <c:forEach var="can" items="${list0}" varStatus="loop">
                        <tr>
                            <td style="text-align: left;"><fmt:formatNumber value="${loop.count}" pattern="" /></td>
                            <td>${can.id}</td>
                            <td>${can.jobname.job_name}</td>
                            <td>${can.email}</td>
                            <td>${can.cv}</td>
                            <td style="text-align: center">
                                <a class="gg-software-download" style="margin-top:20px; display:inline-block;"  href="apply?op=downloadFile&fileName=${can.cv}"></a>
                                | <a class="gg-check-r" style="margin-top:20px; display:inline-block; color: #66D7A7" href="apply?op=yesupNewest&can_id=${can.id}&email=${can.email}&job_name=${can.jobname.job_name}"></a>
                                | <a class="gg-remove-r" style="margin-top:20px; display:inline-block; color: red" href="apply?op=deleteFile&can_id=${can.id}&email=${can.email}&job_name=${can.jobname.job_name}&stand=Newest"></a> 
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
