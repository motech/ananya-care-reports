'use strict';

/* localization service */

var localizationModule = angular.module('localization', [])

localizationModule.factory("i18nService", function() {

    var service = {
        ready : false,
        loading : false,
        languages : {},

        getMessage : function(key, params) {
            var msg = key;

            if (this.ready == true) {
                msg = jQuery.i18n.prop(key, params);
            } else if (this.loading == false) {
                this.init();
            }

            return msg;
        },

        init : function(lang) {
            this.ready = false;
            this.loading = true;
            var self = this;

            jQuery.i18n.properties({
                name:'messages',
                path: 'api/languages/messages/plain/',
                mode:'map',
                cache: true,
                language: lang || null,
                callback: function() {
                    self.ready = true;
                    self.loading = false;
                }
            });
        },

        getLanguage : function(locale) {
            return {
                key: locale.toString() || "df",
                value: this.languages[locale.fullName()] || this.languages[locale.withoutVariant()] || this.languages[locale.language] || "Default English"
            }
        }
    }

    return service;
});
