var care = angular.module('care');

care.controller('roleListController', function($scope, $http, $routeParams, $location, $dialog, $errorService) {
    $scope.title = $scope.msg('roles.title');

    $scope.fetchRoles = function() {
        $http.get('api/users/roles').success(function(roles) {
            $scope.roles = roles;
        });
    };

    $scope.deleteRole = function(role) {
        var btns = [{result:'yes', label: $scope.msg('common.yes'), cssClass: 'btn-primary btn'},
                            {result:'no', label: $scope.msg('common.no'), cssClass: 'btn-danger btn'}];
                $dialog.messageBox($scope.msg('roles.list.confirmDelete.header'),
                                   $scope.msg('roles.list.confirmDelete.message') + role.name + '?', btns)
            .open()
            .then(function(result) {
                if (result === 'yes') {
                    $http({method: 'DELETE', url: 'api/users/roles/' + role.id})
                    .success(function(data, status, headers, config) {
                        $scope.fetchRoles();
                    }).error(function(response) {
                        $errorService.apiError($scope, response);
                    });
                }
            });
    };

    $scope.fetchRoles();

});

care.controller('roleController', function($scope, $http, $routeParams, $location, $dialog, $errorService) {
    $scope.title = $scope.msg('roles.title');

    var isEdit = ($routeParams.roleId !== undefined);
    $scope.selectedPermissions = [];
    $scope.permissionList = [];

    $scope.fetchPermissions = function() {
        $http.get('api/users/permissions')
            .success(function(permissionList) {
                permissionList.sort(function(a, b) { return a.displayName > b.displayName; });
                $scope.permissionList = permissionList;

                if (isEdit) {
                    for (var i = 0; i < $scope.role.permissions.length; i++) {
                        var id = $scope.role.permissions[i].id;
                        $scope.selectedPermissions[id] = true;
                    }
                } else {
                     for (var i = 0; i < $scope.permissionList.length; i++) {
                         $scope.selectedPermissions[i] = false;
                     }
                 }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('roles.list.error.cannotLoadPermissionList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    }

    if (isEdit) {
        $scope.fetchRole = function() {
            var roleId = $routeParams.roleId;

            $http.get('api/users/roles/' + roleId)
                .success(function(role) {
                    $scope.role = role;
                    $scope.role.permissions.sort(function(a, b) { return a.displayName > b.displayName; });
                    $scope.fetchPermissions();
                }).error(function() {
                    $dialog.messageBox("Error", $scope.msg('roles.list.error.cannotLoadRole'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
                });
        };
        $scope.fetchRole();
        $scope.formHeaderMsg = $scope.msg('roles.form.header.edit');
    } else {
        $scope.role = {};
        $scope.role.permissions = [];
        $scope.fetchPermissions();
        $scope.formHeaderMsg = $scope.msg('roles.form.header.add');
    }

    $scope.getSelectedPermissions = function() {
        $scope.role.permissions = [];

        for (var i = 0; i < $scope.permissionList.length; i++) {
            var id = $scope.permissionList[i].id;
            if ($scope.selectedPermissions[id] === true) {
                $scope.role.permissions.push($scope.permissionList[i]);
            }
        }
    };

    $scope.selectAllPermissions = function() {
        if($scope.isAllSelected() == true) {
            var check = false;
        } else {
            var check = true;
        }

        for (var i = 0; i < $scope.permissionList.length; i++) {
            var id = $scope.permissionList[i].id;
            $scope.selectedPermissions[id] = check;
        }
    };

    $scope.isAllSelected = function() {
        var sum = 0;
        for (var i = 0; i < $scope.selectedPermissions.length; i++) {
             if($scope.selectedPermissions[i] === true)
                sum++;
         }
        return sum != 0 && $scope.permissionList.length == sum;
    };

    $scope.submitRole = function() {
        var requestUrl = (isEdit) ? "api/users/roles/" + $scope.role.id : "api/users/roles";
        var requestMethod = (isEdit) ? "PUT" : "POST";
        var errorMsg = (isEdit) ? "roles.list.error.cannotUpdateRole" : "roles.list.error.cannotCreateRole";

        $scope.getSelectedPermissions();
        $http({
            url: requestUrl,
            method: requestMethod,
            data: $scope.role,
            headers: { 'Content-Type': 'application/json' }
        }).success(function(response) {
                $location.path("/users/roles");
        }).error(function(data, status, headers, config) {
                $dialog.messageBox($scope.msg('common.error'), data, [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
        });
    };
});
