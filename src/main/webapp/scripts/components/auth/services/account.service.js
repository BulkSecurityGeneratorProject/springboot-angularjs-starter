'use strict';

angular.module('smgportalApp')
    .factory('Account', function Account($resource) {
        return $resource('rest/account', {}, {
            'get': { method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response;
                    }
                }
            }
        });
    });
