angular.module('controllers')
	/**
	 * Controller for main page
	 *
	 * @param $scope Injected $scope (AngularJS provided service)
	 */
	.controller('MainController', ['$scope', '$location', '$routeParams', 'DataService', function($scope, $location, $routeParams, dataService) {
		var mode = $routeParams.mode || 'all';
		$scope.ride = mode == 'ride';
		$scope.room = mode == 'room';
		$scope.all = mode == 'all';

		$scope.from;
		$scope.to;
		$scope.invalidDates = false;
		$scope.everythingFilledOut = true;

		$scope.find = function() {
			var params = {
				from : $scope.from,
				to : $scope.to,
				start : Date.parse($("#start").val()),
				end : Date.parse($("#end").val()),
				guests : $scope.guests
			};

            dataService.set('params', params);

            if (params.start == null || params.from == null ||
				params.end == null || params.to == null ||
				params.guests == null) {
            	$scope.everythingFilledOut = false;
            	return;
			}

            if (params.start > params.end) {
            	$scope.invalidDates = true;
            	return;
			}

            if (mode == 'ride') {
                $location.path(
                    '/rides/h/' + params.from
                    + '/' + params.to
                    + '/' + params.start
                    +'/' + params.end
					+ '/' + mode
                    + '/' + params.guests
                );
            } else {
                $location.path(
                    '/rooms/' + params.from
                    + '/' + params.to
                    + '/' + params.start
                    +'/' + params.end
                    + '/' + mode
                    + '/' + params.guests
                );
			}
		};
	}])
;
