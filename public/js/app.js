/*global define, angular */

'use strict';

require(['angular', './controllers', './services', 'angular-route', './angular-masonry'],
    function(angular, controllers) {

        angular.module('movies', ['movies.services', 'wu.masonry', 'ngRoute']).
            config(['$routeProvider', function($routeProvider) {
                $routeProvider.when('/movies', {templateUrl: 'partials/movies.html', controller: controllers.Movies});
                $routeProvider.when('/movies/:id', {templateUrl: 'partials/movie.html', controller: controllers.Movie});
                $routeProvider.otherwise({redirectTo: '/movies'});
            }]);

        angular.bootstrap(document, ['movies']);
});
