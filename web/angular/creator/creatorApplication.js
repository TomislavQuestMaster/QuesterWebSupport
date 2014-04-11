var app = angular.module('CreatorApp', ['google-maps']).filter('locationFilter', function(){

    return function(items, location){

        var arrayToReturn = [];
        for (var i=0; i<items.length; i++){
            if (items[i].questLocation.latitude == location.latitude && items[i].questLocation.longitude == location.longitude) {
                arrayToReturn.push(items[i]);
            }
        }

        return arrayToReturn;
    };
});