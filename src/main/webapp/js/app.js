'use strict';

/**
 * Initialize dependency modules
 */
angular.module('directives', []);
angular.module('filters', []);
angular.module('services', []);
angular.module('controllers', []);

/**
 * Loads the template of the URL with the fitting controller.
 * If no valid URL redirect to dashboard.
 */
angular.module('app', ['ngRoute', 'directives', 'filters', 'services', 'controllers'])
    .filter('newlines', function() {
        return function(text) {
            return text.split(/\n/g);
        };
    })
  .config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'views/main.html',
            controller: 'MainController'
        })
        .when('/impressum', {
            templateUrl: 'views/impressum.html',
            controller: 'PageController'
        })
        .when('/find/:mode', {
            templateUrl: 'views/main.html',
            controller: 'MainController'
        })
        .when('/rooms/:from/:to/:start/:end/:mode/:guests', {
            templateUrl: 'views/list_rooms.html',
            controller: 'RoomsController'
        })
        .when('/rides/:type/:from/:to/:start/:end/:mode/:guests', {
            templateUrl: 'views/list_rides.html',
            controller: 'RidesController'
        })
        .when('/checkout', {
            templateUrl: 'views/checkout.html',
            controller: 'CheckoutController'
        })
        .otherwise({ redirectTo: '/'});
  })
;
