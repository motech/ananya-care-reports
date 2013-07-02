<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html ng-app="care">
<head>
    <meta charset="UTF-8" />
    <c:set var="req" value="${pageContext.request}" />
    <c:set var="uri" value="${req.requestURI}" />
    <c:set var="url">${req.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/" />

    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/main.css" />

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
    <script src="resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="resources/lib/angular/angular.min.js" type="text/javascript"></script>
    <script src="resources/lib/angular/angular-resource.min.js" type="text/javascript"></script>
    <script src="resources/lib/angular/angular-ui.min.js" type="text/javascript"></script>
    <script src="resources/lib/angular/ui-bootstrap-tpls-0.4.0.js"></script>
    <script src="resources/js/app.js" type="text/javascript"></script>
    <script src="resources/js/services.js" type="text/javascript"></script>
    <script src="resources/js/controllers.js" type="text/javascript"></script>
    <script src="resources/js/directives.js" type="text/javascript"></script>
</head>
<body>
    <div id="dashboard-container" class="container">
        <div class="navbar" bs-navbar>
            <div class="navbar-inner">
                <ul class="nav">
                    <li data-match-route="/"><a href="#">Dashboard</a></li>
                    <li data-match-route="/indicator/new"><a href="#/indicator/new">Create indicator</a></li>
                    <li><a href="#">Manage indicators</a></li>
                    <li><a href="#">Manage system users</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Manage forms <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="#/forms"><i class="icon-list"></i> Form list</a></li>
                            <li><a href="#/forms/new"><i class="icon-plus-sign"></i> Add new form</a></li>
                        </ul>
                    </li>
                </ul>
                <div class="pull-right">
                    <span>Welcome, <sec:authentication property="principal.username" />!</span>
                    <a href="logout" class="btn btn-info">Logout</a>
                </div>
            </div>
        </div>
        <div class="container dashboard" ng-view>
        </div>
    </div>
</body>
</html>