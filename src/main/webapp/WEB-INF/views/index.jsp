<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html ng-app="care">
<head>
    <meta charset="UTF-8" />
    <c:set var="req" value="${pageContext.request}" />
    <c:set var="uri" value="${req.requestURI}" />
    <c:set var="url">${req.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/" />
    <title ng:bind-template="{{pageTitle}} - Care Reporting"></title>

    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap-multiselect.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/main.css" />

    <script src="resources/lib/jquery/jquery-1.10.1.min.js"></script>
    <script src="resources/lib/jquery/jquery.i18n.properties-min-1.0.9.js"></script>
    <script src="resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="resources/lib/angular/angular.min.js" type="text/javascript"></script>
    <script src="resources/lib/angular/angular-resource.min.js" type="text/javascript"></script>
    <script src="resources/lib/angular/angular-ui.min.js" type="text/javascript"></script>
    <script src="resources/lib/angular/angular-bootstrap.js" type="text/javascript"></script>
    <script src="resources/lib/angular/angular-strap.min.js" type="text/javascript"></script>
    <script src="resources/lib/angular/ui-bootstrap-tpls-0.4.0.js"></script>
    <script src="resources/lib/angular/angular-strap.min.js"></script>
    <script src="resources/lib/bootstrap-multiselect.js"></script>
    <script src="resources/js/localization.js"></script>
    <script src="resources/js/app.js" type="text/javascript"></script>
    <script src="resources/js/services.js" type="text/javascript"></script>
    <script src="resources/js/controllers/categories.js" type="text/javascript"></script>
    <script src="resources/js/controllers/dashboards.js" type="text/javascript"></script>
    <script src="resources/js/controllers/forms.js" type="text/javascript"></script>
    <script src="resources/js/controllers/indicators.js" type="text/javascript"></script>
    <script src="resources/js/controllers/roles.js" type="text/javascript"></script>
    <script src="resources/js/controllers/users.js" type="text/javascript"></script>
    <script src="resources/js/directives.js" type="text/javascript"></script>
</head>
<body>

    <div id="dashboard-container" class="container">
        <div class="navbar" bs-navbar>
            <div class="navbar-inner">
                <ul class="nav">
                    <li data-match-route="/"><a href="#">{{msg('menu.dashboard')}}</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">{{msg('menu.manageIndicators')}}<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="#/indicator/new"><i class="icon-plus-sign"></i> {{msg('menu.manageIndicators.addNewIndicator')}}</a></li>
                        </ul>
                    </li>
                    <sec:authorize access="hasRole('CAN_MANAGE_SYSTEM_USERS')">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">{{msg('menu.manageSystemUsers')}}<b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#/users"><i class="icon-list"></i> {{msg('menu.manageSystemUsers.usersList')}}</a></li>
								<li><a href="#/users/roles"><i class="icon-list"></i> {{msg('menu.manageSystemUsers.rolesList')}}</a></li>
                                <li><a href="#/users/new"><i class="icon-plus-sign"></i> {{msg('menu.manageSystemUsers.addNewUser')}}</a></li>
                            </ul>
                        </li>
                    </sec:authorize>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">{{msg('menu.manageForms')}} <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="#/forms"><i class="icon-list"></i> {{msg('menu.manageForms.formList')}}</a></li>
                            <li><a href="#/forms/new"><i class="icon-plus-sign"></i> {{msg('menu.manageForms.addNewForm')}}</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">{{msg('menu.manageCategories')}}  <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="#/categories"><i class="icon-list"></i> {{msg('menu.manageCategories.list')}}</a></li>
                            <li><a href="#/categories/add"><i class="icon-plus-sign"></i> {{msg('menu.manageCategories.add')}}</a></li>
                        </ul>
                    </li>
                </ul>
                <div class="pull-right">
                    <span>{{msg('menu.welcome', '<sec:authentication property="principal.firstName" /> <sec:authentication property="principal.lastName" />')}}</span>
                    <a href="logout" class="btn btn-info">{{msg('menu.logout')}}</a>
                </div>
            </div>
        </div>
        <div class="container dashboard" ng-view>
        </div>
    </div>
</body>
</html>