var care = angular.module('care');

String.prototype.addSlashes = function()
{
   return this.replace(/[\\"']/g, '\\$&').replace(/\u0000/g, '\\0');
}

care.factory('$errorService', function($dialog) {
    return {
        apiError: function(scope, response) {
            var errors = "<ul>";
            for (i in response) {
                if (response.hasOwnProperty(i)) {
                    var error = response[i];
                    errors += "<li>" + error.message + "</li>";
                }
            }
            errors += "</ul>"

            var t = '<div class="modal-header">'+
                      '<h3>' + scope.msg('users.form.error.cannotSubmitHeader') + '</h3>'+
                      '</div>'+
                      '<div class="modal-body">'+
                      errors +
                      '</div>'+
                      '<div class="modal-footer">'+
                      '<button ng-click="close()" class="btn btn-primary" >' + scope.msg('common.close') + '</button>'+
                      '</div>';

              scope.opts = {
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                template:  t,
                controller: 'errorsDialogController'
              };

            $dialog.dialog(scope.opts).open();
        },
        genericError: function(scope, message, param) {
            $dialog.messageBox("Error", scope.msg(message, param), [{label: scope.msg('ok'), cssClass: 'btn'}]).open();
        },
        stackTraceDialog: function(scope, response) {
            var t = '<div class="modal-header">'+
                    '<h3>' + response.header + '</h3>'+
                    '</div>'+
                    '<div class="modal-body">' +
                    '<pre class="pre-no-bg">' +
                    response.message +
                    '</pre>' +
                    '</div>' +
                    '<div class="modal-footer">'+
                    '<button ng-click="close()" class="btn btn-primary" >' + scope.msg('common.close') + '</button>'+
                    '</div>';

            scope.opts = {
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                template: t,
                controller: 'errorsDialogController',
                dialogClass: 'modal modal-wide'
            };

            $dialog.dialog(scope.opts).open();
        }
    }
});

care.factory('$simplifiedHttpService', function($http, $errorService) {
    return {
        get: function(scope, url, errorMessageCode, successFunction) {
            $http.get(url)
                .success(successFunction)
                .error(function() {
                    $errorService.genericError(scope, errorMessageCode);
                });
        }
    }
});

care.service('$roleService', function($http, $errorService, $rootScope) {
    var permissions = [];

    var fetchUserPermissions = function() {
        $http.get('api/users/logged_in/permissions')
            .success(function(data) {
                 permissions = data;
            }).error(function() {
                 $errorService.genericError($rootScope, 'menu.error.cannotGetRoles');
            });
        };

    if(permissions.length == 0) {
         fetchUserPermissions();
    }

    return {
        hasRole: function(role) {
            return permissions.indexOf(role) != -1;
        }
    }
});
