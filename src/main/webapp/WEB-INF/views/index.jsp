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
        <c:if test="${error != null}">
            <script>
                springError = "${error}";
            </script>
        </c:if>
        <base href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/" />
        <title ng:bind-template="{{pageTitle}} - CARE Reporting"></title>

        <link rel="shortcut icon" href="resources/images/favicon.png" />
        <link rel="stylesheet" type="text/css" href="resources/css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="resources/css/hqstyle-core.css"/>
        <link rel="stylesheet" type="text/css" href="resources/css/bootstrap-multiselect.css" />
        <link rel="stylesheet" type="text/css" href="resources/css/bootstrap-datepicker.css" />
        <link rel="stylesheet" type="text/css" href="resources/css/bootstrap-timepicker.css" />
        <link rel="stylesheet" type="text/css" href="resources/css/jquery-ui-1.10.3.custom.min.css" />
        <link rel="stylesheet" type="text/css" href="resources/css/jquery-jvectormap-1.2.2.css" />
        <link rel="stylesheet" type="text/css" href="resources/css/daterangepicker.css" />
        <link rel="stylesheet" type="text/css" href="resources/css/main.css" />

        <script src="resources/lib/jquery/jquery-1.10.1.min.js"></script>
        <script src="resources/lib/jquery/jquery.i18n.properties-min-1.0.9.js"></script>
        <script src="resources/lib/jquery/jquery-ui-1.10.3.custom.min.js"></script>
        <script src="resources/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="resources/lib/angular/angular.min.js" type="text/javascript"></script>
        <script src="resources/lib/angular/angular-resource.min.js" type="text/javascript"></script>
        <script src="resources/lib/angular/angular-ui.min.js" type="text/javascript"></script>
        <script src="resources/lib/angular/angular-bootstrap.js" type="text/javascript"></script>
        <script src="resources/lib/angular/angular-strap.min.js" type="text/javascript"></script>
        <script src="resources/lib/angular/ui-bootstrap-tpls-0.4.0.js"></script>
        <script src="resources/lib/angular/angular-strap.min.js"></script>
        <script src="resources/lib/bootstrap-multiselect.js"></script>
        <script src="resources/lib/bootstrap-datepicker.js"></script>
        <script src="resources/lib/bootstrap-timepicker.js"></script>
        <script src="resources/lib/moment.min.js"></script>
        <script src="resources/lib/daterangepicker.js"></script>
        <script src="resources/js/localization.js"></script>
        <script src="resources/js/app.js" type="text/javascript"></script>
        <script src="resources/js/services.js" type="text/javascript"></script>
        <script src="resources/js/filters.js" type="text/javascript"></script>
        <script src="resources/js/controllers/languages.js" type="text/javascript"></script>
        <script src="resources/js/controllers/classifications.js" type="text/javascript"></script>
        <script src="resources/js/controllers/dashboards.js" type="text/javascript"></script>
        <script src="resources/js/controllers/forms.js" type="text/javascript"></script>
        <script src="resources/js/controllers/queries.js" type="text/javascript"></script>
        <script src="resources/js/controllers/indicators.js" type="text/javascript"></script>
        <script src="resources/js/controllers/roles.js" type="text/javascript"></script>
        <script src="resources/js/controllers/users.js" type="text/javascript"></script>
        <script src="resources/js/controllers/reports.js" type="text/javascript"></script>
        <script src="resources/js/controllers/computed-fields.js" type="text/javascript"></script>
        <script src="resources/js/directives.js" type="text/javascript"></script>
        <script src="resources/lib/flotr2.min.js"></script>
        <script src="resources/lib/jVectorMap/jquery-jvectormap-1.2.2.min.js"></script>
        <script src="resources/lib/jVectorMap/maps/bihar-state.js"></script>
        <script src="resources/lib/jVectorMap/maps/bihar-block.js"></script>
        <!--[if lt IE 9]>
            <script type="text/javascript" src="resources/lib/flashcanvas.js"></script>
        <![endif]-->
    </head>
    <body>
        <div class="hq-container-fluid hq-double-col">
            <header>
                <div class="navbar">
                    <div class="navbar-inner">
                        <hgroup class="brand">
                            <h1>
                                <a href="https://www.commcarehq.org/">
                                    <img alt="CommCare HQ Logo" src="resources/images/commcare-logo.png"></img>
                                </a>
                            </h1>
                        </hgroup>
                        <ul class="nav">
                            <li ng-class="{active: getLocation() == '#/'}"><a href="#">{{msg('menu.dashboards')}}</a></li>
                                <li ng-class="{active: getLocation().startsWith('#/indicators')}"><a href="#/indicators">{{msg('menu.indicators')}}</a></li>
                            <sec:authorize access="hasRole('CAN_CREATE_COMPUTED_FIELDS') || hasRole('CAN_CREATE_LANGUAGES') || hasRole('CAN_MANAGE_FORMS')">
                                <li ng-class="{active: getLocation().startsWith('#/admin')}"><a href="#/admin/forms">{{msg('menu.admin')}}</a></li>
                            </sec:authorize>
                        </ul>
                        <div class="btn-toolbar pull-right">
                            <div class="btn-group">
                                <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="#">
                                    <i class="icon-white icon-user"></i> <sec:authentication property="principal.username" /> <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu nav-list dropdown-blue right-align">
                                    <li class="nav-header">{{msg('menu.signedInAs')}}:</li>
                                    <li><sec:authentication property="principal.username" /></li>
                                    <li class="divider"></li>
                                    <li><a href="logout">{{msg('menu.logout')}}</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
            <div class="hq-page-header-container">
                <h1 class="page-header">
                    <ul class="breadcrumb">
                        <li><a href="/"><strong>{{msg('menu.careReporting')}}</strong></a> <span class="divider">&gt;</span></li>
                        <li class="active"><a href="">{{pageTitle}}</a></li>
                    </ul>
                </h1>
            </div>
            <div ng-view></div>
        </div>
    </body>
</html>