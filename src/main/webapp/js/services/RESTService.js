angular.module('services')
	.factory('RESTService', ['$http', function($http) {
		return {
			find: function(type, trip) {
				return $http.post('rest/' + type + '/find', trip);
			}
		}
	}])