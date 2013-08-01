var care = angular.module('care');

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
        genericError: function(scope, message) {
            $dialog.messageBox("Error", scope.msg(message), [{label: scope.msg('ok'), cssClass: 'btn'}]).open();
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