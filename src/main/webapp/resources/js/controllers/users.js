var care = angular.module('care');

care.controller('errorsDialogController', function($scope, dialog) {
    $scope.close = function(result){
    dialog.close(result);
  };
});

care.controller('userListController', function($scope, $http, $routeParams, $location, $dialog, $errorService) {
    $scope.title = $scope.msg('users.title');

    $scope.fetchUsers = function() {
        $http.get('api/users').success(function(users) {
            $scope.users = users;
        });
    };

    $scope.deleteUser = function(user) {
        var btns = [{result:'yes', label: $scope.msg('yes'), cssClass: 'btn-primary btn'}, {result:'no', label: $scope.msg('no'), cssClass: 'btn-danger btn'}];
        $dialog.messageBox($scope.msg('users.list.confirmDelete.header'), $scope.msg('users.list.confirmDelete.message', user.username), btns)
            .open()
            .then(function(result) {
                if (result === 'yes') {
                    $http({method: 'DELETE', url: 'api/users/' + user.id})
                    .success(function(data, status, headers, config) {
                        $scope.fetchUsers();
                    }).error(function(response) {
                        $errorService.apiError($scope, response);
                    });
                }
            });
    };

    $scope.fetchUsers();

});

care.controller('userController', function($scope, $http, $routeParams, $location, $dialog, $errorService) {
    $scope.title = $scope.msg('users.title');

    $scope.userId = $routeParams.userId;

    $scope.isEditing = function() {
        return $scope.userId != undefined;
    }

    $scope.careUser = {};

    $scope.assignUser = function(user) {
        roles:
        for (var role in user.roles) {
            for (var globalRole in $scope.roles) {
                if ($scope.roles[globalRole].id == user.roles[role].id) {
                    user.roles[role] = $scope.roles[globalRole];
                    continue roles;
                }
            }
        }
        $scope.$watch('careUser', function() {
            $("#roles").multiselect('refresh');
        });
        $scope.careUser = user;
    };

    $scope.fetchUser = function() {
        $http.get('api/users/' + $scope.userId)
            .success(function(user) {
                if ($scope.roles == undefined) {
                    $scope.$watch('roles', function() {
                        $scope.assignUser(user);
                    });
                } else {
                    $scope.assignUser(user);
                }
            }).error(function() {
                $dialog.messageBox($scope.msg('error'), $scope.msg('users.form.error.cannotFetchUser'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchRoles = function() {
        $http.get('api/users/roles')
            .success(function(roles) {
                $scope.$watch('roles', function() {
                    $("#roles").multiselect();
                });
                $scope.roles = roles;
            })
            .error(function() {
                $dialog.messageBox($scope.msg('error'), $scope.msg('users.form.error.cannotLoadRoles'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchAreas = function() {
       $http.get('api/users/areas')
           .success(function(areas) {
                areas.sort(function(a, b) {
                    return a.name.localeCompare(b.name);
                });
                var arr = Array(),
                    pushAllChildrenOf = function(arr, area) {
                        for (var index=0; index<areas.length; index+=1) {
                            if (areas[index].parentAreaId == area.id) {
                                arr.push(areas[index]);
                                pushAllChildrenOf(arr, areas[index]);
                            }
                        }
                    };

                for (var index=0; index<areas.length; index+=1) {
                    if (areas[index].levelHierarchyDepth == 0) {
                        arr.push(areas[index]);
                        pushAllChildrenOf(arr, areas[index]);
                    }
                }
                $scope.areas = arr;
           })
           .error(function() {
               $dialog.messageBox($scope.msg('error'), $scope.msg('users.form.error.cannotLoadAreas'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
           });
    }

    $scope.submitUser = function(user) {
        if (user.area != undefined) {
            delete user.area.level;
            delete user.area.parentArea;
        }
        $http({method: 'PUT', url: 'api/users' + (user.id !== undefined ? ('/' + user.id) : ''), data: user})
            .success(function(response) {
                $location.path( "/users" );
            }).error(function(data, status, headers, config) {
                $dialog.messageBox($scope.msg('error'), data, [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });

    };

    $scope.fetchRoles();
    $scope.fetchAreas();

    if ($scope.userId !== undefined) {
        $scope.fetchUser();
        $scope.formHeaderMsg = $scope.msg('users.form.header.edit');
    } else {
        $scope.formHeaderMsg = $scope.msg('users.form.header.add');
    };
});
