/*global define */

'use strict';

define(['angular'], function(angular) {

/* Services */

angular.module('movies.services', []).
    factory('moviesAPIservice', function($http) {

        var moviesAPI = {};

        moviesAPI.getMovies = function(year) {
            var url = "/movies";
            if (year) url += "?year=" + year;
            return $http({
                method: 'GET',
                url: url
            });
        };

        moviesAPI.getMovie = function(id) {
            return $http({
                method: 'GET',
                url: '/movies/' + id
            });
        };

        moviesAPI.getImdbInformation = function(imdb_id) {
            return $http({
                method: 'GET',
                url: 'http://www.omdbapi.com/?i=' + imdb_id
            });
        };

        moviesAPI.getYears = function() {
            return $http({
                method: 'GET',
                url: '/years'
            });
        };

        return moviesAPI;
    });
});
