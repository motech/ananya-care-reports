var care = angular.module('care');

care.controller('errorsDialogController', function($scope, dialog) {
    $scope.close = function(result){
    dialog.close(result);
  };
});

care.controller('userListController', function($scope, $http, $routeParams, $location, $dialog) {
    $scope.title = $scope.msg('users.title');

    $scope.fetchUsers = function() {
        $http.get('api/users').success(function(users) {
            $scope.users = users;
        });
    };

    $scope.deleteUser = function(user) {
        var btns = [{result:'yes', label: 'Yes', cssClass: 'btn-primary btn'}, {result:'no', label: 'No', cssClass: 'btn-danger btn'}];
        $dialog.messageBox($scope.msg('users.list.confirmDelete.header'), $scope.msg('users.list.confirmDelete.message', user.username), btns)
            .open()
            .then(function(result) {
                if (result === 'yes') {
                    $http({method: 'DELETE', url: 'api/users/' + user.id})
                    .success(function(data, status, headers, config) {
                        $scope.fetchUsers();
                    }).error(function(response) {
                        $dialog.messageBox($scope.msg('error'), $scope.msg('users.list.error.deleteUser'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
                    });
                }
            });
    };

    $scope.fetchUsers();

});

care.controller('userController', function($scope, $http, $routeParams, $location, $dialog) {
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
               $scope.areas = areas;
               var assignUserArea = function() {
                    for (var area in areas) {
                        if ($scope.careUser.area != undefined && areas[area].id == $scope.careUser.area.id) {
                            $scope.careUser.area = areas[area];
                            break;
                        }
                    }
               };
               if ($scope.user == undefined) {
               $scope.$watch('careUser', function() {
                    assignUserArea();
               });
               } else {
                   assignUserArea();
               }
           })
           .error(function() {
               $dialog.messageBox($scope.msg('error'), $scope.msg('users.form.error.cannotLoadAreas'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
           });
    }

    $scope.submitUser = function(user) {
        if (user.area != undefined) {
            delete user.area.levelId;
        }
        $http({method: 'PUT', url: 'api/users' + (user.id !== undefined ? ('/' + user.id) : ''), data: user})
            .success(function(response) {
                $location.path( "/users" );
            }).error(function(response) {
                var errors = "<ul>";
                for (i in response) {
                    if (response.hasOwnProperty(i)) {
                        var error = response[i];
                        errors += "<li>" + error.message + "</li>";
                    }
                }
                errors += "</ul>"

                var t = '<div class="modal-header">'+
                          '<h3>' + $scope.msg('users.form.error.cannotSubmitHeader') + '</h3>'+
                          '</div>'+
                          '<div class="modal-body">'+
                          errors +
                          '</div>'+
                          '<div class="modal-footer">'+
                          '<button ng-click="close()" class="btn btn-primary" >' + $scope.msg('close') + '</button>'+
                          '</div>';

                  $scope.opts = {
                    backdrop: true,
                    keyboard: true,
                    backdropClick: true,
                    template:  t,
                    controller: 'errorsDialogController'
                  };

                $dialog.dialog($scope.opts).open();
            });

    };

    $scope.fetchRoles();
    $scope.fetchAreas();

    if ($scope.userId !== undefined) {
        $scope.fetchUser();
    };
});
