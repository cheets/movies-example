/*global define */

'use strict';

define(['angular'], function(angular) {

/* Controllers */
var controllers = {};

var sanitizeText = function(text) {
    if(typeof text != 'undefined') {
        text = text.toString();
        return encodeURIComponent(text.replace("-", "--").replace("_", "__"));
    }
    return "";
};

var createBadge = function(subject, status, color) {
    var badge = "http://img.shields.io/badge/";
    var imgPostfix = ".svg?style=flat";
    return badge + sanitizeText(subject) + "-" + sanitizeText(status) + "-" + color + imgPostfix;
};

controllers.Movies = function($scope, $routeParams, moviesAPIservice) {
    $scope.year = $routeParams.year;
    moviesAPIservice.getMovies($scope.year).success(function(response) {
        response.movies.forEach(function(movie){
            moviesAPIservice.getImdbInformation(movie.imdbID).success(function(data) {
                movie.rating = data.imdbRating;
                movie.ratingBadgeUrl = createBadge("IMDb Rating", movie.rating, "yellow");
                movie.yearBadgeUrl = createBadge("Year", movie.year, "brightgreen");
            });
        });

        $scope.movies = response.movies;
    });

    moviesAPIservice.getYears().success(function(response) {
        $scope.years = response;
    })
};

controllers.Movies.$inject = ["$scope", "$routeParams", "moviesAPIservice"];

controllers.Movie = function($scope, $routeParams, moviesAPIservice) {
    $scope.id = $routeParams.id;
    moviesAPIservice.getMovie($scope.id).success(function(movie) {
        moviesAPIservice.getImdbInformation(movie.imdbID).success(function(data) {
            movie.rating = data.imdbRating;
            movie.metascore = data.Metascore;
            movie.director = data.Director;
            movie.writer = data.Writer;
            movie.actors = data.Actors;
            movie.plot = data.Plot;
            movie.genre = data.Genre;
            movie.runtime = data.Runtime;
            movie.rated = data.Rated;

            var color = "green";
            switch (data.Rated) {
                case "G":
                    color = "brightgreen";
                    break;
                case "PG":
                    color = "yellow";
                    break;
                case "PG-13":
                    color = "orange";
                    break;
                case "R":
                    color = "red";
                    break;
            }

            movie.ratedBadgeUrl = createBadge("Rated", movie.rated, color);
            movie.ratingBadgeUrl = createBadge("IMDb Rating", movie.rating, "yellow");
            movie.yearBadgeUrl = createBadge("Year", movie.year, "brightgreen");
            movie.metascoreBadgeUrl = createBadge("Metascore", movie.metascore, "orange");
        });

        $scope.movie = movie;
    });
};

controllers.Movie.$inject = ["$scope", "$routeParams", "moviesAPIservice"];

return controllers;

});