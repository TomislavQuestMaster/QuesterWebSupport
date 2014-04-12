app.controller("CreatorController", function ($scope, $filter, $log, $http) {

    $scope.atomic = 1;
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

    $scope.path = [
        {
            latitude: 42.641900799999990000,
            longitude: 18.106484899999940000
        },
        {
            latitude: 42.64300799999990000,
            longitude: 18.106484899999940000
        }
    ];
    /* quest model part */
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

    $scope.connections = {};
    $scope.connections[0] = {
        children: [],
        parents: []
    };
    $scope.connections[1] = {
        children: [],
        parents: []
    };

    $scope.quest = {

        id: 123,
        nodes: $scope.nodes,
        connections: $scope.connections
    };
    // quest model end

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

        if($scope.markerType == "test"){
            $scope.path.push({
                latitude: lat,
                longitude: lon
            });
            return;
        }

        var node = {
            id: $scope.getId(),
            radius: 50,
            questLocation: {
                latitude: lat,
                longitude: lon
            }
        };
        $scope.current = node;
        $scope.connections[node.id] = {
            children: [],
            parents: []
        };

        $scope.nodes.push(node);
    };

    $scope.selected = 0;
    $scope.lines = [];
    $scope.begin = {
        location: {
            latitude: 42.641900799999990000,
            longitude: 18.106484899999940000
        },
        marker: null
    };

    $scope.markerClickEvent = {
        click: function (marker, eventName, args) {

            var previous = $scope.current;
            var items = $filter('locationFilter')($scope.nodes, {
                latitude: marker.getPosition().lat(),
                longitude: marker.getPosition().lng()
            });
            $scope.current = items[0];

            if ($scope.selected == 0) {
                $scope.begin.location = {
                    latitude: marker.getPosition().lat(),
                    longitude: marker.getPosition().lng()
                };
                $scope.selected = 1;
                $scope.begin.marker = marker;
                marker.setAnimation(google.maps.Animation.BOUNCE);
            }
            else if ($scope.selected == 1) {


                $scope.begin.marker.setAnimation(null);
                $scope.selected = 0;

                $scope.connections[previous.id].children.push($scope.current.id);
                $scope.connections[$scope.current.id].parents.push(previous.id);

                $scope.lines.push(
                    [
                        previous.questLocation,
                        $scope.current.questLocation
                    ]
                );
            }

            $scope.$apply();
        }
    };

    $scope.deleteMarker = function () {

        if ($scope.selected == 0) {
            return;
        }

        $scope.nodes.splice($scope.nodes.indexOf($scope.current), 1);

        $scope.connections[$scope.current.id].children.forEach(
            function deleteParent(id) {
                $scope.connections[id].parents.splice(
                    $scope.connections[id].parents.indexOf($scope.current.id), 1);
            });

        $scope.connections[$scope.current.id].parents.forEach(
            function deleteChild(id) {
                $scope.connections[id].children.splice(
                    $scope.connections[id].children.indexOf($scope.current.id), 1);
            });

        $scope.lines.forEach(
            function deleteLine(line, index) {
                if (equals(line[0], $scope.current.questLocation) || equals(line[1], $scope.current.questLocation)) {
                    $scope.lines.splice(index, 1);
                }
            }
        );

        delete $scope.connections[$scope.current.id];
        $scope.selected = 0;

        $scope.$apply();
    };

    function equals(A, B) {
        return A.latitude == B.latitude && A.longitude == B.longitude;
    }

    $scope.submitData = function () {

        $http.post('/app/hook', $scope.quest, {headers: {
                'Content-Type': 'application/json',
                'dataType': 'application/json',
                'Accept': 'application/json, text/javascript'}}
        ).success(function (data, status, headers, config) {
                $log.log(data, status, headers, config);
            }).error(function (data, status, headers, config) {
                $log.log(data, status, headers, config);
            });

    }

    $scope.runTest = function () {

        $http.post('/app/test', $scope.path, {headers: {
                'Content-Type': 'application/json',
                'dataType': 'application/json',
                'Accept': 'application/json, text/javascript'}}
        ).success(function (data, status, headers, config) {
                $log.log(data, status, headers, config);
            }).error(function (data, status, headers, config) {
                $log.log(data, status, headers, config);
            });

    }

    $scope.markerType = "quest";
    $scope.switchMarkers = function () {

        if($scope.markerType == "quest"){
            $scope.markerType = "test";
        }
        else if($scope.markerType == "test"){
            $scope.markerType = "quest";
        }
    }


});

