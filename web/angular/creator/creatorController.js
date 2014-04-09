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

    $scope.nodes = [
        {
            id: 0,
            radius: 50,
            questLocation: {
                latitude: 42.641900799999990000,
                longitude: 18.106484899999940000
            }
        },
        {
            id: 1,
            radius: 50,
            questLocation: {
                latitude: 42.64300799999990000,
                longitude: 18.106484899999940000
            }
        }
    ];

    $scope.quest = {

        id:123,
        nodes: $scope.nodes
    };

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
        questLocation: {
            latitude: 42.641900799999990000,
            longitude: 18.106484899999940000
        }
    };


    $scope.addNew = function (lat, lon) {
        var node = {
            id: $scope.getId(),
            radius: 50,
            questLocation: {
                latitude: lat,
                longitude: lon
            }
        };
        $scope.current = node;

        $scope.nodes.push(node);
    };

    $scope.markerClickEvent = {
        click: function (marker, eventName, args) {

            var items = $filter('locationFilter')($scope.nodes, {
                latitude: marker.getPosition().lat(),
                longitude: marker.getPosition().lng()
            });
            $scope.current = items[0];
            $scope.$apply();
        }
    };

    $scope.deleteMarker = function () {
        $scope.nodes.splice($scope.nodes.indexOf($scope.current), 1);
    };

    $scope.submitData = function () {

        var postData = { status: 0, message: "hello"};

        $http.post('/app/hook', $scope.quest , {headers: {
                'Content-Type': 'application/json',
                'dataType': 'application/json',
                'Accept': 'application/json, text/javascript'}}
        ).success(function (data, status, headers, config) {
                $log.log(data, status, headers, config);
            }).error(function (data, status, headers, config) {
                $log.log(data, status, headers, config);
            });

    }



});

