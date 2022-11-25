<%--
    Document   : view
    Created on : Oct 5, 2022, 4:27:07 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--Icon ProVjp-->
<link href='https://css.gg/software-download.css' rel='stylesheet'>
<link href='https://css.gg/trash.css' rel='stylesheet'>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">
        <title>JSP Page</title>

    </head>
    <body>
        <main class=" user-profile">
            <h2>My profile</h2>
            <div class="container">
                <div class="row">
                    <!-- ben trai  -->
                    <div class="col-2">
                        <figure>
                            <img src="${info.picture}" alt="Not Found" class="user-img" onerror="this.src='<c:url value="/cvs/default.jpg"/>';"/> 
                        </figure>

                        <!-- user name -->
                        <h4>User name</h4>
                    </div>

                    <!--ben phai-->
                    <div class="col-10">
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
                                        <c:if test="${role.id==user.roleId}">${role.name}</c:if>
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
                Account management
            </h3>

            <span class="account-management-item">-Edit your info <button onclick="document.querySelector('dialog').showModal()" >Update </button></span>
            <dialog >
                <p>Update Infomation</p>
                <form action="<c:url value="/user"/>" method="post">
                    <input type="hidden" value="${user.email}" name="email"/>
                    <input class="input" type="text" value="${user.name}" name="name" placeholder="Name"/><br/>
                    <input class="input" type="text" value="${user.phone}" name="phone" placeholder="Phone Number" pattern="[0]{1}[1-9]{1}[0-9]{8}"/><br/>
                    <input class="input" type="text" value="${user.address}" name="address" placeholder="address"/><br/>
                    <input type="hidden" name="op" value="updatehandler"/>
                    <button type="submit" >Update</button>
                </form>
                <button onclick="document.querySelector('dialog').close()" class="btn btn-secondary"  >Cancel</button>
            </dialog>
        </div>
        <div class="account-management">
            <h3>
                My Application
            </h3>

            <table class="table table-striped" border="1" cellspacing="0" cellpadding="4">
                <c:choose>
                    <c:when test="${not empty listEmail}">
                        <thead>
                            <tr>
                                <th>No.</th><th>Job Name</th>
                                <th>File Upload</th>
                                <th>Exam Score</th>
                                <!--                    <th>Interview Score</th>-->
                                <th>Status</th><th style="text-align: center">Operations</th>
                            </tr>
                        </thead>
                        <c:forEach var="can" items="${listEmail}" varStatus="loop">
                            <tr>
                                <td style="text-align: center;"><fmt:formatNumber value="${loop.count}" pattern="000" /></td>
                                <td>${can.jobname.job_name}</td>
                                <td>${can.cv}</td>
                                <td>
                                    <!--Score-->
                                    <c:choose>
                                        <c:when test="${can.isStatus <2 || can.isStatus ==6}">
                                            Not Yet
                                        </c:when>
                                        <c:when test="${can.score!= null && can.isStatus >= 2 && can.isStatus <6}">
                                            ${can.score}
                                        </c:when>
                                        <c:otherwise>
                                            Error Value
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <!--Status-->
                                <c:choose>
                                    <c:when test="${ can.score<4 && can.isStatus >= 2}">
                                        <td>
                                            <!--Failed-->
                                            <c:choose>
                                                <c:when test="${can.isStatus==6}">
                                                    <div style="color: #FF8000">Cancel</div>
                                                </c:when>
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
                                                <c:otherwise>
                                                    Error Value
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>
                                            <!--Passed-->
                                            <c:choose>
                                                <c:when test="${can.isStatus==6}">
                                                    <div style="color: #FF8000">Cancel</div>
                                                </c:when>
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
                                <td>
                                    <a class="gg-software-download" style="margin-top:20px; display:inline-block; color: orange" href="apply?op=downloadFile&fileName=${can.cv}"> </a>
                                    <c:choose>
                                        <c:when test="${can.isStatus==6 || can.isStatus==0}">
                                            <!--Xóa những CV dự phòng (Standby=1) hoặc chưa đc Accepted--> 
                                            <c:if test="${can.score < 4}" >
                                                | <a class="gg-trash" style="margin-top:20px; display:inline-block; color: red" href="apply?op=deleteFile&can_id=${can.id}&stand=info"
                                                     onclick="return confirm('Are you sure to delete the Application in ${can.jobname.job_name} ?')"></a>
                                            </c:if>
                                        </c:when>
                                        <c:when test="${can.isStatus >0 && can.isStatus < 6}">
                                            <!--Xóa CV chính : đã được Accepted trở lên, isStatus2345-->
                                            <c:choose>
                                                <c:when test="${can.score == null}" >
                                                    | <a class="gg-trash" style="margin-top:20px; display:inline-block; color: red"  href="apply?op=deleteApplied&can_id=${can.id}&email=${info.email}&stand=info"
                                                         onclick="return confirm('Are you sure to delete the Application in ${can.jobname.job_name} ?')"></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${can.isStatus == 1}">
                                                            | <a class="gg-trash" style="margin-top:20px; display:inline-block; color: red"  href="apply?op=deleteApplied&can_id=${can.id}&email=${info.email}"
                                                                 onclick="return confirm('Are you sure to delete the Application in ${can.jobname.job_name} ? \nStage [1/5]: Accepted')"></a>
                                                        </c:when>
                                                        <c:when test="${can.isStatus == 2}">
                                                            | <a class="gg-trash" style="margin-top:20px; display:inline-block; color: red"  href="apply?op=deleteApplied&can_id=${can.id}&email=${info.email}"
                                                                 onclick="return confirm('Are you sure to delete the Application in ${can.jobname.job_name} ? \nScore: ${can.score} \nStage [2/5]: Tested')"></a>
                                                        </c:when>
                                                        <c:when test="${can.isStatus == 3}">
                                                            | <a class="gg-trash" style="margin-top:20px; display:inline-block; color: red"  href="apply?op=deleteApplied&can_id=${can.id}&email=${info.email}"
                                                                 onclick="return confirm('Are you sure to delete the Application in ${can.jobname.job_name} ? \nScore: ${can.score} \nStage [3/5]: Scheduled')"></a>
                                                        </c:when>
                                                        <c:when test="${can.isStatus == 4}">
                                                            | <a class="gg-trash" style="margin-top:20px; display:inline-block; color: red"  href="apply?op=deleteApplied&can_id=${can.id}&email=${info.email}"
                                                                 onclick="return confirm('Are you sure to delete the Application in ${can.jobname.job_name} ? \nScore: ${can.score} \nStage [4/5]: Interviewed')"></a>
                                                        </c:when>
                                                        <c:when test="${can.isStatus == 5}">
                                                            | <a class="gg-trash" style="margin-top:20px; display:inline-block; color: red"  href="apply?op=deleteApplied&can_id=${can.id}&email=${info.email}"
                                                                 onclick="return confirm('Are you sure to delete the Application in ${can.jobname.job_name} ? \nScore: ${can.score} \nStage [5/5]: Hired')"></a>
                                                        </c:when>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                    </c:choose>
                                    <c:if test="${can.isStatus==1}">
                                        <br/>
                                        <a href="exam?op=confirmExam&canId=${can.id}">Attempt Exam</a>
                                    </c:if> 
                                </td>
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
        <style>
            a:hover {
                color: red;
                background-color: transparent;
                text-decoration: underline;
            }
            a:active {
                color: #66D7A7;
                background-color: transparent;
                text-decoration: underline;
            }
        </style>
    </body>
</html>
