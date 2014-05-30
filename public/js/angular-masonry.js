/*!
 * angular-masonry 0.8.1
 * Pascal Hartig, weluse GmbH, http://weluse.de/
 * License: MIT
 * Modified from https://github.com/passy/angular-masonry/pull/41/commits to work without jQuery
 */
'use strict';
define(['angular'], function (angular) {
    angular.module('wu.masonry', []).controller('MasonryCtrl', [
        '$scope',
        '$element',
        '$timeout',
        function controller($scope, $element, $timeout) {
            var bricks = {};
            var schedule = [];
            var destroyed = false;
            var self = this;
            var timeout = null;
            var mason = null;
            this.preserveOrder = false;
            this.loadImages = true;

            // Masonry object created... use away
            $scope.$on('masonry.created', function () {
                mason = $scope.mason;
            });

            this.scheduleMasonryOnce = function scheduleMasonryOnce() {
                var args = arguments;
                var found = schedule.filter(function filterFn(item) {
                    return item[0] === args[0];
                }).length > 0;
                if (!found) {
                    this.scheduleMasonry.apply(null, arguments);
                }
            };
            // Make sure it's only executed once within a reasonable time-frame in
            // case multiple elements are removed or added at once.
            this.scheduleMasonry = function scheduleMasonry() {
                if (timeout) {
                    $timeout.cancel(timeout);
                }
                schedule.push([].slice.call(arguments));
                timeout = $timeout(function runMasonry() {
                    if (destroyed) {
                        return;
                    }
                    schedule.forEach(function scheduleForEach(args) {
                        mason[args]();
                    });
                    schedule = [];
                }, 30);
            };
            function defaultLoaded(_el) {
                angular.element(_el).addClass('loaded');
            }

            this.appendBrick = function appendBrick(element, id) {
                if (destroyed) {
                    return;
                }
                function _append() {
                    if (Object.keys(bricks).length === 0) {
                        mason.resize();
                    }
                    if (bricks[id] === undefined) {
                        // Keep track of added elements.
                        bricks[id] = true;
                        defaultLoaded(element);
                        mason.appended(element);
                    }
                }

                function _layout() {
                    // I wanted to make this dynamic but ran into huuuge memory leaks
                    // that I couldn't fix. If you know how to dynamically add a
                    // callback so one could say <masonry loaded="callback($element)">
                    // please submit a pull request!
                    self.scheduleMasonryOnce('layout');
                }

                if (!self.loadImages) {
                    _append();
                    _layout();
                } else if (self.preserveOrder) {
                    _append();
                    imagesLoaded(element, _layout);
                } else {
                    imagesLoaded(element, function imagesLoaded() {
                        _append();
                        _layout();
                    });
                }
            };
            this.removeBrick = function removeBrick(id, element) {
                if (destroyed) {
                    return;
                }
                delete bricks[id];
                mason.remove(element);
                this.scheduleMasonryOnce('layout');
            };
            this.destroy = function destroy() {
                destroyed = true;

                mason.destroy();

                $scope.$emit('masonry.destroyed');
                bricks = [];
            };
            this.reload = function reload() {
                mason.layout();
                $scope.$emit('masonry.reloaded');
            };
        }
    ]).directive('masonry', function masonryDirective() {
        return {
            restrict: 'AE',
            controller: 'MasonryCtrl',
            scope: true,
            link: {
                pre: function preLink(scope, element, attrs, ctrl) {
                    var attrOptions = scope.$eval(attrs.masonry || attrs.masonryOptions);
                    var options = angular.extend({
                        itemSelector: attrs.itemSelector || '.masonry-brick',
                        columnWidth: parseInt(attrs.columnWidth, 10) || attrs.columnWidth
                    }, attrOptions || {});

                    // Assign to scope to share with the controller
                    scope.mason = new Masonry(element[0], options);
                    var loadImages = scope.$eval(attrs.loadImages);
                    ctrl.loadImages = loadImages !== false;
                    var preserveOrder = scope.$eval(attrs.preserveOrder);
                    ctrl.preserveOrder = preserveOrder !== false && attrs.preserveOrder !== undefined;
                    scope.$emit('masonry.created', element);
                    scope.$on('$destroy', ctrl.destroy);
                }
            }
        };
    }).directive('masonryBrick', function masonryBrickDirective() {
        return {
            restrict: 'AC',
            require: '^masonry',
            scope: true,
            link: {
                pre: function preLink(scope, element, attrs, ctrl) {
                    var id = scope.$id, index;
                    ctrl.appendBrick(element, id);
                    element.on('$destroy', function () {
                        ctrl.removeBrick(id, element);
                    });
                    scope.$on('masonry.reload', function () {
                        ctrl.scheduleMasonryOnce('reloadItems');
                        ctrl.scheduleMasonryOnce('layout');
                    });
                    scope.$watch('$index', function () {
                        if (index !== undefined && index !== scope.$index) {
                            ctrl.scheduleMasonryOnce('reloadItems');
                            ctrl.scheduleMasonryOnce('layout');
                        }
                        index = scope.$index;
                    });
                }
            }
        };
    });
});
