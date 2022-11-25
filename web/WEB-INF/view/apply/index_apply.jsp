<%-- 
    Document   : index_apply
    Created on : Oct 18, 2022, 8:06:22 PM
    Author     : ADMIN
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>


        <!--<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>-->

    </head>
    <body>
        <div class="container" style="text-align: left">

            <form  action="<c:url value="/apply?op=uploadFile&email=${info.email}"/>" enctype="multipart/form-data" method="post">
                <input type="hidden" value="${job_id}" name="job_id">
                <input type="hidden" value="${job_name}" name="job_name">
                <input type="hidden" value="${cv}" name="cv" id="choosenFile">
                <p>Select Your CV:</p>
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" title="choose">
                    Choose a CV in the past.
                </button>
                <!-- The Modal -->
                <div class="modal fade" id="myModal" role="dialog">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-body">
                                <div style="margin-top: 10px; margin-bottom: 20px; margin-right: 10px">
                                    <button type="button" class="close" data-dismiss="modal" >&times;</button>
                                </div>
                                <c:if test="${not empty cvs}">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>No.</th>
                                                <th>CV</th>
                                                <th>Upload Date</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="cv" items="${cvs}" varStatus="loop">
                                                <tr id="cv-${loop.count}" >
                                                    <td>${loop.count}</td>
                                                    <td>${cv.can_cv}</td>
                                                    <td>
                                                        <fmt:formatDate value="${cv.date}" pattern="yyyy-MM-dd"/>
                                                    </td>
                                                </tr>
                                            <script>
                                                $(document).ready(function () {
                                                    $("#cv-" + ${loop.count}).click(function () {
                                                        $("#myModal").modal("toggle");
                                                        $(".custom-file-input").siblings(".custom-file-label").addClass("selected").html("${cvs[loop.count-1].can_cv}");
                                                        $("#choosenFile").val("${cvs[loop.count-1].can_cv}");
                                                        $("#submitbutton").removeAttr("disabled");
                                                    });
                                                });
                                            </script>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </c:if>
                                <c:if test="${empty cvs}">
                                    <h4 style="text-align: center; margin-bottom: 50px">You don't have any uploaded cv yet!</h4>
                                </c:if>
                            </div>

                        </div>
                    </div>
                </div>
                <span style="margin-right: 10px; margin-left: 10px">Or</span>
                <div class="custom-file mb-3" style="width: 45%">
                    <input type="file" class="custom-file-input" id="customFile" name="filename" accept=".pdf,.doc,.docx" onchange="validateFileType()">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
                <p>
                    <i style="color: red;">*Only accept .pdf, .doc, .docx file</i>
                </p>
                <div >
                    <button type="submit" class="btn btn-success" id="submitbutton" disabled="true">Apply CV</button>
                </div>
            </form>

        </div>
        <script>
// Add the following code if you want the name of the file appear on select
            $(document).ready(function () {
                $(".custom-file-input").on("change", function () {
                    var fileName = $(this).val().split("\\").pop();
                    $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
                });
            });
            document.getElementById('submitbutton').disabled = true;
            function validateFileType() {
                var fileName = document.getElementById("customFile").value;
                var idxDot = fileName.lastIndexOf(".") + 1;
                var extFile = fileName.substr(idxDot, fileName.length).toLowerCase();
                if (extFile === "pdf" || extFile === "doc" || extFile === "docx") {
                    document.getElementById('submitbutton').disabled = false;
                } else {
                    alert("Only .pdf, .doc, .docx files are allowed !");
                    document.getElementById('submitbutton').disabled = true;
                }
            }
        </script>
    </body>
</html>
