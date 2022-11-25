<%-- 
    Document   : main
    Created on : Sep 23, 2022, 9:34:15 AM
    Author     : Thien's
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="description" content="Ogani Template">
        <meta name="keywords" content="Ogani, unica, creative, html">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>RECRUITMENT</title>

        <!-- Google Font -->
        <link href="https://fonts.googleapis.com/css2?family=Cairo:wght@200;300;400;600;900&display=swap" rel="stylesheet">

        <!-- Css Styles -->
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
        <link rel="stylesheet" href="css/nice-select.css" type="text/css">
        <link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
        <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
        <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
        <link rel="stylesheet" href="css/style.css" type="text/css">
        <link rel="stylesheet" href="css/notification.css" type="text/css">
        <style>
            /* unvisited link */
            .notify a:link {
                color: red;
            }

            /* visited link */
            .notify a:visited {
                color: green;
            }

            /* mouse over link */
            .notify a:hover {
                color: hotpink;
            }

            /* selected link */
            .notify a:active {
                color: blue;
            }
        </style>
    </head>
    <body>
        <div>
            <!-- Page Preloder -->
            <div id="preloder">
                <div class="loader"></div>
            </div>

            <!-- Humberger End -->

            <!-- Header Section Begin -->
            <header class="header">
                <div class="header__top">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-6 col-md-6">
                                <div class="header__top__left">
                                    <ul>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6">
                                <div class="header__top__right">
                                    <div class="header__top__right__auth">
                                        <c:choose>
                                            <c:when test="${not empty info && role == 'Admin'}">
                                                <ul class="login-ava">
                                                    Hi, <i>${info.name}</i>
                                                    <li style="list-style-type:none">
                                                        <img src="${info.picture}" alt="Not Found" class="avatar" onerror="this.src='<c:url value="/cvs/default.jpg"/>';"/> 
                                                        <ul>
                                                            <li style="list-style-type:none">
                                                                <a class="link" href="<c:url value="/user?op=info"/>">Info</a>
                                                            </li>
                                                            <li style="list-style-type:none">
                                                                <a class="link" href="<c:url value="/login?op=logout"/>">Logout</a>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </c:when>
                                            <c:when test="${not empty info && role == 'Member'}">
                                                <ul class="login-ava">
                                                    Hi, <i>${info.name}</i>
                                                    <li style="list-style-type:none">
                                                        <img src="${info.picture}" alt="Not Found" class="avatar" onerror="this.src='<c:url value="/cvs/default.jpg"/>';"/> 
                                                        <ul>
                                                            <li style="list-style-type:none">
                                                                <a class="link" href="<c:url value="/user?op=info"/>" style="text-align: center">Info</a>
                                                            </li>
                                                            <li style="list-style-type:none">
                                                                <a class="link" href="<c:url value="/login?op=logout"/>" style="text-align: center">Logout</a>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </c:when>
                                            <c:when test="${not empty info && role == 'HR Staff'}">
                                                <ul class="login-ava">
                                                    Hi, <i>${info.name}</i>
                                                    <li style="list-style-type:none">
                                                        <img src="${info.picture}" alt="Not Found" class="avatar" onerror="this.src='<c:url value="/cvs/default.jpg"/>';"/> 
                                                        <ul><li style="list-style-type:none">
                                                                <a class="link" href="<c:url value="/user?op=info"/>">Info</a>
                                                            </li>
                                                            <li style="list-style-type:none">
                                                                <a class="link" href="<c:url value="/interview?op=set_schedule"/>">Set Interview Schedule</a>
                                                            </li>
                                                            <li style="list-style-type:none">
                                                                <a class="link" href="<c:url value="/login?op=logout"/>">Logout</a>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </c:when>
                                            <c:when test="${not empty info && role == 'Interviewer'}">
                                                <ul class="login-ava">
                                                    Hi, <i>${info.name}</i>
                                                    <li style="list-style-type:none">
                                                        <img src="${info.picture}" alt="Not Found" class="avatar" onerror="this.src='<c:url value="/cvs/default.jpg"/>';"/> 
                                                        <ul>
                                                            <li style="list-style-type:none">
                                                                <a class="link" href="<c:url value="/user?op=info"/>">Info</a>
                                                            </li>
                                                            <li style="list-style-type:none">
                                                                <a class="link" href="<c:url value="/interview?op=interview_schedule&email=${info.email}"/>">Interview Schedule</a>

                                                            </li>
                                                            <li style="list-style-type:none">
                                                                <a class="link" href="<c:url value="/login?op=logout"/>">Logout</a>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </c:when>
                                            <c:when test="${not empty info && role == 'Candidate'}">
                                                <ul class="login-ava">
                                                    Hi, <i>${info.name}</i>
                                                    <li style="list-style-type:none">
                                                        <img src="${info.picture}" alt="Not Found" class="avatar" onerror="this.src='<c:url value="/cvs/default.jpg"/>';"/> 
                                                        <ul>
                                                            <li style="list-style-type:none">
                                                                <a class="link" href="<c:url value="/user?op=info"/>">Info</a>
                                                            </li>
                                                            <li style="list-style-type:none">
                                                                <a class="link" href="<c:url value="/interview?op=interview_process&email=${info.email}"/>">Interview process</a>

                                                            </li>
                                                            <li style="list-style-type:none">
                                                                <a class="link" href="<c:url value="/login?op=logout"/>">Logout</a>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="popup" onclick="popUp()"><i class="fa fa-user"></i> 
                                                    <div class="popuptext" id="myPopup">
                                                        <div style="width: 100%;">
                                                            <h4>Join Us</h4>
                                                            <br/>
                                                            <div class="google-btn" style=" margin: auto">
                                                                <a href="https://accounts.google.com/o/oauth2/auth?scope=email  profile&redirect_uri=http://localhost:8084/recruitment-system/login?op=login&response_type=code&client_id=779040387699-c58vkqmlf6cmvtv3som469pl5k78lgar.apps.googleusercontent.com&approval_prompt=force">
                                                                    <div class="google-icon-wrapper">
                                                                        <img class="google-icon"
                                                                             src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg" />
                                                                    </div>
                                                                    <p class="btn-text"><b>Sign in with google</b></p>
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container">
                    <div class="row">
                        <div class="col-lg-3">
                            <div class="header__logo">
                                <a href="<c:url value='/home?op=index'/>"><img src="img/banner/logo-recruitment2.png" alt=""></a>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <nav class="header__menu">
                                <ul>
                                    <li class="${empty list?'active':'link'}"><a href="<c:url value='/home?op=index'/>">Home</a></li>
                                    <!--Làm Đẹp cái Link-->
                                    <c:choose>
                                        <c:when test="${not empty info}">
                                            <li class="${not empty list?'active':'link'}"><a  href="<c:url value="/job?op=list&email=${info.email}"/>">Jobs</a></li>
                                            </c:when>
                                            <c:otherwise>
                                            <li class="${not empty list?'active':'link'}"><a  href="<c:url value="/job?op=list"/>">Jobs</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                    <!--Làm Đẹp cái Link-->
                                    <c:if test="${not empty info}" >
                                        <c:if test="${ role == 'Interviewer' || role == 'HR Staff'|| role == 'Admin'}" >
                                            <li ><a href="<c:url value="/exam?op=QuestionBank"/>"> Question Bank </a>
<!--                                                <ul class="header__menu__dropdown">
                                                    <li><a href="<c:url value="/exam?op=QuestionBank"/>">Question Bank</a></li>-->
<!--                                                    <li><a href="<c:url value="/exam?op=confirmExam&canId=C001"/>">Test Exam</a></li>-->
                                                <!--</ul>-->
                                            </li>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${not empty info && role == 'HR Staff' || role == 'Admin'}">
                                        <li ><a href="<c:url value="/apply?op=listAll"/>"> Application </a>
                                            <ul class="header__menu__dropdown">
                                                <li><a class="link" href="<c:url value="/apply?op=listAll"/>">List All Applications</a></li>
                                                <li><a class="link" href="<c:url value="/apply?op=list0"/>">Latest Applications</a></li>
                                                <li><a class="link" href="<c:url value="/apply?op=listInprocess"/>">In-Progress Applications</a></li>
                                                <li><a class="link" href="<c:url value="/apply?op=list4"/>">Recruit Candidates</a></li>
                                            </ul>
                                        </li>
                                    </c:if>
                                </ul>
                            </nav>
                        </div>
                        <c:if test="${not empty info}">

                            <div class="col-lg-3">
                                <div class="header__cart">
                                    <ul>
                                        <li>
                                            <a id="notification_bell" 
                                               href="<c:url value="/user?op=listNotification"/> "
                                               >
                                                <i class="fa fa-bell"></i>
                                            </a>
                                            <c:if test="${count != 0}">
                                                <span id="notification_count">${count}</span>
                                            </c:if>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </c:if>
                    </div>
                    <div class="humberger__open">
                        <i class="fa fa-bars"></i>
                    </div>
                </div>
            </header>
            <!-- Header Section End -->

            <!-- Hero Section Begin -->
            <section class="hero">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-3">
                            <li class="dropdown dropdown-1">
                                <div>
                                    <i class="fa fa-bars"></i>
                                    <span>All departments</span>
                                    <span class="arrow_carrot-down"></span>
                                </div>
                                <ul class="dropdown_menu dropdown_menu-1">
                                    <c:forEach var="major" items="${listMajor}" varStatus="loop">
                                        <a href="<c:url value="/job?major_id=${major.major_id}&level_id=0&salary=0&op=filter_job"/>" style="text-decoration: none">
                                            <li class="dropdown_item-${loop.count}">
                                                ${major.major_name}
                                            </li>
                                        </a>
                                    </c:forEach>
                                </ul>
                            </li>
                        </div>
                        <div class="col-lg-9">
                            <div class="hero__search">
                                <div class="hero__search__form">
                                    <form action="<c:url value="/job"/>">
                                        <input type="text" name="search" placeholder="What do you need?">
                                        <button type="submit" class="site-btn" name="op" value="search">SEARCH</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <div class="container-fluid text-center row ">
            <div class="col" style="min-height: 500px">
                <jsp:include page="/WEB-INF/view/${controller}/${action}.jsp"/>
            </div>
        </div>
        <div>
            <!-- Footer Section Begin -->
            <footer class="footer spad">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-3 col-md-6 col-sm-6">
                            <div class="footer__about">
                                <div class="footer__about__logo">
                                    <a href="./index.html"><img src="img/logo.png" alt=""></a>
                                </div>
                                <ul>
                                    <li>Address: 60-49 Road 11378 New York</li>
                                    <li>Phone: +65 11.188.888</li>
                                    <li>Email: hello@colorlib.com</li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-6 col-sm-6 offset-lg-1">
                            <div class="footer__widget">
                                <h6>Useful Links</h6>
                                <ul>
                                    <li><a href="#">About Us</a></li>
                                    <li><a href="#">About Our Shop</a></li>
                                    <li><a href="#">Secure Shopping</a></li>
                                    <li><a href="#">Delivery infomation</a></li>
                                    <li><a href="#">Privacy Policy</a></li>
                                    <li><a href="#">Our Sitemap</a></li>
                                </ul>
                                <ul>
                                    <li><a href="#">Who We Are</a></li>
                                    <li><a href="#">Our Services</a></li>
                                    <li><a href="#">Projects</a></li>
                                    <li><a href="<c:url value="/home?op=viewFeedback"/>">Feedback</a></li>
                                    <li><a href="#">Innovation</a></li>
                                    <li><a href="#">Testimonials</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-12">
                            <div class="footer__widget">
                                <h6>Join Our Newsletter Now</h6>
                                <p>Get E-mail updates about our latest shop and special offers.</p>
                                <form action="#">
                                    <input type="text" placeholder="Enter your mail">
                                    <button type="submit" class="site-btn">Subscribe</button>
                                </form>
                                <div class="footer__widget__social">
                                    <a href="#"><i class="fa fa-facebook"></i></a>
                                    <a href="#"><i class="fa fa-instagram"></i></a>
                                    <a href="#"><i class="fa fa-twitter"></i></a>
                                    <a href="#"><i class="fa fa-pinterest"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="footer__copyright">
                                <div class="footer__copyright__text">
                                    <p>
                                        <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                        Copyright &copy;
                                        <script>document.write(new Date().getFullYear());</script>
                                    </p>
                                </div>
                                <div class="footer__copyright__payment"><img src="img/payment-item.png" alt="">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </footer>
            <!-- Footer Section End -->

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
        <script src="js/notification.js"></script>
    </body>
</html>
