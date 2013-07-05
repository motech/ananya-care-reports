var care = angular.module('care');

care.factory('$errorsDialogService', function($dialog) {
    return function(scope, response) {
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
                  '<button ng-click="close()" class="btn btn-primary" >' + scope.msg('close') + '</button>'+
                  '</div>';

          scope.opts = {
            backdrop: true,
            keyboard: true,
            backdropClick: true,
            template:  t,
            controller: 'errorsDialogController'
          };

        $dialog.dialog(scope.opts).open();
    }
});