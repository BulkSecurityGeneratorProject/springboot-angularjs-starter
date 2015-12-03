'use strict';

angular.module('smgportalApp')
    .factory('Activate', function ($resource) {
        return $resource('rest/activate', {}, {
            'get': { method: 'GET', params: {}, isArray: false}
        });
    });


