<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8" />
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap-responsive.min.css" />
    <link rel="stylesheet" type="text/css" href="/resources/css/main.css" />
</head>
<body>
    <div class="container">
        <div class="offset3 span6 well login-container">
            <h1 class="text-center">Welcome to Care reporting!</h1>
            <form class="form-horizontal login-form" action="j_spring_security_check" method="POST">
                <div class="control-group">
                    <label class="control-label" for="login">Login: </label>
                    <div class="controls">
                        <div class="input-append">
                            <input type="text" id="login" name="login" placeholder="Enter your login..." />
                            <span class="add-on"><i class="icon-user"></i></span>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="password">Password: </label>
                    <div class="controls">
                        <div class="input-append">
                            <input type="password" id="password" name="password" placeholder="Enter your password...">
                            <span class="add-on"><i class="icon-lock"></i></span>
                        </div>
                    </div>
                </div>
                <c:if test="${(not empty param.e) and (not empty SPRING_SECURITY_LAST_EXCEPTION)}">
                    <div class="alert text-center">
                        <p><span class="label label-important">Error!</span> Bad login or password. Try test/test.</p>
                    </div>
                </c:if>
                <div class="control-group">
                    <button type="submit" class="btn btn-success btn-large offset2">Login</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>