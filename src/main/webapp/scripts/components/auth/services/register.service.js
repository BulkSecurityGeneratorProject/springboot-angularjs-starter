'use strict';

angular.module('smgportalApp')
    .factory('Register', function ($resource) {
        return $resource('rest/register', {}, {
        });
    });


