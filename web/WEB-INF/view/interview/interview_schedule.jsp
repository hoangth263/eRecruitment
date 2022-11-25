<%-- 
    Document   : interview_schedule
    Created on : Oct 14, 2022, 10:05:34 AM
    Author     : Thien's
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Interview Schedule</title>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <%--css--%>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link rel="stylesheet" href="<c:url value='/css/thien.css'/>" type="text/css">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body>
        <div class="container">
            <c:if test="${not empty interview}">
                <div class="row">
                    <nav class="interview-table">
                        <ul>
                            <li style="padding-left: 30px;">Name</li>
                            <li style="padding-left: 50px;">Interview's Date</li>
                            <li >Location</li>
                            <li style="padding-left: -20px;">Status</li>
                        </ul>
                    </nav>
                </div>
                <div>
                    <nav class="interview-table-content">
                        <c:forEach var="i" items="${sublist}" varStatus="loop">
                            <div style="border-radius: 10px; padding-bottom: 0;margin-bottom: 50px;">
                                <div class="set-can interview-information" onclick="openForm(${loop.count})">
                                    <ul class="">
                                        <li style="text-align: left">${i.can_name}</li>
                                        <li><fmt:formatDate value="${i.date}" pattern="dd-MM-yyyy HH:mm"/></li>
                                        <li>${i.location}</li>
                                            <c:if test="${i.status=='Has Interviewed'}">
                                            <li style="color: #007fff">${i.status}</li>
                                            </c:if>
                                            <c:if test="${i.status=='Hasn\'t Interviewed'}">
                                            <li style="color: #fa732a">${i.status}</li>
                                            </c:if>
                                    </ul>
                                    <c:if test="${i.status=='Has Interviewed'}">
                                        <!-- Trigger/Open The Modal -->
                                        <button id="myBtn-${loop.count}" class="btn btn-default" style="position: absolute; bottom: 10px; right: 180px">Modify your record</button>

                                        <!-- The Modal -->
                                        <div id="myModal-${loop.count}" class="modal">

                                            <!-- Modal content -->
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h2>Modify your record</h2>
                                                    <span class="close" id="close-${loop.count}">&times;</span>
                                                </div>
                                                <div class="modal-body">
                                                    <form action="<c:url value="/interview"/>">
                                                        <textarea rows="5" cols="110" name="comment"  placeholder="Enter text here..." required>${i.comment}</textarea><br/>
                                                        <input type="number" name="score" value="${i.score}" min='0' max='100' required=""
                                                               style="width: 5%"> &nbsp; /100
                                                        <input type="hidden" name="id" value="${i.id}">
                                                        <input type="hidden" name="can_id" value="${i.can_id}">
                                                        <input type="hidden" name="date" value="<fmt:formatDate value="${i.date}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                                                        <input type="hidden" name="op" value="record">
                                                        <button type="submit" class="interview-record-btn">Submit</button>
                                                    </form>
                                                </div>
                                            </div>
                                            <script>
                                                // Get the modal
                                                var modal = document.getElementById("myModal-" + ${loop.count});

// Get the button that opens the modal
                                                var btn = document.getElementById("myBtn-" + ${loop.count});

// Get the <span> element that closes the modal
                                                var span = document.getElementById("close-"+${loop.count});

// When the user clicks the button, open the modal 
                                                btn.onclick = function () {
                                                    modal = document.getElementById("myModal-" + ${loop.count});
                                                    modal.style.display = "block";
                                                }

// When the user clicks on <span> (x), close the modal
                                                span.onclick = function () {
                                                    modal = document.getElementById("myModal-" + ${loop.count});
                                                    modal.style.display = "none";
                                                }

// When the user clicks anywhere outside of the modal, close it
                                                window.onclick = function (event) {
                                                    if (event.target === modal) {
                                                        modal.style.display = "none";
                                                    }
                                                }
                                            </script>
                                        </div>
                                    </c:if> 
                                    <div class="interview-table-content-btn">
                                        <a href="apply?op=downloadFile&fileName=${i.can_cv}">
                                            <div >
                                                <span class="fa fa-download">
                                                    Download CV
                                                </span>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                                <c:if test="${i.status=='Hasn\'t Interviewed'}">
                                    <div class="interview-record set-can" style="text-align: left;" id="record-${loop.count}">
                                        <span style="" onclick="closeForm(${loop.count})">&times;</span>  
                                        <form action="<c:url value="/interview"/>">
                                            <textarea rows="5" cols="130" name="comment"  placeholder="Enter text here..." required></textarea><br/>
                                            <input type="number" name="score" value="${score}" min='0' max='100' required=""
                                                   style="width: 5%"> &nbsp; /100
                                            <input type="hidden" name="id" value="${i.id}">
                                            <input type="hidden" name="can_id" value="${i.can_id}">
                                            <input type="hidden" name="date" value="<fmt:formatDate value="${i.date}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                                            <input type="hidden" name="op" value="record">
                                            <button type="submit" class="interview-record-btn">Submit</button>
                                        </form>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </nav>
                </div>
            </c:if>
            <c:if test="${noOfPage.size() > 1}">
                <ul class="pagination" style="margin-top: 30px">
                    <c:forEach var="p" items="${noOfPage}" varStatus="loop">
                        <li class="${page == loop.count?'active':''}">
                            <a href="<c:url value="/interview?op=interview_schedule&page=${loop.count}"/>">${loop.count}</a>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${empty interview}">
                <h2>There's no interview's schedule!</h2>
            </c:if>
            <c:if test="${not empty message}">
                <script>
                    document.getElementById("error").innerHTML = alert("${message}");
                </script>
            </c:if>
        </div>
        <script>

            function openForm(i) {
                document.getElementById("record-" + i).style.display = "block";
            }

            function closeForm(i) {
                document.getElementById("record-" + i).style.display = "none";
            }



        </script>
    </body>
</html>
