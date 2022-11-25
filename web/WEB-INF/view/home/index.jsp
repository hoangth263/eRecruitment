<%-- 
    Document   : index
    Created on : Sep 23, 2022, 9:35:34 AM
    Author     : Thien's
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!-- Featured Section Begin -->
        <div class="container">
            <div class="row">
                <c:if test="${empty info}">
                    <div class="col-lg-3">
                        <div class="login-content">
                            <h5>
                                Join a dynamic team of 3HTD professionals at
                                one of the world’s leading providers of Audit,
                                Tax and Advisory services.
                            </h5>
                        </div>

                        <div class="row">
                            <div class="col login">
                                <h3>Join Us</h3>
                                <br>
                                <div class="google-btn">
                                    <a href="https://accounts.google.com/o/oauth2/auth?scope=email  profile&redirect_uri=http://localhost:8084/recruitment-system/login?op=login&response_type=code&client_id=779040387699-c58vkqmlf6cmvtv3som469pl5k78lgar.apps.googleusercontent.com&approval_prompt=force">
                                        <div class="google-icon-wrapper">
                                            <img class="google-icon"
                                                 src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg" />
                                        </div>
                                        <p class="btn-text"><b>Sign in with google</b></p>
                                    </a>
                                </div>
                                <h5>-----------OR-----------</h5>
                                <p>You are our staff ?</p>
                                <button class="login-staff-btn">
                                    <a href="https://accounts.google.com/o/oauth2/auth?scope=email  profile&redirect_uri=http://localhost:8084/recruitment-system/login?op=login&response_type=code&client_id=779040387699-c58vkqmlf6cmvtv3som469pl5k78lgar.apps.googleusercontent.com&approval_prompt=force">
                                        STAFF
                                    </a>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div style="border-radius: 10px;" class="hero__item set-bg col-lg-9"
                         data-setbg="img/banner/recruitment-banner-2.jpg">
                    </div>
                </c:if>
                <c:if test="${not empty info}">
                    <div style="border-radius: 10px;" class="hero__item set-bg col-lg-12"
                         data-setbg="img/banner/recruitment-banner-2.jpg">
                    </div>
                </c:if>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="section-title">
                        <h2>Find Your Passion</h2>
                    </div>
                    <ul class="department-list">
                        <li class="department-item">
                            <a href="<c:url value="/job?major_id=1&level_id=0&salary=0&op=filter_job"/>" style="color: blue">
                                <div class="department-item-title">Marketing</div>
                            </a>
                        </li>
                        <li class="department-item">
                            <a href="<c:url value="/job?major_id=2&level_id=0&salary=0&op=filter_job"/>" style="color: blue">
                                <div class="department-item-title">Logistic</div>
                            </a>
                        </li>
                        <li class="department-item">
                            <a href="<c:url value="/job?major_id=3&level_id=0&salary=0&op=filter_job"/>" style="color: blue">
                                <div class="department-item-title">Data Science and Analytics</div>
                            </a>
                        </li>
                        <li class="department-item">
                            <a href="<c:url value="/job?major_id=4&level_id=0&salary=0&op=filter_job"/>" style="color: blue">
                                <div class="department-item-title">Information Technology</div>
                            </a>
                        </li>
                        <li class="department-item">
                            <a href="<c:url value="/job?major_id=5&level_id=0&salary=0&op=filter_job"/>" style="color: blue">
                                <div class="department-item-title">Graphic Design</div>
                            </a>
                        </li>
                        <li class="department-item">
                            <a href="<c:url value="/job?major_id=6&level_id=0&salary=0&op=filter_job"/>" style="color: blue">
                                <div class="department-item-title">Engineering</div>
                            </a>
                        </li>
                        <li class="department-item">
                            <a href="<c:url value="/job?major_id=7&level_id=0&salary=0&op=filter_job"/>" style="color: blue">
                                <div class="department-item-title">Risk Manager</div>
                            </a>
                        </li>
                        <li class="department-item">
                            <a href="<c:url value="/job?major_id=8&level_id=0&salary=0&op=filter_job"/>" style="color: blue">
                                <div class="department-item-title">Economic Finance</div>
                            </a>
                        </li>
                        <li class="department-item">
                            <a href="<c:url value="/job?major_id=9&level_id=0&salary=0&op=filter_job"/>" style="color: blue">
                                <div class="department-item-title">Business Intelligence and Development</div>
                            </a>                        
                        </li>
                        <li class="department-item">
                            <a href="<c:url value="/job?major_id=10&level_id=0&salary=0&op=filter_job"/>" style="color: blue">
                                <div class="department-item-title">Operations</div>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>


        <!-- Js Plugins -->
        <script src="js/jquery-3.3.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.nice-select.min.js"></script>
        <script src="js/jquery-ui.min.js"></script>
        <script src="js/jquery.slicknav.js"></script>
        <script src="js/mixitup.min.js"></script>
        <script src="js/owl.carousel.min.js"></script>
        <script src="js/main.js"></script>
        <script src="js/popUp.js"></script>


    </body>
</html>
