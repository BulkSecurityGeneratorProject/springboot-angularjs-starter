'use strict';

angular.module('smgportalApp')
    .factory('errorHandlerInterceptor', function ($q, $rootScope) {
        return {
            'responseError': function (response) {
                if (!(response.status == 401 && response.data.path.indexOf("/rest/account") == 0 )){
	                $rootScope.$emit('smgportalApp.httpError', response);
	            }
                return $q.reject(response);
            }
        };
    });
