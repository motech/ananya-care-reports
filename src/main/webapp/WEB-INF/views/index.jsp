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

    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap-multiselect.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap-datepicker.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap-timepicker.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/jquery-ui-1.10.3.custom.min.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/jquery-jvectormap-1.2.2.css" />
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
    <script src="resources/js/localization.js"></script>
    <script src="resources/js/app.js" type="text/javascript"></script>
    <script src="resources/js/services.js" type="text/javascript"></script>
    <script src="resources/js/filters.js" type="text/javascript"></script>
    <script src="resources/js/controllers/nav.js" type="text/javascript"></script>
    <script src="resources/js/controllers/languages.js" type="text/javascript"></script>
    <script src="resources/js/controllers/categories.js" type="text/javascript"></script>
    <script src="resources/js/controllers/dashboards.js" type="text/javascript"></script>
    <script src="resources/js/controllers/forms.js" type="text/javascript"></script>
    <script src="resources/js/controllers/indicators.js" type="text/javascript"></script>
    <script src="resources/js/controllers/roles.js" type="text/javascript"></script>
    <script src="resources/js/controllers/users.js" type="text/javascript"></script>
    <script src="resources/js/controllers/reports.js" type="text/javascript"></script>
    <script src="resources/js/controllers/computed-fields.js" type="text/javascript"></script>
    <script src="resources/js/directives.js" type="text/javascript"></script>
    <script src="resources/lib/flotr2.min.js"></script>
    <script src="resources/lib/jVectorMap/jquery-jvectormap-1.2.2.min.js"></script>
    <script src="resources/lib/jVectorMap/maps/bihar.js"></script>
    <!--[if lt IE 9]>
        <script type="text/javascript" src="resources/lib/flashcanvas.js"></script>
    <![endif]-->
</head>
<body>
    <div class="navbar" bs-navbar ng-controller="navController">
         <div class="navbar-inner">
             <a class="brand" href="https://www.commcarehq.org/"><img src="resources/images/commcare-logo.png" alt="CommCare HQ Logo" /></a>
             <ul class="nav">
                 <li data-match-route="/"><a href="#">{{msg('menu.dashboards')}}</a></li>
                 <!-- TODO: Add `Manage reports` menu option when this feature is implemented. -->
                 <li class="dropdown">
                     <a href="#" class="dropdown-toggle" data-toggle="dropdown">{{msg('menu.indicators')}}<b class="caret"></b></a>
                     <ul class="dropdown-menu">
                        <li class="dropdown-submenu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">{{msg('menu.calculator')}}</a>
                                <ul class="dropdown-menu">
                                    <li><a href="" ng-click="launchFrequencyDialog()"><i class="icon-time"></i> {{msg('menu.calculator.frequency')}}</a></li>
                                </ul>
                        </li>
                        <li class="dropdown-submenu">
                             <a href="#" class="dropdown-toggle" data-toggle="dropdown">{{msg('menu.categories')}}</a>
                             <ul class="dropdown-menu">
                                <li><a href="#/categories"><i class="icon-list"></i> {{msg('menu.categories.list')}}</a></li>
                                <sec:authorize access="hasRole('CAN_CREATE_CATEGORIES')">
                                    <li><a href="#/categories/new"><i class="icon-plus-sign"></i> {{msg('menu.categories.add')}}</a></li>
                                </sec:authorize>
                             </ul>
                        </li>
                         <li class="divider"></li>
                         <li><a href="#/indicators"><i class="icon-list"></i> {{msg('indicators.list.header')}}</a></li>
                         <li><a href="#/indicator/upload-xml"><i class="icon-upload"></i> {{msg('menu.indicators.uploadXml')}}</a></li>
                     </ul>
                 </li>
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">{{msg('menu.admin')}} <b class="caret"></b></a>
                  <ul class="dropdown-menu">
                      <li><a href="#/forms"><i class="icon-briefcase"></i> {{msg('menu.forms')}}</a></li>
                      <li class="dropdown-submenu">
                          <a href="#" class="dropdown-toggle" data-toggle="dropdown">{{msg('menu.languages')}}</a>
                          <ul class="dropdown-menu">
                              <li class="dropdown-submenu">
                                  <a href=""><i class="icon-globe"></i> {{msg('menu.languages.select')}}</a>
                                  <ul class="dropdown-menu">
                                      <li ng-repeat="item in listLanguages">
                                          <a href="#" ng-click="selectLanguage(item)">
                                            <i class="icon-arrow-right" ng-show="defaultLanguage.code == item.code"></i>
                                            {{item.name}}
                                          </a>
                                      </li>
                                  </ul>
                              </li>
                              <li><a href="#/messages"><i class="icon-list"></i> {{msg('menu.languages.list')}}</a></li>
                          </ul>
                      </li>
                  </ul>
                </li>

             </ul>
             <div class="pull-right">
                 <a href="logout" class="btn btn-info">{{msg('menu.logout')}}</a>
             </div>
         </div>
     </div>
    <div id="dashboard-container" class="container-fluid">
        <div class="container-fluid dashboard" ng-view>
        </div>
    </div>
</body>
</html>