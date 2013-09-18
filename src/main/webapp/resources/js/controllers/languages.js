var care = angular.module('care');

care.controller('languageListController', function($scope, $rootScope, $simplifiedHttpService,
        $http, $dialog, $location, $errorService) {
    $scope.title = $scope.msg('languages.list.title');

    Array.prototype.sortByField = function(fieldName) {
        this.sort(function(a, b) {
            if (a[fieldName] > b[fieldName]) {
                return 1;
            } else if (a[fieldName] < b[fieldName]) {
                return -1;
            } else {
                return 0;
            }
        });
    };

    $scope.language = {
        code: null
    };
    $scope.listLanguages = [];
    $scope.selectedLanguage = null;

    $scope.fetchLanguages = function() {
        $simplifiedHttpService.get($scope, 'api/languages',
                'languages.cannotLoadLanguageList', function(languages) {
            languages.sortByField('name');
            $scope.listLanguages = languages;

            if (Object.keys($scope.listLanguages).length > 0) {
                $scope.selectedLanguage = $scope.listLanguages[0].code;
            }
        });
    };
    $scope.fetchLanguages();

    $scope.defineLanguage = function() {
        $location.url('/admin/languages/' + $scope.language.code
            + '?templateCode=' + $scope.selectedLanguage + '&edit=false');
    };

    $scope.editLanguage = function(language) {
        $location.url('/admin/languages/' + language.code
            + '?templateCode=' + language.code + '&edit=true');
    };

    $scope.removeLanguage = function(language) {
        var btns = [{result:'yes', label: $scope.msg('common.yes'), cssClass: 'btn-primary btn'}, {result:'no', label: $scope.msg('common.no'), cssClass: 'btn-danger btn'}];
        $dialog.messageBox($scope.msg('languages.list.confirmDelete.header'), $scope.msg('languages.list.confirmDelete.message', language.name), btns)
            .open()
            .then(function(result) {
                if (result === 'yes') {
                    $http({
                        url: 'api/languages/' + language.code,
                        method: 'DELETE'
                    })
                    .success(function() {
                        location.reload();
                    })
                    .error(function() {
                        $errorService.genericError($scope, 'languages.error.cannotDeleteLanguage');
                    });
                }
            });
    };
});

care.controller('messageController', function($scope, $rootScope, $simplifiedHttpService,
        $http, $dialog, $routeParams, $errorService, $location, $timeout, i18nService) {

 $scope.fetchActiveUserDefaultLanguage = function() {
        $simplifiedHttpService.get($scope, 'api/users/logged_in/language',
                'languages.error.cannotLoadLanguage', function(language) {
            $scope.defaultLanguage = language;
            i18nService.init(language.code);
        });
    };
    $timeout($scope.fetchActiveUserDefaultLanguage, 0);

    $rootScope.$watch('languagesChanged', function() {
        if ($rootScope.languagesChanged !== undefined && $rootScope.languagesChanged !== false) {
            $rootScope.languagesChanged = false;
            $scope.fetchLanguages();
        }
    });

  $scope.selectLanguage = function(language) {
        $http({
            url: 'api/users/logged_in/language',
            method: 'PUT',
            data: language,
            headers: { 'Content-Type': 'application/json' }
        }).success(function() {
            $scope.defaultLanguage = language;
            i18nService.init(language.code);
        });
    };

   $scope.listLanguages = [];
    $scope.defaultLanguage = null;

    $scope.fetchLanguages = function() {
        $simplifiedHttpService.get($scope, 'api/languages',
                'languages.error.cannotLoadLanguageList', function(languages) {
            languages.sort();
            $scope.listLanguages = languages;
        });
    };
    $timeout($scope.fetchLanguages, 0);


    $scope.title = $scope.msg('languages.edit.title');

    $scope.isEdit = ($routeParams['edit'] == 'true');
    $scope.newCode = $routeParams['languageCode'];
    $scope.templateCode = $routeParams['templateCode'];
    $scope.listMessages = [];
    $scope.messages = {};

    var messageUrl;
    if ($scope.isEdit) {
        messageUrl = 'api/languages/messages/' + $scope.newCode;
    } else {
        messageUrl = ($scope.templateCode == null || $scope.templateCode == 'df')
            ? 'api/languages/messages' : 'api/languages/messages/' + $scope.templateCode;
    }
    
    $scope.fetchLanguage = function() {
        $simplifiedHttpService.get($scope, 'api/languages/' + $scope.newCode, 'languages.cannotLoadLanguage', function(language) {
            $scope.language = language;
        });
    };
    if ($scope.isEdit) {
        $scope.fetchLanguage();
    }

    $scope.fetchMessages = function() {
        $simplifiedHttpService.get($scope, messageUrl, 'languages.cannotLoadMessageList', function(messages) {
            $scope.listMessages = messages;
            $scope.constructMessages(messages);
        });
    };
    $timeout($scope.fetchMessages, 100);

    $scope.constructMessages = function(messages) {
        for (var i = 0; i < messages.length; i++) {
            $scope.constructMessage(messages[i]);
        }

        $scope.nextId = 1;
        var div = $scope.constructMessageElements($scope.messages, 0, $('#messageContainer'));
        $('#messageContainer').append(div);
    };

    $scope.constructMessage = function(message) {
        var messageParts = message.code.split('.');

        var lastCategory = $scope.messages;
        for (var i = 0; i < messageParts.length; i++) {
            var categoryName = messageParts[i];

            if (!lastCategory[categoryName]) {
                lastCategory[categoryName] = { '_name': categoryName };
            }
            lastCategory = lastCategory[categoryName];
        }

        lastCategory['_message'] = message.value;
        lastCategory['_code'] = message.code;
    };

    $scope.formatCategoryName = function(categoryName)
    {
        if (categoryName === undefined || categoryName.length <= 0) {
            return;
        }

        for (var i = categoryName.length - 1; i > 0; i--) {
            var character = categoryName.charAt(i);
            // Prevent adding spaces before characters that are not letters
            if (character == character.toUpperCase() && character != character.toLowerCase()) {
                categoryName = categoryName.substr(0, i) + categoryName.slice(i, 1) + ' ' + character
                    + categoryName.substr(i + 1, categoryName.length);
            }
        }

        return categoryName[0].toUpperCase() + categoryName.substr(1);
    };

    $scope.constructMessageElements = function(category, hierarchyLevel, parentElement) {
        var level = hierarchyLevel + 2;
        var div = angular.element('<div />');
        if (hierarchyLevel == 0) {
            div.attr('data-hierarchy-level', hierarchyLevel);
        }
        var isMessage = (category['_message'] !== undefined);

        if (isMessage) {
            div.attr('data-message', true);
            var id = $scope.nextId++;
            var code = category['_code'];
            var message = category['_message'];

            var controlGroup = angular.element('<div />').addClass('control-group');
            var label = angular.element('<label />').addClass('control-label')
                .attr('for', id).text($scope.msg(code, '{0}'));
            var controls = angular.element('<div />').addClass('controls');
            var input = angular.element('<input />').addClass('input-block-level')
                .attr('id', id).attr('type', 'text').attr('required', 'required')
                .attr('data-code', code).val(message).on('change', function() {
                    var error = ($(this).val().length <= 0);

                    $(this).parent().parent().toggleClass('alert alert-error', error);
            });

            controls.append(input);
            controlGroup.append(label);
            controlGroup.append(controls);
            div.append(controlGroup);

            var lastMessage = parentElement.children('div:not(.message-category):last');
            if (lastMessage.length) {
                lastMessage.after(div);
            } else {
                parentElement.prepend(div);
            }
            return null;
        }

        var keys = Object.keys(category);
        keys.sort();
        var messages = keys;

        for (var i = 0; i < keys.length; i++) {
            if (keys[i] != '_name' && category.hasOwnProperty(keys[i])) {
                if (category['_message'] !== undefined) {
                    continue;
                }

                div.append($scope.constructMessageElements(category[keys[i]], hierarchyLevel + 1, div));
            };
        }

        var name = $scope.formatCategoryName(category['_name']);
        var caption = angular.element('<h' + level + ' />').text(name);
        div.prepend(caption);
        div.addClass('message-category');
        return div;
    };

    $scope.saveMessages = function() {
        var method = ($scope.isEdit) ? 'PUT' : 'POST';
        var url = ($scope.isEdit) ? 'api/languages/' + $scope.newCode : 'api/languages';

        $scope.language.messages = [];
        $('#messageContainer').find('input').each(function(index, value) {
            var message = {
                code: $(value).attr('data-code'),
                value: $(value).val()
            };

            $scope.language.messages.push(message);
        });

        $http({
            url: url,
            method: method,
            data: $scope.language,
            headers: { 'Content-Type': 'application/json' },
        }).success(function(data, status, headers, config) {
            $rootScope.languagesChanged = true;
            location.href = '#/messages';
        }).error(function(data, status, headers, config) {
            $errorService.genericError($scope, 'languages.error.cannotSaveLanguage');
        });
    };

    $scope.revertChanges = function() {
        var btns = [{result:'yes', label: $scope.msg('common.yes'), cssClass: 'btn-primary btn'}, {result:'no', label: $scope.msg('common.no'), cssClass: 'btn-danger btn'}];
        $dialog.messageBox($scope.msg('languages.edit.confirmRevert.header'), $scope.msg('languages.edit.confirmRevert.message'), btns)
            .open()
            .then(function(result) {
                if (result === 'yes') {
                    $('#messageContainer').empty();
                    $scope.constructMessages($scope.listMessages);
                }
            });
    };

    $scope.validateForm = function() {
        var emptyMessages = $('form').find('input:invalid');

        return (emptyMessages.length <= 0);
    };
});
