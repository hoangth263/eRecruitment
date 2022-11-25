<%-- 
    Document   : validation
    Created on : Oct 17, 2022, 5:18:23 PM
    Author     : Thien's
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verification</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <h2>Account Verification</h2>

        <p>Please input verification code:</p>
        <form action="<c:url value="/login"/>" method="post">
            G - <input type="text" name="inputCode" value="${inputCode}" required>
            <input type="hidden" name="code" value="${code}">
            <button type="submit" name="op" value="verification_handler">Submit</button>
        </form><br/>
        <a href="<c:url value="/login?op=verification_handler&send=true"/>">
            Don't see your email? Send again &#8594
        </a><br/>
        <i style="color: red;">${message}</i>
    </body>
</html>
