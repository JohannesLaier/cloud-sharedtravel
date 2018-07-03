angular.module('controllers')
/**
 * Controller for main page
 *
 * @param $scope Injected $scope (AngularJS provided service)
 */
    .controller('RidesController', ['$scope', '$location', '$routeParams', 'DataService', function($scope, $location, $routeParams, dataService) {
        var mode = $routeParams.mode || 'all';

        $scope.rides;
        $scope.type = $routeParams.type;
        $scope.loading = true;
        $scope.error = false;
        $scope.invalidDates = false;
        $scope.everythingFilledOut = true;

        $scope.calcDuration = function(durationInSeconds) {
            var durationInminutes = durationInSeconds / 60;
            var minutes = durationInminutes % 60;
            var hours = (durationInminutes - minutes) / 60;
            return (hours > 0 ? hours + " Stunden " : "") + minutes + " Minuten";
        };

        var departureDate = $routeParams.start;
        var arrivalDate = $routeParams.end;
        var departure = $routeParams.from;
        var arrival = $routeParams.to;
        var guests = $routeParams.guests;
        var mode = $routeParams.mode;
        if ($routeParams.type == 'r') {
            departureDate = $routeParams.end;
            arrivalDate = $routeParams.start;
            departure = $routeParams.to;
            arrival = $routeParams.from;
        }

        $scope.select = function(ride) {
            if ($routeParams.type == 'h') {
                dataService.set('selectedRideOutward', ride);
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
                    '/rides/r/' + $routeParams.from
                    + '/' + $routeParams.to
                    + '/' + $routeParams.start
                    +'/' + $routeParams.end
                    + '/' + mode
                    + '/' + $routeParams.guests
                );
            } else {
                dataService.set('selectedRideReturn', ride);
                $location.path('/checkout/');
            }
        };

        dataService.find('ride', {
            departureDate : departureDate,
            arrivalDate : arrivalDate,
            departure : {
                name : departure
            },
            arrival : {
                name : arrival
            },
            seats : guests
        }, function (data) {
            $scope.rides = data;
            $scope.loading = false;
            if (!!data && data.length <= 0) {
                $scope.error = true;
            }
        }, function () {
            $scope.loading = false;
            $scope.error = true;
        });
    }])
;