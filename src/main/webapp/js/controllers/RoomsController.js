angular.module('controllers')
/**
 * Controller for main page
 *
 * @param $scope Injected $scope (AngularJS provided service)
 */
    .controller('RoomsController', ['$scope', '$location', '$routeParams', 'DataService', function($scope, $location, $routeParams, dataService) {
        var mode = $routeParams.mode || 'all';

        $scope.rooms;
        $scope.loading = true;
        $scope.error = false;
        $scope.invalidDates = false;
        $scope.everythingFilledOut = true;

        $scope.select = function (room) {
            dataService.set('selectedRoom', room);
            if (mode == 'room') {
                $location.path('/checkout/');
            } else {
                if ($routeParams.start == null || $routeParams.from == null ||
                    $routeParams.end == null || $routeParams.to == null ||
                    $routeParams.guests == null) {
                    $scope.everythingFilledOut = false;
                    return;
                }

                if ($routeParams.start > $routeParams.end) {
                    $scope.invalidDates = true;
                    return;
                }

                $location.path(
                    '/rides/h/' + $routeParams.from
                    + '/' + $routeParams.to
                    + '/' + $routeParams.start
                    +'/' + $routeParams.end
                    + '/' + mode
                    + '/' + $routeParams.guests
                );
            }
        };

        dataService.find('room', {
             departureDate : $routeParams.start,
             arrivalDate : $routeParams.end,
             departure : {
             name : $routeParams.from
             },
             arrival : {
             name : $routeParams.to
             },
             seats : $routeParams.guests
         }, function (data) {
            $scope.rooms = data;
            $scope.loading = false;
            if (!!data && data.length <= 0) {
                $scope.error = true;
            }
         }, function() {
            $scope.loading = false;
            $scope.error = true;
        });
    }])
;