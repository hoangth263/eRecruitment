<%-- 
    Document   : result
    Created on : Oct 15, 2022, 2:33:55 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/style.css" type="text/css">
        <style>
            .result{
                text-align: center;
                padding: 25px 0;
            }
        </style>

    </head>
    <body>

        <div class="container">
            <c:if test="${testInfo!=null}">
                <div style="text-align: center; margin: 100px 50px 0 50px">
                    <h3>
                        Test name: ${testInfo.exam_id}
                    </h3>
                    <h5>Candidate ID: ${canId} | Major: <c:forEach items="${listMajor}" var="major"><c:if test="${major.major_id==testInfo.major_id}">${major.major_name}</c:if></c:forEach></h5><br/>
                            <p> 
                                <b style="color: red">
                                    Warning: You need to score at least 4.0 to be interviewed. Otherwise, this application will be rejected.
                                </b>
                            </p>
                            <a href="<c:url value="/exam?op=takeExam&canId=${canId}"/>">
                        <button class=" btn btn-info">Attempt exam</button>
                    </a>
                </div>
            </c:if>
            <div class="result">
                <h2>${message}</h2>
                <c:if test="${mark != null}">
                    Score : ${mark} <br/>
                </c:if>
                <a class="return-home-btn" href="<c:url value="/home?op=index"/>">
                    <button class=" btn btn-info">Return to home page</button>
                </a>
                <c:if test="${not empty info}">
                    <a class="return-home-btn" href="<c:url value="/user?op=info"/>">
                        <button class=" btn btn-info">Return to Info page</button>
                    </a>
                </c:if>
            </div>
        </div>

    </body>
</html>
