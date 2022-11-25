<%-- 
    Document   : exam
    Created on : Oct 15, 2022, 2:33:45 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <style>
            .update-btn{
                text-decoration: none;
            }
            .question-title{
                display: block;
                text-align: left;

            }
            .question-major{
                display: block;
                text-align: left;
            }
            .list-option{
                background: #E4E4E4;
                display: block;
                padding: 10px 2px;
                margin: 10px 0;
                text-align: left;
            }
            li{
                list-style-type: none
            }
            dialog {
                width: 500px;
                height: 500px !important;
            }
            .center{
                margin-left: auto !important;
                margin-right: auto !important;
            }
            .bordertest tr td {
                border-style:solid;border-color: #96D4D4;
            }
            .close-window{
                margin-bottom:  40px;
            }
        </style>
    </head>
    <body>

        <h1 style="text-align: center">Exam</h1>

        <div class="container-sm">
            <form action="<c:url value="/exam"/>"  method="post">
                <div class="list-question">
                    <input type="hidden" name="op" value="result" />
                    <input type="hidden" name="canId" value="${canId}" />
                    <c:forEach items="${listQuestion}" var="question" varStatus="loop">
                        <li style="border: 1px solid blue; padding: 10px; margin: 25px 0; background: #C2C1C5;" >
                            <span class="question-title">${loop.count}. Question : ${question.questiontxt} </span>


                            <table id = "main" class="list-option">
                                <%--<c:set var="numOfOption" value="0"/>--%>
                                <c:forEach var="option" items="${listOption}">
                                    <input type="radio" name="answer${loop.count}" value="0" checked="true" hidden="true">
                                    <c:if test="${option.q_id == question.q_id}">
                                        <tr>
                                            <td>
                                                <label>
                                                    <input type="radio" name="answer${loop.count}" value="${option.op_id}">  ${option.content}
                                                </label>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </table>
                        </li>
                    </c:forEach>
                    <br/>
                </div>
                <div class="d-flex justify-content-center align-items-center flex-column gap-5 pb-4">                
                    <button type="submit" class="btn btn-primary mx-auto d-inline-block">Submit</button>
                    <button type="button" class="" onclick="window.close();">Close current window if possible</button>
                </div>
            </form>
        </div>
    </body>
</html>
