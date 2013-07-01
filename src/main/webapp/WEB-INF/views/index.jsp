<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html ng-app="care">
<head>
    <meta charset="UTF-8" />
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap-responsive.min.css" />
    <link rel="stylesheet" type="text/css" href="/resources/css/main.css" />

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
    <script src="/resources/lib/angular/angular.min.js" type="text/javascript"></script>
    <script src="/resources/lib/angular/angular-resource.min.js" type="text/javascript"></script>
    <script src="/resources/lib/angular/angular-strap.min.js" type="text/javascript"></script>
    <script src="/resources/js/app.js" type="text/javascript"></script>
    <script src="/resources/js/services.js" type="text/javascript"></script>
    <script src="/resources/js/controllers.js" type="text/javascript"></script>
    <script src="/resources/js/directives.js" type="text/javascript"></script>
</head>
<body>
    <div id="dashboard-container" class="container">
        <div class="navbar navbar-static-top" bs-navbar>
            <div class="navbar-inner">
                <ul class="nav">
                    <li data-match-route="/"><a href="#">Dashboard</a></li>
                    <li data-match-route="/indicator/new"><a href="#/indicator/new">Create indicator</a></li>
                    <li><a href="#">Manage indicators</a></li>
                    <li><a href="#">Manage system users</a></li>
                </ul>
                <div class="pull-right">
                    <span>Welcome, <sec:authentication property="principal.username" />!</span>
                    <a href="/logout" class="btn btn-info">Logout</a>
                </div>
            </div>
        </div>
        <div class="container dashboard" ng-view>
        </div>
    </div>
</body>
</html>