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
    <div id="login-container" class="container-fluid login-container">
        <div class="row-fluid login-container">
            <div class="span4 well offset4">
                <form class="form-horizontal" action="j_spring_security_check" method="POST">
                    <div class="control-group">
                        <label class="control-label" for="login">Login:</label>
                        <div class="controls">
                            <input type="text" name="login" />
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="password">Password:</label>
                        <div class="controls">
                            <input type="password" name="password" />
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <button class="btn btn-primary btn-large">Login</button>
                        </div>
                    </div>
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