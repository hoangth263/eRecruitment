<%-- 
    Document   : search
    Created on : Oct 6, 2022, 4:47:37 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    </head>
    <body>
        <style>
            .form-popup {
                position: absolute;
                display: none;
                top: 0;
                right: 0px;
                border: 3px solid black;
                z-index: 1;
                background-color: white; 
                margin-bottom: 50px;
                padding-left: 20px;
            }
            .form-container {
                height: 500px;
                padding: 10px;
                width: 95%;

            }
            .job-box-0:hover{
                cursor: pointer;
                box-shadow: 1px 1px 2px red, 0 0 25px red, 0 0 5px red;
                transition-duration: 0.2s;
            }
            .job-box:hover{
                cursor: pointer;
                box-shadow: 1px 1px 2px #66d7a7, 0 0 25px #66d7a7, 0 0 5px #66d7a7;
                transition-duration: 0.2s;
            }
        </style>
        <script>
            let last = null;
            function job_detail(job_id) {
                if (last !== null) {
                    document.getElementById("view_job_detail" + last).style.display = "none";
                }
                document.getElementById("view_job_detail" + job_id).style.display = "block";
                last = job_id;
            }
            $(document).ready(function () {
                $("div[title|= 'lastDiv']").css("position", "static");
                $("div[title|= 'lastDiv']").css("width", "100%");
                $("div[title|= 'lastDiv']").css("margin-left", "15px");
            });

            $(document).on("click", ".open-AddBookDialog", function () {
                var myBookId = $(this).data('id');
                $(".modal-body #bookId").val(myBookId);
                // As pointed out in comments, 
                // it is unnecessary to have to manually call the modal.
                // $('#addBookDialog').modal('show');
            });
        </script>
        <div class="container">
            <c:if test="${not empty info && role == 'HR Staff'}">
                <a class="link" style="float: right" href="<c:url value="/job?op=add_job"/>">Add a new job</a><br/>
            </c:if>
            <c:if test="${not empty message}">
                <script>
                    alert("${message}");
                </script>
            </c:if>
            <form action="<c:url value="/job"/>">
                <ul style="position: relative">
                    <div style="display: flex;">
                        <div class="input-group">

                            <li class="input-group-text">
                                Major
                            </li>   
                            <li style="list-style-type: none;">
                                <select name="major_id" >
                                    <option value="0" >All</option>
                                    <option value="1"<c:if test="${major_id==1}">selected="true"</c:if>>Marketing</option>
                                    <option value="2"<c:if test="${major_id==2}">selected="true"</c:if>>Logistic</option>
                                    <option value="3"<c:if test="${major_id==3}">selected="true"</c:if>>Data Science and Analytics</option>
                                    <option value="4"<c:if test="${major_id==4}">selected="true"</c:if>>Information Technology</option>
                                    <option value="5"<c:if test="${major_id==5}">selected="true"</c:if>>Graphic Design</option>
                                    <option value="6"<c:if test="${major_id==6}">selected="true"</c:if>>Engineering</option>
                                    <option value="7"<c:if test="${major_id==7}">selected="true"</c:if>>Risk Manager</option>
                                    <option value="8"<c:if test="${major_id==8}">selected="true"</c:if>>Economic Finance</option>
                                    <option value="9"<c:if test="${major_id==9}">selected="true"</c:if>>Business Intelligence and Development</option>
                                    <option value="10"<c:if test="${major_id==10}">selected="true"</c:if>>Operations</option>
                                    </select>
                                </li>
                            </div>
                            <div class="input-group">

                                <li class="input-group-text">
                                    Level:  
                                </li>   
                                <li style="list-style-type: none;">
                                    <select name="level_id">
                                        <option value="0">All</option>
                                        <option value="1"<c:if test="${level_id==1}">selected="true"</c:if>>Intern</option>
                                    <option value="2"<c:if test="${level_id==2}">selected="true"</c:if>>Fresher</option>
                                    <option value="3"<c:if test="${level_id==3}">selected="true"</c:if>>Junior</option>
                                    <option value="4"<c:if test="${level_id==4}">selected="true"</c:if>>Senior</option>
                                    <option value="5"<c:if test="${level_id==5}">selected="true"</c:if>>Manager</option>
                                    <option value="6"<c:if test="${level_id==6}">selected="true"</c:if>>Leader</option>
                                    </select>
                                </li>
                            </div>
                            <div class="input-group">
                                <li class="input-group-text">
                                    Salary:  
                                </li>   
                                <li style="list-style-type: none;">
                                    <select name="salary">
                                        <option value="0">All</option>
                                        <option value="1"<c:if test="${salary==1}">selected="true"</c:if>> less than 1000 </option>
                                    <option value="2"<c:if test="${salary==2}">selected="true"</c:if>> 1000 to 5000</option>
                                    <option value="3"<c:if test="${salary==3}">selected="true"</c:if>> more than 5000</option>
                                    </select>
                                </li>
                            </div>
                        </div>
                        <span style="display: inline-block;position: absolute;top: 5px;right:0"><button type="submit" name="op" value="filter_job">Search</button></span>
                    </ul>
                </form>
            </div>
        <c:if test="${empty list}">
            <h3>No result!</h3>
        </c:if>
        <br>
        <c:if test="${not empty msg}">
            <div class="alert alert-success alert-dismissible container">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${msg}
            </div>
        </c:if>
        <c:if test="${not empty msgFailed}">
            <div class="alert alert-danger alert-dismissible container">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${msgFailed} <br>
                <script>
                    var message = "${msgFailed}";
                    alert(message);
                </script>
            </div>
        </c:if>
        <c:if test="${not empty checkApplied}">
            <div class="alert alert-success alert-dismissible container">
                <a class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <!--<strong>You already have Accepted Resume</strong>-->
                <!--<br>-->
                <a href="<c:url value="/user?op=info"/>" style="text-decoration: underline"><strong>You already have Accepted Resume</strong></a>
            </div>
        </c:if>
        <c:if test="${not empty list}">
            <div class="container" style="margin-top: 5%">
                <c:forEach var="job" items="${list}" varStatus="loop">
                    <c:if test="${job.job_vacancy>0}">
                        <div class="row">
                            <div class="col-md-6 " onclick="job_detail(${loop.count})">
                                <div style="background-color: #59c5dc; border-radius: 10px; margin-bottom: 50px;padding-left: 20px;" class="job-box" >
                                    <h4 style="text-align: left;color: black;font-style: italic;">
                                        Job Name: ${job.job_name}
                                    </h4> <br/>
                                    <div style="text-align: left; width: 100%" >
                                        <p style="display: inline-block; margin-right: 30px;">
                                            Major Name: ${job.major_name} 
                                        </p>
                                        <p style="display: inline-block; margin-right: 30px">
                                            Level: ${job.level_name} <br/>
                                        </p>
                                        <p style="display: inline-block; margin-right: 30px;">
                                            Vacancy: ${job.job_vacancy} 
                                        </p>
                                    </div>
                                    <p style="text-align: left;font-style: italic;">
                                        Post Date: ${job.post_date} <br/>
                                    </p>
                                    <!--Check Role != Member / Candidate-->
                                    <c:choose>
                                        <c:when test="${role == 'Member' ||role == 'Candidate' || empty info}">
                                            <p style="text-align: right;font-style: italic;">
                                                <!--Validate Applied - Apply-->
                                                <c:forEach var="app" items="${listApplied}">
                                                    <c:if  test="${app.jobId == job.job_id }">
                                                        <c:set var="check" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:choose>
                                                    <c:when test="${check == 1}">
                                                        <a class="btn btn-success disabled" style="color: #ffffff !important; border-color: #66D7A7;
                                                           background: #66D7A7; border-style: solid; text-transform: uppercase; font-weight: 500;
                                                           width: 120px" >
                                                            Applied
                                                        </a>
                                                        <c:set var="check" value="0" />
                                                    </c:when>
                                                </c:choose>
                                                <!--Validate Applied - Apply-->
                                            </p>
                                        </c:when>
                                    </c:choose>
                                    <!--Check Role != Member / Candidate-->
                                </div>
                            </div>


                            <div class="col-md-6"  style="position: relative">
                                <c:if test="${loop.count != list.size()}">
                                    <div class="form-popup form-container" id="view_job_detail${loop.count}" title="lastDiv" style="background-color: white; border-radius: 10px; margin-bottom: 50px;padding-left: 20px">
                                        <div>
                                            <c:if test="${not empty info && role == 'HR Staff'}">
                                                <c:if test="${job.job_vacancy==0}">
                                                    <a class="link" style="float: right" href="<c:url value="/job?op=delete_job&job_id=${job.job_id}"/>">Delete job</a>
                                                </c:if>
                                                <a class="link" style="float: right; margin-right: 5px" href="<c:url value="/job?op=update_job&job_id=${job.job_id}"/>">Update job</a>
                                            </c:if>
                                            <h2 style="text-align: center; color: blue;font-style: italic;">
                                                Job Name: ${job.job_name}
                                            </h2> <br/>
                                            <div style="text-align: left; width: 100%;">
                                                <p style="display: inline-block; margin-right: 30px; color: black">
                                                    Major: ${job.major_name} 
                                                </p>    
                                                <p style="display: inline-block; margin-right: 30px; color: black">
                                                    Level: ${job.level_name} <br/>
                                                </p>
                                            </div>
                                            <p style="text-align: left; color: black">
                                                Description: ${job.job_description} <br/>
                                            </p>
                                            <p style="text-align: left; color: black">
                                                Required Skill: ${job.job_skill} <br/>
                                            </p>
                                            <div style="text-align: left; width: 100%;">
                                                <p style="display: inline-block; margin-right: 30px; color: black">
                                                    Vacancy: ${job.job_vacancy} 
                                                </p>
                                                <p style="display: inline-block; color: black">
                                                    Salary: ${job.salary}$ <br/>
                                                </p>
                                            </div>
                                            <p style="text-align: left;color: grey;font-style: italic;">
                                                Post Date: ${job.post_date} <br/>
                                            </p>
                                            <!--Check Role != Member / Candidate-->
                                            <c:choose>
                                                <c:when test="${role == 'Member' ||role == 'Candidate' || empty info}">
                                                    <p style="text-align: right;font-style: italic;">
                                                        <!--Validate Applied - Apply-->
                                                        <c:forEach var="app" items="${listApplied}">
                                                            <c:if  test="${app.jobId == job.job_id }">
                                                                <c:set var="check" value="1" />
                                                            </c:if>
                                                        </c:forEach>
                                                        <c:choose>
                                                            <c:when test="${check == 1}">
                                                                <a class="btn btn-success disabled" style="color: #ffffff !important; border-color: #66D7A7;
                                                                   background: #66D7A7; border-style: solid; text-transform: uppercase; font-weight: 500;
                                                                   width: 120px" >
                                                                    Applied
                                                                </a>
                                                                <c:set var="check" value="0" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                <!--Check Accepted CV ?-->
                                                                <c:choose>
                                                                    <c:when test="${not empty info && not empty checkApplied}" >
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <a class="btn btn-success" style="color: #ffffff !important; border-color: #66D7A7;
                                                                           background: #66D7A7; border-style: solid; text-transform: uppercase; font-weight: 500;
                                                                           width: 120px" 
                                                                           <c:choose>
                                                                               <c:when test="${not empty info}"> href="<c:url value="/apply?op=index&job_id=${job.job_id}&job_name=${job.job_name}"/>"
                                                                               </c:when>
                                                                               <c:otherwise>  href="<c:url value="https://accounts.google.com/o/oauth2/auth?scope=email  profile&redirect_uri=http://localhost:8084/recruitment-system/login?op=login&response_type=code&client_id=779040387699-c58vkqmlf6cmvtv3som469pl5k78lgar.apps.googleusercontent.com&approval_prompt=force"/>"
                                                                               </c:otherwise> 
                                                                           </c:choose>>Apply
                                                                        </a>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <!--Check Accepted CV ?-->
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <!--Validate Applied - Apply-->
                                                    </p>
                                                </c:when>
                                            </c:choose>
                                            <!--Check Role != Member / Candidate-->
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${loop.count == list.size()}">
                                    <div title="lastDiv" class="form-popup form-container" id="view_job_detail${loop.count}" style="background-color: white; border-radius: 10px; margin-bottom: 50px;padding-left: 20px;">
                                        <div>
                                            <c:if test="${not empty info && role == 'HR Staff'}">
                                                <a class="link" style="float: right; margin-right: 5px" href="<c:url value="/job?op=update_job&job_id=${job.job_id}"/>">Update job</a>
                                            </c:if>
                                            <h2 style="text-align: center; color: black;font-style: italic;">
                                                Job Name: ${job.job_name}
                                            </h2> <br/>
                                            <div style="text-align: left; width: 100%;">
                                                <p style="display: inline-block; margin-right: 30px; color: black">
                                                    Major: ${job.major_name} 
                                                </p>    
                                                <p style="display: inline-block; margin-right: 30px; color: black">
                                                    Level: ${job.level_name} <br/>
                                                </p>
                                            </div>
                                            <p style="text-align: left; color: black">
                                                Description: ${job.job_description} <br/>
                                            </p>
                                            <p style="text-align: left; color: black">
                                                Require Skill: ${job.job_skill} <br/>
                                            </p>
                                            <div style="text-align: left; width: 100%;">
                                                <p style="display: inline-block; margin-right: 30px; color: black">
                                                    Vacancy: ${job.job_vacancy} 
                                                </p>
                                                <p style="display: inline-block; color: black">
                                                    Salary: ${job.salary}$ <br/>
                                                </p>
                                            </div>
                                            <p style="text-align: left;color: black;font-style: italic;">
                                                Post Date: ${job.post_date} <br/>
                                            </p>
                                            <p style="text-align: right;">
                                                <!--Validate Applied - Apply-->
                                                <c:forEach var="app" items="${listApplied}">
                                                    <c:if  test="${app.jobId == job.job_id }">
                                                        <c:set var="check" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:choose>
                                                    <c:when test="${check == 1}">
                                                        <a class="btn btn-success disabled" style="color: #ffffff !important; border-color: #66D7A7;
                                                           background: #66D7A7; border-style: solid; text-transform: uppercase; font-weight: 500;
                                                           width: 100px" >
                                                            Applied
                                                        </a>
                                                        <c:set var="check" value="0" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <!--Check Accepted CV ?-->
                                                        <c:choose>
                                                            <c:when test="${not empty info && not empty checkApplied}" >
                                                                <a class="btn btn-success" style="color: #ffffff !important; border-color: #66D7A7;
                                                                   background: #66D7A7; border-style: solid; text-transform: uppercase; font-weight: 500;
                                                                   width: 100px" onclick="return confirm('Your CV had accepted. \nDo you want submit another CV on Job: ${job.job_name}?');"
                                                                   <c:choose>
                                                                       <c:when test="${not empty info}"> href="<c:url value="/apply?op=index&job_id=${job.job_id}&job_name=${job.job_name}"/>"
                                                                       </c:when>
                                                                       <c:otherwise>  href="<c:url value="https://accounts.google.com/o/oauth2/auth?scope=email  profile&redirect_uri=http://localhost:8084/recruitment-system/login?op=login&response_type=code&client_id=779040387699-c58vkqmlf6cmvtv3som469pl5k78lgar.apps.googleusercontent.com&approval_prompt=force"/>"
                                                                       </c:otherwise> 
                                                                   </c:choose>>Apply
                                                                </a>
                                                                <c:set var="check" value="0" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a class="btn btn-success" style="color: #ffffff !important; border-color: #66D7A7;
                                                                   background: #66D7A7; border-style: solid; text-transform: uppercase; font-weight: 500;
                                                                   width: 100px" 
                                                                   <c:choose>
                                                                       <c:when test="${not empty info}"> href="<c:url value="/apply?op=index&job_id=${job.job_id}&job_name=${job.job_name}"/>"
                                                                       </c:when>
                                                                       <c:otherwise>  href="<c:url value="https://accounts.google.com/o/oauth2/auth?scope=email  profile&redirect_uri=http://localhost:8084/recruitment-system/login?op=login&response_type=code&client_id=779040387699-c58vkqmlf6cmvtv3som469pl5k78lgar.apps.googleusercontent.com&approval_prompt=force"/>"
                                                                       </c:otherwise> 
                                                                   </c:choose>>Apply
                                                                </a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <!--Check Accepted CV ?-->
                                                    </c:otherwise>
                                                </c:choose>
                                                <!--Validate Applied - Apply-->
                                            </p>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty info && role == 'HR Staff'}">
                        <c:if test="${job.job_vacancy==0 && not empty job.job_id}">
                            <div class="row">
                                <div class="col-md-6" onclick="job_detail(${loop.count})">
                                    <div style="background-color: #EA7F67; border-radius: 10px; margin-bottom: 50px;padding-left: 20px;" class="job-box-0">
                                        <h4 style="text-align: left;color: black;font-style: italic;">
                                            Job Name: ${job.job_name}
                                        </h4> <br/>
                                        <div style="text-align: left; width: 100%" >
                                            <p style="display: inline-block; margin-right: 30px;">
                                                Major Name: ${job.major_name} 
                                            </p>
                                            <p style="display: inline-block; margin-right: 30px">
                                                Level: ${job.level_name} <br/>
                                            </p>
                                            <p style="display: inline-block; margin-right: 30px;">
                                                Vacancy: ${job.job_vacancy} 
                                            </p>
                                        </div>
                                        <p style="text-align: left;font-style: italic;">
                                            Post Date: ${job.post_date} <br/>
                                        </p>
                                    </div>
                                </div>


                                <div class="col-md-6"  style="position: relative">
                                    <c:if test="${loop.count != list.size()}">
                                        <div class="form-popup form-container" id="view_job_detail${loop.count}" title="lastDiv" style="background-color: white; border-radius: 10px; margin-bottom: 50px;padding-left: 20px">
                                            <div>
                                                <c:if test="${not empty info && role == 'HR Staff'}">
                                                    <c:if test="${job.job_vacancy==0}">
                                                        <a class="link" style="float: right" onclick="return confirm('Are you sure you want to delete this job?');" href="<c:url value="/job?op=delete_job&job_id=${job.job_id}"/>">Delete job</a>
                                                    </c:if>
                                                    <a class="link" style="float: right; margin-right: 5px" href="<c:url value="/job?op=update_job&job_id=${job.job_id}"/>">Update job</a>
                                                </c:if>
                                                <h2 style="text-align: center; color: blue;font-style: italic;">
                                                    Job Name: ${job.job_name}
                                                </h2> <br/>
                                                <div style="text-align: left; width: 100%;">
                                                    <p style="display: inline-block; margin-right: 30px; color: black">
                                                        Major: ${job.major_name} 
                                                    </p>    
                                                    <p style="display: inline-block; margin-right: 30px; color: black">
                                                        Level: ${job.level_name} <br/>
                                                    </p>
                                                </div>
                                                <p style="text-align: left; color: black">
                                                    Description: ${job.job_description} <br/>
                                                </p>
                                                <p style="text-align: left; color: black">
                                                    Required Skill: ${job.job_skill} <br/>
                                                </p>
                                                <div style="text-align: left; width: 100%;">
                                                    <p style="display: inline-block; margin-right: 30px; color: black">
                                                        Vacancy: ${job.job_vacancy} 
                                                    </p>
                                                    <p style="display: inline-block; color: black">
                                                        Salary: ${job.salary}$ <br/>
                                                    </p>
                                                </div>
                                                <p style="text-align: left;color: gray;font-style: italic;">
                                                    Post Date: ${job.post_date} <br/>
                                                </p>
                                                <p style="text-align: right; margin-bottom: 0;">
                                                    <!--<a class="btn btn-success" style="color: #ffffff !important; border-color: #66D7A7;
                                                       background: #66D7A7; border-style: solid; text-transform: uppercase; font-weight: 500;
                                                       width: 100px" <c:choose>
                                                        <c:when test="${not empty info}"> href="<c:url value="/apply?op=index&job_id=${job.job_id}"/>"</c:when>
                                                        <c:otherwise>  href="<c:url value="https://accounts.google.com/o/oauth2/auth?scope=email  profile&redirect_uri=http://localhost:8084/recruitment-system/login?op=login&response_type=code&client_id=779040387699-c58vkqmlf6cmvtv3som469pl5k78lgar.apps.googleusercontent.com&approval_prompt=force"/>"</c:otherwise> 
                                                    </c:choose>>Apply</a>
                                                                                             <a class="btn btn-success" style=" float: left;color: #ffffff !important; border-color: #66D7A7;
                                                                                                background: #66D7A7; border-style: solid; text-transform: uppercase; font-weight: 500;
                                                                                                width: 100px" <c:choose>
                                                        <c:when test="${not empty info}"> href="<c:url value="/job?op=save_job&job_id=${job.job_id}"/>"</c:when>
                                                        <c:otherwise>  href="<c:url value="https://accounts.google.com/o/oauth2/auth?scope=email  profile&redirect_uri=http://localhost:8084/recruitment-system/login?op=login&response_type=code&client_id=779040387699-c58vkqmlf6cmvtv3som469pl5k78lgar.apps.googleusercontent.com&approval_prompt=force"/>"</c:otherwise> 
                                                    </c:choose>>Save</a>-->
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${loop.count == list.size()}">
                                        <div title="lastDiv" class="form-popup form-container" id="view_job_detail${loop.count}" style="background-color: white; border-radius: 10px; margin-bottom: 50px;padding-left: 20px;">
                                            <div>
                                                <c:if test="${not empty info && role == 'HR Staff'}">
                                                    <c:if test="${job.job_vacancy==0}">
                                                        <a class="link" style="float: right" onclick="return confirm('Are you sure you want to delete this job?');" href="<c:url value="/job?op=delete_job&job_id=${job.job_id}"/>">Delete job</a>
                                                    </c:if>
                                                    <a class="link" style="float: right; margin-right: 5px" href="<c:url value="/job?op=update_job&job_id=${job.job_id}"/>">Update job</a>
                                                </c:if>
                                                <h2 style="text-align: center; color: blue;font-style: italic;">
                                                    Job Name: ${job.job_name}
                                                </h2> <br/>
                                                <div style="text-align: left; width: 100%;">
                                                    <p style="display: inline-block; margin-right: 30px; color: black">
                                                        Major: ${job.major_name} 
                                                    </p>    
                                                    <p style="display: inline-block; margin-right: 30px; color: black">
                                                        Level: ${job.level_name} <br/>
                                                    </p>
                                                </div>
                                                <p style="text-align: left; color: black">
                                                    Description: ${job.job_description} <br/>
                                                </p>
                                                <p style="text-align: left; color: black">
                                                    Require Skill: ${job.job_skill} <br/>
                                                </p>
                                                <div style="text-align: left; width: 100%;">
                                                    <p style="display: inline-block; margin-right: 30px; color: black">
                                                        Vacancy: ${job.job_vacancy} 
                                                    </p>
                                                    <p style="display: inline-block; color: black">
                                                        Salary: ${job.salary}$ <br/>
                                                    </p>
                                                </div>
                                                <p style="text-align: left;color: gray;font-style: italic;">
                                                    Post Date: ${job.post_date} <br/>
                                                </p>
                                                <p style="text-align: right;">
                                                    <a class="btn btn-success" style="color: #ffffff !important; border-color: #66D7A7;
                                                       background: #66D7A7; border-style: solid; text-transform: uppercase; font-weight: 500;
                                                       width: 100px" <c:choose>
                                                           <c:when test="${not empty info}"> href="<c:url value="/apply?op=index&job_id=${job.job_id}"/>"</c:when>
                                                           <c:otherwise>  href="<c:url value="https://accounts.google.com/o/oauth2/auth?scope=email  profile&redirect_uri=http://localhost:8084/recruitment-system/login?op=login&response_type=code&client_id=779040387699-c58vkqmlf6cmvtv3som469pl5k78lgar.apps.googleusercontent.com&approval_prompt=force"/>"</c:otherwise> 
                                                       </c:choose>>Apply</a>
                                                    <!--                                            <a class="btn btn-success" style=" float: left;color: #ffffff !important; border-color: #66D7A7;
                                                                                                   background: #66D7A7; border-style: solid; text-transform: uppercase; font-weight: 500;
                                                                                                   width: 100px" <c:choose>
                                                        <c:when test="${not empty info}"> href="<c:url value="/job?op=save_job&job_id=${job.job_id}"/>"</c:when>
                                                        <c:otherwise>  href="<c:url value="https://accounts.google.com/o/oauth2/auth?scope=email  profile&redirect_uri=http://localhost:8084/recruitment-system/login?op=login&response_type=code&client_id=779040387699-c58vkqmlf6cmvtv3som469pl5k78lgar.apps.googleusercontent.com&approval_prompt=force"/>"</c:otherwise> 
                                                    </c:choose>>Save</a>-->
                                                </p>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </c:if>
                    </c:if>
                </c:forEach>
            </c:if>
        </div>
    </body>
</html>
