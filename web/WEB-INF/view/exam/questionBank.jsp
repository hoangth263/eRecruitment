<%-- 
    Document   : questionBank
    Created on : Oct 4, 2022, 4:58:19 PM
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
        <link rel="stylesheet" href="css/style.css" type="text/css">
        <style>
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
                border-style:solid;
                border-color: #96D4D4;
            }
            .update-btn{
                text-align: right;
                margin: 0 auto;
            }
            .option{
                display: block;
                padding: 20px;

            }
            .center {
                text-align: center;
            }

            .pagination {
                display: inline-block;
            }

            .pagination a {
                color: black;
                float: left;
                padding: 8px 16px;
                text-decoration: none;
                transition: background-color .3s;
                border: 1px solid #ddd;
                margin: 0 4px;
            }

            .pagination a.active {
                background-color: #4CAF50;
                color: white;
                border: 1px solid #4CAF50;
            }

            .pagination a:hover:not(.active) {background-color: #ddd;}

        </style>
    </head>
    <body>

        <h1 style="text-align: center">Question bank</h1>
        <div class="container">
            <button class="btn btn-danger"onclick="document.querySelector('dialog').showModal()" >Create Exam </button>
            <a href="<c:url value="/exam?op=Add"/>"
               <button class="btn btn-danger" >Add question</button>
            </a>
            <div class="row">
                <div class="col-sm">                   
                </div>
                <div class="col-lg-3">                   
                    <li class="dropdown dropdown-1">
                        <div>
                            <span>Major</span>
                            <span class="arrow_carrot-down"></span>
                        </div>
                        <ul class="dropdown_menu dropdown_menu-1">
                            <c:forEach var="major" items="${listMajor}">
                                <li class="dropdown_item-1">
                                    <a href="<c:url value='/exam?op=QuestionBank&major=${major.major_id}'/>">${major.major_name}</a></li>
                                </c:forEach>
                        </ul>
                    </li>
                </div>
                <div class="col-sm">                   
                </div>
            </div>
        </div>


        <div>
            <dialog>
                <div class="modal-content">
                    <h5 class="modal-title">Create Exam</h5>
                    <form action="<c:url value="/exam"/>" method="post">  
                        <div class="input-group mb-3">
                            <input type="hidden" value="CreateExam" name="op"/>
                            <span class="input-group-text">Exam name:</span>
                            <input class="form-control" type="text" name="name" required="true"/>
                        </div>

                        <div class="input-group mb-3">
                            <span class="input-group-text mb-3">Major:</span>
                            <select class="form-control" name="major">
                                <c:forEach var="major" items="${listMajor}" >
                                    <option value="${major.major_id}" >${major.major_name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="input-group mb-3">
                            <span class="input-group-text">Number Of Question :</span>
                            <input class="form-control" type="number" name="numOfQuestion" min="1" max="10" required="true" value="10"/>
                        </div>
                        <button type="submit" class="btn btn-secondary">Update</button>
                        <button onclick="document.querySelector('dialog').close()" class="btn btn-secondary" >Cancel</button>
                    </form> 
                </div>
            </dialog>
        </div>
        <div class="container">
            <div class="list-question">
                <c:set var="No" value="${20*page+1}"/>
                <c:forEach items="${listQuestion}" var="question" begin="${20*page}" end="${(page+1)*20-1}">
                    <li style="border: 1px solid blue; padding: 10px; margin: 25px 0; background: #C2C1C5;" >
                        <span class="question-title">${No}. Question : ${question.questiontxt} </span>
                        <c:set var="No" value="${No+1}"/>
                        <span class="question-major">
                            Major : 
                            <c:forEach var="major" items="${listMajor}">
                                <c:if test="${major.major_id == question.major_id}">${major.major_name}</c:if>
                            </c:forEach>
                        </span>

                        <input type="hidden" name="q_id" value="${question.q_id}"/>
                        <div id = "main" class="list-option">
                            <c:set var="numOfOption" value="0"/>
                            <c:forEach var="option" items="${listOption}">
                                <c:if test="${option.q_id == question.q_id}">
                                    <div class="input-group">
                                        <input type="text" id="disabledTextInput" class="form-control"  value="${option.content}"
                                               <c:if test="${option.isCorrect}"> 
                                                   style="color: white; background-color: #58c190; font-style: italic"
                                               </c:if>
                                               >
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                        <div class="update-btn">
                            <a href="<c:url value="/exam?op=Update&q_id=${question.q_id}"/>">
                                <button class="btn btn-primary">Update question</button>
                            </a>
                        </div>
                    </li>
                </c:forEach>
            </div>
        </div>
        <div class="center">
            <div class="pagination">
                <c:choose>
                    <c:when test="${viewMajor!=null}">
                        <c:if test="${page!=0 && page!=1}">
                            <a href="<c:url value="/exam?op=QuestionBank&major=${viewMajor}&page=${page-1}"/>">&laquo;</a>
                        </c:if>
                        <c:if test="${page==1}">
                            <a href="<c:url value="/exam?op=QuestionBank&major=${viewMajor}"/>">&laquo;</a>
                        </c:if>
                        <a  class="active">${page+1}</a>
                        <c:if test="${page!=size}">
                            <a href="<c:url value="/exam?op=QuestionBank&major=${viewMajor}&page=${page+1}"/>">&raquo;</a>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${page!=0 && page!=1}">
                            <a href="<c:url value="/exam?op=QuestionBank&page=${page-1}"/>">&laquo;</a>
                        </c:if>
                        <c:if test="${page==1}">
                            <a href="<c:url value="/exam?op=QuestionBank"/>">&laquo;</a>
                        </c:if>
                        <a  class="active">${page+1}</a>
                        <c:if test="${page!=size}">
                            <a href="<c:url value="/exam?op=QuestionBank&page=${page+1}"/>">&raquo;</a>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>
