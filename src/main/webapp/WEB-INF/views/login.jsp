<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta charset="UTF-8" />
    <c:set var="req" value="${pageContext.request}" />
    <c:set var="uri" value="${req.requestURI}" />
    <c:set var="url">${req.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/" />
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/hqstyle-core.css"/>
    <title><spring:message code="login.title" /> - CARE Reporting</title>
    <script src="resources/lib/jquery/jquery-1.10.1.min.js"></script>
</head>
<body>
<div class="hq-container-fluid hq-centered-content">
    <header>
        <div class="navbar">
            <div class="navbar-inner">
                <hgroup class="brand">
                    <h1><a href="https://www.commcarehq.org/"><img src="resources/images/commcare-logo.png" alt="CommCare HQ Logo" /></a></h1>
                </hgroup>
            </div>
        </div>
    </header>

    <div class="container">
        <div class="row">
            <div class="span8">
                <br/>
                <div class="page-header">
                </div>
                <form class="form-horizontal login-form" action="j_spring_security_check" method="POST">
                    <c:if test="${(not empty param.e) and (not empty SPRING_SECURITY_LAST_EXCEPTION)}">
                        <div class="alert text-center">
                            <p><spring:message code="login.error.message" /></p>
                        </div>
                    </c:if>
                    <fieldset>
                        <div class="control-group">
                            <label class="control-label" for="login"><spring:message code="login.email" /></label>
                            <div class="controls">
                                <div class="input-append">
                                    <input type="text" id="login" name="login" placeholder="<spring:message code="login.login.placeholder" />" />
                                </div>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="password"><spring:message code="login.password.label" /></label>
                            <div class="controls">
                                <div class="input-append">
                                    <input type="password" id="password" name="password" placeholder="<spring:message code="login.password.placeholder" />">
                                </div>
                            <p class="help-block">
                                <a href="https://www.commcarehq.org/accounts/password_reset_email/"><spring:message code="login.password.forgot" /></a>
                            </p>
                            </div>

                        </div>
                    </fieldset>
                    <div class="form-actions"><button tabindex="3" type="submit" class="btn btn-primary btn-large">
                    <spring:message code="login.submit" />
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
