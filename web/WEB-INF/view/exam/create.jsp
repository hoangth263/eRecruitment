<%-- 
    Document   : questionBank
    Created on : Sep 27, 2022, 3:12:55 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <!--<link href="<c:url value="/site2.css"/>" rel="stylesheet" type="text/css">-->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <style>
            h1{
                text-align: center;
            }
            hr{
                height: 1px;
                background-color: blue;
                border: none;
            }
            .create-exam{
                border-radius: 10px;
                background:  #C7C7C7;
                padding: 25px 30px;
            }
            .question-input{
                border-radius: 10px;
                background-color: #E3E3E3;
                margin:  20px;
                padding: 20px;
            }
            textarea, input{
                border: none; 
                margin:  10px 5px;
            }

        </style>

    </head>
    <body>

        <h1>Add new question</h1>
        <div class="container">
            <div class="create-exam">
                <form action="<c:url value="/exam"/>" id="mainform" onsubmit="return validateForm()" method="post">
                    <div>
                        <textarea type="text" placeholder="Enter Question" name="question" cols="140" rows="10" style="resize: none;" required="true"></textarea>
                    </div>
                    <select name="major">
                        <c:forEach var="major" items="${listMajor}">
                            <option value="${major.major_id}">${major.major_name}</option>
                        </c:forEach>
                    </select>

                    <div class="question-input">
                        <table>
                            <tbody id = "main">
                                <tr>
                                    <td class="text-center" >
                                        <input type="radio" value="1"  name="correctOptions" required="true"/>
                                    </td>
                                    <td class="text-center" >
                                        <input type="text" placeholder="Enter Option"  style="width: 680px;" name="option1" required="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-center" >
                                        <input type="radio" value="2" class="radoOption" name="correctOptions" required="true"/>
                                    </td >
                                    <td>
                                        <input type="text" placeholder="Enter Option" style="width: 680px;" class="someInput" name="option2" required="true"/>
                                    </td>
                                </tr>
                            </tbody>
                            <input type="hidden" id="countInput" name="count" value="2"/>
                        </table>
                        <div>
                            <input type="button" id="addButton" class="opbutton" value="Add" onclick="add(2)"  style="width: 12%"/>
                            <input type="button" id="removeButton"  value="Remove" onclick="remove(2)"  style="width: 12%" disabled="true"/>
                        </div>
                    </div>
                    <div >
                        <button style="font-size: 15px" class="btn btn-primary" type="submit" name="op" value="Create">Create</button>
                    </div>
                </form>
                <br/>
                <div>
                    <a href="<c:url value="/exam?op=QuestionBank"/>">
                        <button class="btn" >Cancel</button>
                    </a>
                </div>
            </div>

            <script src="<c:url value="/js/hoang.js"/>"></script> 
    </body>
</html>