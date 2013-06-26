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
    <div id="loginContainer" class="container centered-container">
        <div class="row centered-row">
            <div class="span5 offset3 well">
                <form class="form-horizontal" action="j_spring_security_check" method="POST">
                    <label for="login">Login:</label>
                    <input class="input-block-level" type="text" id="login" name="login" />
                    <label for="password">Password:</label>
                    <input class="input-block-level" type="password" id="password" name="password" />
                    <button class="btn btn-primary">Login</button>
                    <c:if test="${(not empty param.e) and (not empty SPRING_SECURITY_LAST_EXCEPTION)}">
                        <div class="alert">
                          <p><span class="label label-important">Error!</span> Bad login or password. <br />Try: test/test</p>
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</body>
</html>