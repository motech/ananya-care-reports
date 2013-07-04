var care = angular.module('care');

care.controller('dashboardController', function($scope) {

});

care.controller('createIndicatorController', function($scope, $http, $modal, $dialog, $filter, $location) {

    $scope.showCreateForm = true;
    $scope.showUpdateForm = false;

    $scope.indicator = {};
    $scope.indicator.complexConditions = [];
    $scope.indicator.values = [];
    $scope.complexConditions = [];
    $scope.categories = [];
    $scope.selectedOwners = {};
    $scope.condition = {};

    $scope.fetchUsers = function() {
        $http.get('api/users/')
                    .success(function(users) {
                        $scope.users = users;
                    }).error(function() {
                        $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadUserList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
                    });
    };
    $scope.fetchUsers();

    $scope.fetchIndicatorTypes = function() {
        $http.get('api/indicator/type')
                            .success(function(indicatorTypes) {
                                $scope.listIndicatorTypes = indicatorTypes;
                            }).error(function() {
                                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadIndicatorTypeList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
                            });
    };
    $scope.fetchIndicatorTypes();

    $scope.fetchCategories = function() {
        $http.get('api/indicator/category')
                            .success(function(categories) {
                                $scope.listCategories = categories;
                            }).error(function() {
                                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadCategoryList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
                            });
    };
    $scope.fetchCategories();

    $scope.listFrequencies = [
        { name: "1 day", value: "1" },
        { name: "2 days", value: "2" },
        { name: "30 days", value: "30" },
        { name: "60 days", value: "60" },
        { name: "180 days", value: "180" }
    ];
    $scope.indicator.frequency = "30";
    $scope.listLevelDistrict = [
        { name: "District 9", value: "1" },
        { name: "District X", value: "2" },
        { name: "District Z", value: "3" }
    ];
    $scope.levelDistrict = "1";
    $scope.listLevelBlock = [
        { name: "Block 1", value: "1" },
        { name: "Block 2", value: "2" },
        { name: "Block 3", value: "3" }
    ];
    $scope.levelBlock = "1";
    $scope.listLevelSubcentre = [
        { name: "Subcentre A", value: "1" },
        { name: "Subcentre B", value: "2" },
        { name: "Subcentre C", value: "3" }
    ];
    $scope.levelSubcentre = "1";
    $scope.listLevelFlwType = [
        { name: "FLW 1", value: "1" },
        { name: "FLW 2", value: "2" },
        { name: "FLW 3", value: "3" }
    ];
    $scope.levelFlwType = "1";
    $scope.listOperators = [
        { name: "ADD", value: "1" },
        { name: "SUBTRACT", value: "2" },
        { name: "MULTIPLY", value: "3" },
        { name: "DIVIDE", value: "4" }
    ];
    $scope.listForms = [
        { name: "Form 1", value: "1" },
        { name: "Form 2", value: "2" }
    ];
    $scope.listCalculateBy = [
        { name: "Formula 1", value: "1" },
        { name: "Formula 2", value: "2" }
    ];
    $scope.listComparisonSymbols = [
        { name: "<=", value: "1" },
        { name: ">=", value: "2" }
    ];

    $scope.listComplexConditions = [
        { id: 0, operator: 1, form: 1, calculateBy: 1, comparisonSymbol: 1, comparisonValue: 10 },
        { id: 1, operator: 1, form: 1, calculateBy: 1, comparisonSymbol: 1, comparisonValue: 11 },
        { id: 2, operator: 1, form: 1, calculateBy: 1, comparisonSymbol: 1, comparisonValue: 12 }
    ];

    $scope.condition = {
        operator: "1",
        form: "1",
        calculateBy: "1",
        comparisonSymbol: "1",
        comparisonValue: 10
    };

    $scope.validateOwners = function() {
        $scope.ownersValid = false;

        for (var key in $scope.selectedOwners) {
            if ($scope.selectedOwners[key] === true) {
                $scope.ownersValid = true;
                return;
            }
        }
    }

    $scope.addCategory = function() {
        if ($scope.selectedCategory != null) {
            var category = $scope.listCategories[$scope.selectedCategory];

            if (category != null) {
                $scope.categories.push(category);

                var index = $scope.listCategories.indexOf(category);
                if (index != -1) {
                    $scope.listCategories.splice(index, 1);
                }
            }
        }
    };

    $scope.removeCategory = function(index) {
        $scope.listCategories.push($scope.categories[index]);

        $scope.categories.splice(index, 1);
    }

    $scope.addComplexCondition = function() {
        if ($scope.selectedComplexCondition != null) {
            var complexCondition = $scope.listComplexConditions[$scope.selectedComplexCondition];

            if (complexCondition != null) {
                $scope.complexConditions.push(complexCondition);

                var index = $scope.listComplexConditions.indexOf(complexCondition);
                if (index != -1) {
                    $scope.listComplexConditions.splice(index, 1);
                }
            }
        }
    };

    $scope.removeComplexCondition = function(index) {
        $scope.listComplexConditions.push($scope.complexConditions[index]);

        $scope.complexConditions.splice(index, 1);
    }

    $scope.getSelectedOwners = function() {
        var selectedOwners = [];

        for (var key in $scope.selectedOwners) {
            if ($scope.selectedOwners[key] === true) {
                selectedOwners.push(parseInt(key));
            }
        }

        return selectedOwners;
    };

    $scope.getSelectedCategories = function() {
        var selectedCategories = [];

        for (var key in $scope.categories) {
            selectedCategories.push($scope.categories[key].id);
        }

        return selectedCategories;
    };

    $scope.submit = function() {
        $scope.indicator.owners = $scope.getSelectedOwners();
        $scope.indicator.categories = $scope.getSelectedCategories();
        $scope.indicator.level = 1;

        $http({
            url: "api/indicator",
            method: "POST",
            data: $scope.indicator,
            headers: { 'Content-Type': 'application/json' }
        }).success(function(categories) {
                $location.path( "/" );
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotCreateIndicator'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };
});

care.controller('formController', function($scope, $http, $routeParams, $location, $dialog) {

    $scope.formId = $routeParams.formId;
    $scope.careForm = {};

    $scope.fetchForm = function() {
        $http.get('api/forms/' + $scope.formId)
            .success(function(form) {
                $scope.careForm = form;
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('forms.form.error.cannotLoadForm'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchTables = function() {
        $http.get('api/forms/tables')
            .success(function(tables) {
                $scope.tables = tables;
            })
            .error(function() {
                $dialog.messageBox("Error", $scope.msg('forms.form.error.cannotLoadTables'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.submitForm = function(form) {

        $http({method: 'PUT', url: 'api/forms' + (form.id !== undefined ? ('/' + form.id) : ''), data: form})
            .success(function(response) {
                $location.path( "/forms" );
            }).error(function(response) {
                $dialog.messageBox("Error", $scope.msg('forms.form.error.submit'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchTables();

    if ($scope.formId !== undefined) {
        $scope.fetchForm();
    };
});

care.controller('formListController', function($scope, $http, $dialog) {

    $scope.fetchForms = function() {
        $http.get('api/forms').success(function(forms) {
            $scope.forms = forms;
        });
    };

    $scope.deleteForm = function(form) {
        var btns = [{result:'yes', label: $scope.msg('yes'), cssClass: 'btn-primary btn'}, {result:'no', label: $scope.msg('no'), cssClass: 'btn-danger btn'}];
        $dialog.messageBox($scope.msg('forms.list.confirmDelete.header'), $scope.msg('forms.list.confirmDelete.message', form.displayName), btns)
            .open()
            .then(function(result) {
                if (result === 'yes') {
                    $http({method: 'DELETE', url: 'api/forms/' + form.id})
                    .success(function(data, status, headers, config) {
                        $scope.fetchForms();
                    }).error(function(response) {
                        $dialog.messageBox("Error", $scope.msg('forms.list.error.delete'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
                    });
                }
            });
    };

    $scope.fetchForms();

});

care.controller('userListController', function($scope, $http, $routeParams, $location, $dialog) {

    $scope.fetchUsers = function() {
        $http.get('api/users').success(function(users) {
            $scope.users = users;
        });
    };

    $scope.deleteUser = function(user) {
        var btns = [{result:'yes', label: 'Yes', cssClass: 'btn-primary btn'}, {result:'no', label: 'No', cssClass: 'btn-danger btn'}];
        $dialog.messageBox("Confirm delete user", "Are you sure you want to delete user: " + user.username + '?', btns)
            .open()
            .then(function(result) {
                if (result === 'yes') {
                    $http({method: 'DELETE', url: 'api/users/' + user.id})
                    .success(function(data, status, headers, config) {
                        $scope.fetchUsers();
                    }).error(function(response) {
                        $dialog.messageBox("Error", "Cannot delete user.", [{label: 'Ok', cssClass: 'btn'}]).open();
                    });
                }
            });
    };

    $scope.fetchUsers();

});

care.controller('roleListController', function($scope, $http, $routeParams, $location, $dialog) {

    $scope.fetchRoles = function() {
        $http.get('api/users/roles').success(function(roles) {
            $scope.roles = roles;
        });
    };

    $scope.deleteRole = function(role) {
        var btns = [{result:'yes', label: $scope.msg('yes'), cssClass: 'btn-primary btn'},
                            {result:'no', label: $scope.msg('no'), cssClass: 'btn-danger btn'}];
                $dialog.messageBox($scope.msg('users.roles.list.confirmDelete.header'),
                                   $scope.msg('users.roles.list.confirmDelete.message') + role.name + '?', btns)
            .open()
            .then(function(result) {
                if (result === 'yes') {
                    $http({method: 'DELETE', url: 'api/users/roles' + role.id})
                    .success(function(data, status, headers, config) {
                        $scope.fetchRoles();
                    }).error(function(response) {
                        $dialog.messageBox($scope.msg('Error'), $scope.msg('users.roles.list.error.delete'),
                                            [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
                    });
                }
            });
    };

    $scope.fetchRoles();

});
