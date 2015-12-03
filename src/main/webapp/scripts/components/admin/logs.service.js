'use strict';

angular.module('smgportalApp')
    .factory('LogsService', function ($resource) {
        return $resource('rest/logs', {}, {
            'findAll': { method: 'GET', isArray: true},
            'changeLevel': { method: 'PUT'}
        });
    });
