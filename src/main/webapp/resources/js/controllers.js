var care = angular.module('care');

Array.prototype.sortByName = function() {
    this.sort(function(a, b) {
        return (a.name > b.name);
    });
}

Array.prototype.sortById = function() {
    this.sort(function(a, b) {
        return (a.id < b.id);
    });
}

care.controller('dashboardController', function($scope) {

});

care.controller('createIndicatorController', function($scope, $http, $modal, $dialog, $filter, $location) {

    $scope.addCategoryDisabled = true;
    $scope.addDimensionDisabled = true;

    $scope.indicator = {};
    $scope.indicator.complexConditions = [];
    $scope.indicator.values = [];
    $scope.complexConditions = [];
    $scope.categories = [];
    $scope.selectedOwners = {};
    $scope.condition = {};
    $scope.ownersValid = false;
    $scope.typeValid = false;
    $scope.categoriesValid = false;
    $scope.conditionsValid = false;

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
                indicatorTypes.sortByName();
                $scope.listIndicatorTypes = indicatorTypes;

                if (Object.keys($scope.listIndicatorTypes).length > 0) {
                    $scope.indicator.indicatorType = $scope.listIndicatorTypes[0].id;
                    $scope.typeValid = true;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadIndicatorTypeList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };
    $scope.fetchIndicatorTypes();

    $scope.fetchCategories = function() {
        $http.get('api/indicator/category')
            .success(function(categories) {
                categories.sortByName();
                $scope.listCategories = categories;

                if (Object.keys($scope.listCategories).length > 0) {
                    $scope.selectedCategory = "0";
                    $scope.addCategoryDisabled = false;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadCategoryList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };
    $scope.fetchCategories();

    $scope.removeUsedConditions = function() {
        var keyCount = Object.keys($scope.listComplexConditions).length;
        for (var i = keyCount - 1; i >= 0; i--) {
            var condition_value = $scope.listComplexConditions[i];
            if (!$scope.listComplexConditions.hasOwnProperty(i)) {
                continue;
            }

            for (var used_condition_key in $scope.complexConditions) {
                var used_condition_value = $scope.complexConditions[used_condition_key];
                if (!$scope.complexConditions.hasOwnProperty(used_condition_key)) {
                    continue;
                }

                if (condition_value.id == used_condition_value.id) {
                    $scope.listComplexConditions.splice(i, 1);
                }
            }
        }
    };

    $scope.fetchComplexConditions = function() {
        $http.get('api/complexcondition')
            .success(function(conditions) {
                conditions.sortById();
                $scope.listComplexConditions = conditions;

                if (Object.keys($scope.listComplexConditions).length > 0) {
                    $scope.removeUsedConditions();
                    $scope.selectedComplexCondition = "0";
                    $scope.addDimensionDisabled = false;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadComplexConditionList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    }
    $scope.fetchComplexConditions();

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
                $scope.categoriesValid = true;

                var index = $scope.listCategories.indexOf(category);
                if (index != -1) {
                    $scope.listCategories.splice(index, 1);

                    if (Object.keys($scope.listCategories).length <= 0) {
                        $scope.addCategoryDisabled = true;
                    }
                }
            }
        }
    };

    $scope.removeCategory = function(index) {
        $scope.listCategories.push($scope.categories[index]);
        $scope.categories.splice(index, 1);
        $scope.addCategoryDisabled = false;

        if (Object.keys($scope.categories).length <= 0) {
            $scope.categoriesValid = false;
        }
    }

    $scope.addComplexCondition = function() {
        if ($scope.selectedComplexCondition != null) {
            var complexCondition = $scope.listComplexConditions[$scope.selectedComplexCondition];

            if (complexCondition != null) {
                $scope.complexConditions.push(complexCondition);
                $scope.conditionsValid = true;

                var index = $scope.listComplexConditions.indexOf(complexCondition);
                if (index != -1) {
                    $scope.listComplexConditions.splice(index, 1);

                    if (Object.keys($scope.listComplexConditions).length <= 0) {
                        $scope.addDimensionDisabled = true;
                    }
                }
            }
        }
    };

    $scope.removeComplexCondition = function(index) {
        $scope.listComplexConditions.push($scope.complexConditions[index]);
        $scope.complexConditions.splice(index, 1);
        $scope.addDimensionDisabled = false;

        if (Object.keys($scope.complexConditions).length <= 0) {
            $scope.conditionsValid = false;
        }
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
            if (!$scope.categories.hasOwnProperty(key)) {
                continue;
            }
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
        }).success(function() {
                $location.path( "/" );
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotCreateIndicator'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
        });
    };

    $scope.fetchOperatorTypes = function() {
        $http.get('api/complexcondition/operatortype')
            .success(function(operatorTypes) {
                operatorTypes.sortByName();
                $scope.listOperatorTypes = operatorTypes;

                if (Object.keys($scope.listOperatorTypes).length > 0) {
                    $scope.newCondition.operatorType = $scope.listOperatorTypes[0].id;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadOperatorTypeList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchForms = function() {
        $http.get('api/forms')
            .success(function(forms) {
                forms.sortByName();
                $scope.listForms = forms;

                if (Object.keys($scope.listForms).length > 0) {
                    $scope.newCondition.form = $scope.listForms[0].id;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadFormTypeList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchComparisonSymbols = function() {
        $http.get('api/complexcondition/comparisonsymbol')
            .success(function(comparisonSymbols) {
                comparisonSymbols.sortByName();
                $scope.listComparisonSymbols = comparisonSymbols;

                if (Object.keys($scope.listComparisonSymbols).length > 0) {
                    $scope.newCondition.comparisonSymbol = $scope.listComparisonSymbols[0].id;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadComparisonSymbolList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.launchDialog = function() {
        $scope.newCondition = {};
        $scope.fetchOperatorTypes();
        $scope.fetchForms();
        $scope.fetchComparisonSymbols();

        var dialog = $modal({
            template: "resources/partials/newComplexConditionDialog.html",
            persist: true,
            show: true,
            backdrop: "static",
            scope: $scope
        });
    };

    $scope.saveNewComplexCondition = function() {
        $scope.newCondition.indicators = [];

        $http({
            url: "api/complexcondition",
            method: "POST",
            data: $scope.newCondition,
            headers: { 'Content-Type': 'application/json' }
        }).success(function() {
                $scope.fetchComplexConditions();
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotCreateNewComplexCondition'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
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
            var mother_forms = new Array();
            var child_forms = new Array();
            var other_forms = new Array();
            for(var i = 0; i < forms.length; i++) {
                if(forms[i].tableName.indexOf("mother") !== -1) {
                    mother_forms.push(forms[i]);
                } else if (forms[i].tableName.indexOf("child") !== -1) {
                    child_forms.push(forms[i]);
                } else {
                    $http.get('api/forms/table/foreignKey/' + forms[i].tableName, {index:i})
                        .success(function(foreignKey, status, header, config) {
                            if(foreignKey.indexOf("mother") !== -1) {
                                mother_forms.push(forms[config.index]);
                            } else if(foreignKey.indexOf("child") !== -1) {
                                child_forms.push(forms[config.index]);
                            } else {
                                other_forms.push(forms[config.index]);
                            }
                        })
                        .error(function(data, status, header, config) {
                            $dialog.messageBox("Error", $scope.msg('forms.form.error.cannotReceiveForeignKey', forms[config.index].tableName), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
                        })
                }
            }
            $scope.mother_forms = mother_forms;
            $scope.child_forms = child_forms;
            $scope.other_forms = other_forms;
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

care.controller('userListController', function($scope, $http, $routeParams, $location, $dialog) {

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

care.controller('roleController', function($scope, $http, $routeParams, $location, $dialog) {

    $scope.fetchPermissions = function() {

    }
});

care.controller('userController', function($scope, $http, $routeParams, $location, $dialog) {

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

    $scope.submitUser = function(user) {

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
                    controller: 'ErrorsDialogController'
                  };

                $dialog.dialog($scope.opts).open();
            });

    };

    $scope.fetchRoles();

    if ($scope.userId !== undefined) {
        $scope.fetchUser();
    };
});

function ErrorsDialogController($scope, dialog){
    $scope.close = function(result){
    dialog.close(result);
  };
}
care.controller('categoriesController', function($scope, $http, $dialog, $route) {
    $scope.category = {};
    $scope.fetchCategories = function() {
        $http.get('api/indicator/category').success(function(category) {
            $scope.category = category;
        }).error(function(response) {
            $dialog.messageBox($scope.msg('error'), $scope.msg('category.error.load'), [{label: 'Ok', cssClass: 'btn'}]).open();
        });
    };
    $scope.createCategory = function(category) {
        $http({method: 'POST', 
            url: 'api/indicator/category' , 
            data: category, 
            headers: { 'Content-Type': 'application/json' }
        })
        .success(function(response) {
            $dialog.messageBox($scope.msg('information'), $scope.msg('category.success.create'), [{label: 'Ok', cssClass: 'btn'}]).open();
        }).error(function(response) {
            $dialog.messageBox($scope.msg('error'), $scope.msg('category.error.create'), [{label: 'Ok', cssClass: 'btn'}]).open();
        });
    };
    $scope.editCategory = function(category, newName) {
        if(newName && category && newName != category.name){
            category.name = newName;
            $http({method: 'PUT', 
                url: 'api/indicator/category/' + category.id, 
                data: category,
                headers: { 'Content-Type': 'application/json' }
            })
            .success(function(response) {
                $route.reload();
                $dialog.messageBox($scope.msg('information'), $scope.msg('category.success.edit'), [{label: 'Ok', cssClass: 'btn'}]).open();
            }).error(function(response) {
                $route.reload();
                $dialog.messageBox($scope.msg('error'), $scope.msg('category.error.edit'), [{label: 'Ok', cssClass: 'btn'}]).open();
            });
        }
        else if(!category){
            $dialog.messageBox($scope.msg('error'), $scope.msg('category.choose'), [{label: 'Ok', cssClass: 'btn'}]).open();
        }
        else {
            $dialog.messageBox($scope.msg('error'), $scope.msg('category.name.valid'), [{label: 'Ok', cssClass: 'btn'}]).open();
        }
    };
    
    $scope.deleteCategory = function(category) {
        var btns = [{result:'yes', label: 'Yes', cssClass: 'btn-primary btn'}, {result:'no', label: 'No', cssClass: 'btn-danger btn'}];
        $dialog.messageBox($scope.msg('category.confirmDelete.header'), $scope.msg('category.confirmDelete.message', category.name), btns)
        .open()
        .then(function(result) {
            if (result === 'yes') {
                $http({method: 'DELETE',
                    url: 'api/indicator/category/' + category.id,
                    data: category,
                    headers: { 'Content-Type': 'application/json' }
                })
                .success(function(response) {
                    $scope.fetchCategories();
                }).error(function(response) {
                    $dialog.messageBox($scope.msg('error'), $scope.msg('category.error.delete'), [{label: 'Ok', cssClass: 'btn'}]).open();
                });
            }
        });
    };
    $scope.fetchCategories();
});

