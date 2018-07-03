angular.module('controllers')
/**
 * Controller for main page
 *
 * @param $scope Injected $scope (AngularJS provided service)
 */
    .controller('PageController', ['$scope', '$location', function($scope, $location) {
            $scope.displayCarousel = $location.path() == '/';
    }])
;
