'use strict';

angular.module('smgportalApp')
    .factory('Password', function ($resource) {
        return $resource('rest/account/change_password', {}, {
        });
    });

angular.module('smgportalApp')
    .factory('PasswordResetInit', function ($resource) {
        return $resource('rest/account/reset_password/init', {}, {
        })
    });

angular.module('smgportalApp')
    .factory('PasswordResetFinish', function ($resource) {
        return $resource('rest/account/reset_password/finish', {}, {
        })
    });
