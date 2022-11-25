<%-- 
    Document   : feedback
    Created on : Nov 3, 2022, 12:37:03 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <p style="background-color: #99ff99">
                ${message}
            </p>
            <c:choose>
                <c:when test="${role == 'Admin' || role == 'HR Staff' && not empty info}">
                    <c:if test="${listFeedback==null}">
                        <h2>Nothing at the moment!</h2>
                    </c:if>
                    <c:forEach items="${listFeedback}" var="feedback">
                        ${feedback.subject} <br/>
                        ${feedback.email} <br/>
                        ${feedback.detail}<br/>
                        <fmt:formatDate type = "date" value = "${feedback.date}" /> <br/>
                        <a href="<c:url value="/home?op=delete&id=${feedback.id}"/>"><button>Delete</button></a>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h2>We'd like your feedback</h2>
                    <div class="feedback_form">
                        <form action="<c:url value="/home"/>" >
                            <div class="feedback_item">
                                <label>Feedback Subject:</label>
                                <input  class="feedback_input" type="text" name="subject"  maxlength="150" size="35" required="true"/>
                            </div>
                            <div class="feedback_item">
                                <label> Email:</label> 
                                <input class="feedback_input" type="text" name="email" maxlength="60" value="${feedbackEmail}"  size="35" required="true"/>
                            </div>
                            <div class="feedback_item">
                                <label>Detail:</label> 
                                <textarea class="feedback_input" type="text" name="detail"  maxlength="1000"  placeholder="Enter message ..."></textarea>
                                <input type="hidden" name="op" value="sendFeedback" required="true"/>
                            </div>
                            <p>
                                <input type="submit" class="btn btn-primary"/>
                            </p>
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
