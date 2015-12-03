'use strict';

angular.module('smgportalApp')
    .factory('Sessions', function ($resource) {
        return $resource('rest/account/sessions/:series', {}, {
            'getAll': { method: 'GET', isArray: true}
        });
    });



