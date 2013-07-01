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
        <div class="row centered-row">
            <div class="span12 well">
               <span class="error"> Status: ${statusCode} <br/>
                Url: ${requestUri} <br/>
                Message: ${exceptionMessage} </span><br/>
                <c:if test="${not empty stackTrace}">
                    <span class="error">Stack trace: </span><br/>
                    <code>
                        <c:forEach items="${stackTrace}" var="line">
                            ${line} <br/>
                        </c:forEach>
                    </code>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>