app.controller("CreatorController", function ($scope, $filter, $log, $http) {

    $scope.atomic = 0;
    $scope.getId = function () {
        $scope.atomic = $scope.atomic + 1;
        return $scope.atomic;
    };

    $scope.circle = {
        center: {
            latitude: 42.641900799999990000,
            longitude: 18.106484899999940000
        },
        radius: 50,
        stroke: {
            color: '#08B21F',
            weight: 2,
            opacity: 1
        },
        fill: {
            color: '#08B21F',
            opacity: 0.5
        }
    };

    $scope.map = {
        center: {
            latitude: 42.641900799999990000,
            longitude: 18.106484899999940000
        },
        zoom: 15,
        events: {
            click: function (mapModel, eventName, originalEventArgs) {

                var e = originalEventArgs[0];

                $scope.addNew(e.latLng.lat(), e.latLng.lng());
                $scope.$apply();
            }
        }
    };

    $scope.markers = [
        {
            id: 0,
            radius: 50,
            location: {
                latitude: 42.641900799999990000,
                longitude: 18.106484899999940000
            }
        },
        {
            id: 1,
            radius: 50,
            location: {
                latitude: 42.64300799999990000,
                longitude: 18.106484899999940000
            }
        }
    ];

    $scope.links = [
        {
            from: 0,
            to: 1
        }
    ];

    $scope.hidden = "false";
    $scope.current = {
        id: 0,
        radius: 50,
        location: {
            latitude: 42.641900799999990000,
            longitude: 18.106484899999940000
        }
    };


    $scope.addNew = function (lat, lon) {
        var marker = {
            id: $scope.getId(),
            radius: 50,
            location: {
                latitude: lat,
                longitude: lon
            }
        };
        $scope.current = marker;

        $scope.markers.push(marker);
    };

    $scope.markerClickEvent = {
        click: function (marker, eventName, args) {

            var items = $filter('locationFilter')($scope.markers, {
                latitude: marker.getPosition().lat(),
                longitude: marker.getPosition().lng()
            });
            $scope.current = items[0];
            $scope.$apply();
        }
    };

    $scope.deleteMarker = function () {
        $scope.markers.splice($scope.markers.indexOf($scope.current), 1);
    };

    $scope.submitData = function () {

        var postData = { status: 0, message: "hello"};
        $http.post('/app/hook', postData, {headers: {
                'Content-Type': 'application/json',
                'dataType': 'application/json',
                'Accept': 'application/json, text/javascript'}}
        ).success(function (data, status, headers, config) {
                alert("yes");
                $log.log(data, status, headers, config);
            }).error(function (data, status, headers, config) {
                alert("noo");
                $log.log(data, status, headers, config);
            });

    }



});

