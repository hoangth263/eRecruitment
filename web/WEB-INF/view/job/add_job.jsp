<%-- 
    Document   : add_job
    Created on : Oct 13, 2022, 4:20:11 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            textarea {
                resize: none;
            }
        </style>
        <script>
            function validateForm() {
                var x = document.forms["myForm"]["job_id"].value;
                var y = document.forms["myForm"]["job_name"].value;
                var z = document.forms["myForm"]["job_vacancy"].value;
                var a = document.forms["myForm"]["job_description"].value;
                var b = document.forms["myForm"]["salary"].value;
                if (x == "" || x == null) {
                    alert("ID must be filled out");
                    return false;
                }
                if (y == "" || y == null) {
                    alert("Name must be filled out");
                    return false;
                }
                if (z == "" || z == null) {
                    alert("Vacancy must be filled out");
                    return false;
                }
                if (a == "" || a == null) {
                    alert("Description must be filled out");
                    return false;
                }
                if (b == "" || b == null) {
                    alert("Salary must be filled out");
                    return false;
                }
                if (!/^[a-zA-Z &nbsp]*$/g.test(document.myForm.job_name.value)) {
                    alert("Invalid job name");
                    document.myForm.job_name.focus();
                    return false;
                }
//                if (!/^[a-zA-Z &nbsp\p{Punct}?]*$/g.test(document.myForm.job_description.value)) {
//                    alert("Invalid job description");
//                    document.myForm.job_description.focus();
//                    return false;
//                }
            }
        </script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
        <!--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">-->

    </head>
    <body>
        <div class="container " >
            <div class="input-group " style="justify-content: center; align-items: center;">

                <form action="<c:url value="/job"/>" style="" name="myForm" onsubmit="return validateForm()"  required >
                    <div class="input-group mb-3">
                        <span class="input-group-text" >Job name:</span>
                        <input placeholder="Enter Job Name" class="form-control" type="text" name="job_name" required="true"/>
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Major:</span>
                        <select name="major_id" class="form-control ">
                            <option value="1">Marketing</option>
                            <option value="2">Logistic</option>
                            <option value="3">Data Science and Analytics</option>
                            <option value="4">Information Technology</option>
                            <option value="5">Graphic Design</option>
                            <option value="6">Engineering</option>
                            <option value="7">Risk Manager</option>
                            <option value="8">Economic Finance</option>
                            <option value="9">Business Intelligence and Development</option>
                            <option value="10">Operations</option>
                        </select>
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Job vacancy:</span>
                        <input placeholder="Enter Job Vacancy" class="form-control" type="number" name="job_vacancy" min="1" cols="50" required="true"/>
                    </div>
                    <div class="input-group mb-3" >
                        <span class="input-group-text">Job Description</span>
                        <textarea placeholder="Enter Job Description" class="form-control" type="text" name="job_description" rows="1" cols="50" required="true"></textarea>
                    </div>
                    <div  id = "main">
                        <input type="text" placeholder="Enter Job Skill"  style="width: 680px;" id="option1" required="true" name="job_skill1"/>
                        <input type="hidden" id="countInput" name="count" value="2"/>
                    </div>
                    <input type="button" id="addButton" class="opbutton" value="Add More Job Skill" onclick="add(i)"  style="width: auto"/>
                    <input type="button" id="removeButton"  value="Remove" onclick="remove(1)"  style="width: 12%" disabled="true"/>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Level:</span>
                        <select name="level_id" class="form-control ">
                            <option value="1">Intern</option>
                            <option value="2">Fresher</option>
                            <option value="3">Junior</option>
                            <option value="4">Senior</option>
                            <option value="5">Manager</option>
                            <option value="6">Leader</option>
                        </select><br/>
                    </div>
                    <div class="input-group mb-3">
                        <a href="add_job.jsp"></a>
                        <span class="input-group-text">Salary($):</span>
                        <input placeholder="Enter Job Salary" class="form-control" type="number" name="salary" min="1" required="true"/>
                    </div>
                    <button class="btn btn-primary" type="submit" name="op" value="add_job_handler">ADD</button>
                </form>
            </div>
        </div>
        <script>
            function add(i) {
                var main = document.getElementById("main");
                var row = document.createElement("tr");
                var td1 = document.createElement("td");
                td1.setAttribute("class", "text-center");
                var td2 = document.createElement("td");
                td2.setAttribute("class", "text-center");


                var x = document.createElement("INPUT");
                x.setAttribute("type", "text");
                x.setAttribute("placeholder", "Enter Job Skill");
                x.setAttribute("style", "width: 680px;");
                x.setAttribute("name", "job_skill"+ (i + 1));
                x.setAttribute("id", "option" + (i + 1));
                x.setAttribute("required", true);

                td2.appendChild(x);
                row.appendChild(td1);
                row.appendChild(td2);
                main.appendChild(row);

                var ad = "add(" + (i + 1) + ")";
                var addButton = document.getElementById("addButton");
                if (i === 9) {
                    addButton.disabled = true;
                }
                addButton.setAttribute('onclick', ad);
//                addButton.value = ad;

                var rem = "remove(" + (i + 1) + ")";
                var removeButton = document.getElementById("removeButton");
                if (i === 1) {
                    removeButton.disabled = false;
                }
                removeButton.setAttribute('onclick', rem);
//                removeButton.value = rem;
                document.getElementById("countInput").value = (i + 1);
            }


            function remove(i) {
                document.getElementById("option" + (i)).remove();
                var ad = "add(" + (i - 1) + ")";
                var addButton = document.getElementById("addButton");
                if (i === 10) {
                    addButton.disabled = false;
                }
                addButton.setAttribute('onclick', ad);
//                addButton.value = ad;
                var rem = "remove(" + (i - 1) + ")";
                var removeButton = document.getElementById("removeButton");
                if (i === 2) {
                    removeButton.disabled = true;
                }
                removeButton.setAttribute('onclick', rem);
//                removeButton.value = rem;
                document.getElementById("countInput").value = (i - 1);
            }
        </script> 
    </body>
</html>
