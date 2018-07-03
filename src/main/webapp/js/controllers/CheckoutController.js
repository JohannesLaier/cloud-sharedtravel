angular.module('controllers')
/**
 * Controller for main page
 *
 * @param $scope Injected $scope (AngularJS provided service)
 */
    .controller('CheckoutController', ['$scope', '$location', '$routeParams', 'DataService', function($scope, $location, $routeParams, dataService) {
        var data = dataService.get();

        $scope.calcDuration = function(durationInSeconds) {
            var durationInminutes = durationInSeconds / 60;
            var minutes = durationInminutes % 60;
            var hours = (durationInminutes - minutes) / 60;
            return (hours > 0 ? hours + " Stunden " : "") + minutes + " Minuten";
        };

        $scope.selectedRoom = data.selectedRoom;
        $scope.selectedRideOutward = data.selectedRideOutward;
        $scope.selectedRideReturn = data.selectedRideReturn;
        $scope.info = data.params;
    }])
;