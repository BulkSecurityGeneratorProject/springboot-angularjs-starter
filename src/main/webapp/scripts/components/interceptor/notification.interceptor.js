 'use strict';

angular.module('smgportalApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-smgportalApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-smgportalApp-params')});
                }
                return response;
            }
        };
    });
