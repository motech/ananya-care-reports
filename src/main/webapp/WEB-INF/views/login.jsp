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
    <link rel="stylesheet" type="text/css" href="resources/css/main.css" />
    <title><spring:message code="login.title" /></title>
</head>
<body>
    <div class="container">
        <div class="offset3 span6 well login-container">
            <h1 class="text-center"><spring:message code="login.header" /></h1>
            <form class="form-horizontal login-form" action="j_spring_security_check" method="POST">
                <div class="control-group">
                    <label class="control-label" for="login"><spring:message code="login.login.label" />: </label>
                    <div class="controls">
                        <div class="input-append">
                            <input type="text" id="login" name="login" placeholder="<spring:message code="login.login.placeholder" />" />
                            <span class="add-on"><i class="icon-user"></i></span>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="password"><spring:message code="login.password.label" />: </label>
                    <div class="controls">
                        <div class="input-append">
                            <input type="password" id="password" name="password" placeholder="<spring:message code="login.password.placeholder" />">
                            <span class="add-on"><i class="icon-lock"></i></span>
                        </div>
                    </div>
                </div>
                <c:if test="${(not empty param.e) and (not empty SPRING_SECURITY_LAST_EXCEPTION)}">
                    <div class="alert text-center">
                        <p><span class="label label-important"><spring:message code="login.error.label" /></span><spring:message code="login.error.message" /></p>
                    </div>
                </c:if>
                <div class="control-group">
                    <button type="submit" class="btn btn-success btn-large offset2"><spring:message code="login.submit" /></button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>