<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
        <!--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">-->

    </head>
    <body onload="generate()">
        <!--Alert-->
        <c:if test="${not empty msgFailed}">
            <div class="alert alert-danger alert-dismissible">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <strong>Warning!</strong> ${msgFailed} <br>
            </div>
        </c:if>
        <!--Alert-->
        <div class="container " >
            <div class="input-group " style="justify-content: center; align-items: center;">

                <form action="<c:url value="/qa"/>" style="" name="myForm" onsubmit="return validateForm()" method="post" required >
                    <div class="input-group mb-3">
                        <span class="input-group-text disabled" >Email:</span>
                        <input placeholder="${info.email}" class="form-control disabled" disabled />
                    </div>
                    <div class="input-group mb-3" s>
                        <span class="input-group-text">Detail:</span>
                        <textarea placeholder="Enter Your Question" class="form-control" type="text" name="detail" rows="1" cols="50"></textarea>
                    </div>

                    <!--                    Captcha
                                        <div id="user-input" class="inline">
                                            <input type="text" id="submit"
                                                   placeholder="Captcha code" />
                                        </div>
                    
                                        <div class="inline" onclick="generate()">
                                            <i class="fas fa-sync"></i>
                                        </div>
                    
                                        <div id="image" class="inline" selectable="False">
                                        </div>
                                        <input type="submit" id="btn" onclick="printmsg()" />
                    
                                        <p id="key"></p>
                                        Captcha-->

                    <%--                    <a class="btn btn-success" style="color: #ffffff !important; border-color: #66D7A7;
                                           background: #66D7A7; border-style: solid; text-transform: uppercase; font-weight: 500;
                                           width: 100px" 
                                           <c:choose>
                                               <c:when test="${not empty info}"> href="<c:url value="/qa?op=qa_question&email=${info.email}&detail=${detail}"/>"
                                               
                                               </c:when>
                                               <c:otherwise> 
                                                   href="<c:url value="https://accounts.google.com/o/oauth2/auth?scope=email  profile&redirect_uri=http://localhost:8084/recruitment-system/login?op=login&response_type=code&client_id=779040387699-c58vkqmlf6cmvtv3som469pl5k78lgar.apps.googleusercontent.com&approval_prompt=force"/>"
                                               </c:otherwise> 
                                           </c:choose>>ADD
                                        </a> 
                    --%>
                    <button class="btn btn-primary" type="submit" name="op" value="qa_question">ADD</button>
                </form>
            </div>

        </div>
        <br/>
        <!--TABLE-->
        <!--<table class="table table-striped" border="1" cellspacing="0" cellpadding="4">-->  
        <div>
            <c:choose>
                <c:when test="${not empty listQ}">
<!--                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Email</th>
                            <th>Detail</th>
                            <th>Date Time</th>
                            <th style="text-align: center">Operation</th>    
                        </tr>
                    </thead>-->
                    <tbody>

                        <c:forEach var="can" items="${listQ}" varStatus="loop">
                            <tr>
                                <td style="text-align: left;"><fmt:formatNumber value="${loop.count}" pattern="000" /></td>
                                <td>${can.qId}</td>
                                <td>${can.email}</td>
                                <td>${can.detail}</td>
                                <td>${can.datetime}

                                </td>

                                <td style="text-align: center">
                                    <a href="apply?op=downloadFile&fileName=${can.qId}">Download</a>
                                </td>

                            </tr>

                        </c:forEach>
                    </tbody>
                </c:when>
                <c:otherwise>
                    <c:out value="NO DATA FOUND"/>
                </c:otherwise>
            </c:choose>
            <!--</table>-->
        </div>

        <script>
            var captcha;
            function generate() {

                // Clear old input
                document.getElementById("submit").value = "";

                // Access the element to store
                // the generated captcha
                captcha = document.getElementById("image");
                var uniquechar = "";

                const randomchar =
                        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

                // Generate captcha for length of
                // 5 with random character
                for (let i = 1; i < 5; i++) {
                    uniquechar += randomchar.charAt(
                            Math.random() * randomchar.length)
                }

                // Store generated input
                captcha.innerHTML = uniquechar;
            }

            function printmsg() {
                const usr_input = document
                        .getElementById("submit").value;

                // Check whether the input is equal
                // to generated captcha or not
                if (usr_input == captcha.innerHTML) {
                    var s = document.getElementById("key")
                            .innerHTML = "Matched";
                    generate();
                } else {
                    var s = document.getElementById("key")
                            .innerHTML = "not Matched";
                    generate();
                }
            }

        </script>
    </body>
</html>
