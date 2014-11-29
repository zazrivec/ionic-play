angular.module('starter.filters', [])
    .filter('tx', function() {
        return function(input) {
            input = input || '';
            if (input == 'd') {
                return "Debet";
            } else if (input == 'c') {
                return "Kredit";
            }
            return input;
        };
    });