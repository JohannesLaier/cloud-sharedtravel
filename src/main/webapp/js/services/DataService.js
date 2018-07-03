angular.module('services')
/**
 * Provides functions concerning data management.
 *
 * @param $location Injected $location (AngularJS provided service)
 * @param $interpolate Injected $interpolate (AngularJS provided service)
 * @param restService Injected RestService
 */
	.factory('DataService', ['RESTService', function (restService) {
		var data = {
			params : {},
            selectedRoom : null,
            selectedRideOutward : null,
            selectedRideReturn : null,
            rides : [],
			rooms : [],
		};
		
		/**
		 * Data initialize state
		 */
		var init  = false;

		var service = {
			/**
			 * Gets the main data object.
			 *
			 * @returns {Object} The main data object
			 */
			get: function() {
				return data;
			},

			/**
			 * Sets the main data object without creating a new object.
			 *
			 * @param data The data from the server
			 */
			set: function(type, entry) {
				data[type] = entry;
			},
			/**
			 * Adds a new entry.
			 *
			 * @param type The type of the entry to find
			 * @param trip Trip
			 */
			find : function(type, trip, callback, error) {
				restService.find(type, trip).success(function(data) {
					service.set(type, data);
					if (typeof callback == "function") {
						callback(data);
					}
				}).error(error);
			}
		};
		return service;
	}])
;
