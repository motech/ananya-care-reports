<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta charset="UTF-8" />
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap-responsive.min.css" />
    <link rel="stylesheet" type="text/css" href="/resources/css/main.css" />
    <title><spring:message code="login.header" /></title>
</head>
<body>
    <div class="container">
        <div class="row centered-row">
            <div class="span12 well">
               <h1><spring:message code="accessDenied" /></h1>
            </div>
        </div>
    </div>
</body>
</html>