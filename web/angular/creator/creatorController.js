app.controller("CreatorController", function ($scope, $filter, $log, $http) {

    $scope.atomic = 0;
    $scope.getId = function () {
        $scope.atomic = $scope.atomic + 1;
        return $scope.atomic;
    };

    $scope.circle = {
        stroke: {
            color: '#08B21F',
            weight: 2,
            opacity: 1
        },
        draggable: false,
        clickable: false,
        editable: true
    };

    /*
     fill: {
     color: '#08B21F',
     opacity: 0.5
     },
     */

    $scope.map = {
        control: {},
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
        },
        lines: []
    };

    $scope.icon = 'icon.png';

    $scope.path = [];
    /* quest model part */
    $scope.nodes = [];
    $scope.connections = {};
    $scope.events = {};
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

        if ($scope.markerType == "test") {
            $scope.path.push({
                latitude: lat,
                longitude: lon
            });
            return;
        }

        var node = {
            id: $scope.getId(),
            radius: $scope.radius,
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

            if($scope.eventFormHidden == false){
                $scope.event.causes[$scope.current.id] = 1;
                $scope.begin.marker.setAnimation(null);
                $scope.$apply();
                return;
            }

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

                if(previous.id == $scope.current.id){
                    $scope.$apply();
                    return;
                }

                $scope.connections[previous.id].children.push($scope.current.id);
                $scope.connections[$scope.current.id].parents.push(previous.id);

                $scope.map.lines.push(
                    {
                        points: [
                            previous.questLocation,
                            $scope.current.questLocation
                        ]
                    }
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

        var deleteLines = [];

        $scope.map.lines.forEach(
            function deleteLineIndexes(line, index) {
                if (equals(line.points[0], $scope.current.questLocation) || equals(line.points[1], $scope.current.questLocation)) {
                    deleteLines.push(index);
                }
            }
        );
        deleteLines.sort();
        for (var i = deleteLines.length - 1; i >= 0; i--) {
            $scope.map.lines.splice(deleteLines[i], 1);
        }

        delete $scope.connections[$scope.current.id];
        $scope.selected = 0;
    };

    function equals(A, B) {
        return A.latitude == B.latitude && A.longitude == B.longitude;
    }

    $scope.submitData = function () {


        var quest = {
            id: 123,
            nodes: $scope.nodes,
            connections: $scope.connections,
            events: $scope.events
        };

        $http.post('/app/save', quest, {headers: {
                'Content-Type': 'application/json',
                'dataType': 'application/json',
                'Accept': 'application/json, text/javascript'}}
        ).success(function (data, status, headers, config) {
                $log.log(data, status, headers, config);
            }).error(function (data, status, headers, config) {
                $log.log(data, status, headers, config);
            });

    };

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

    };

    $scope.load = function () {

        $http.get('/app/load', {headers: {
                'Content-Type': 'application/json',
                'dataType': 'application/json',
                'Accept': 'application/json, text/javascript'}}
        ).success(function (data, status, headers, config) {
                $log.log(data, status, headers, config);

                $scope.connections = data.connections;
                $scope.nodes = data.nodes;
                $scope.events = data.events;

                $scope.nodes.forEach(
                    function loadLines(node) {

                        $scope.connections[node.id].children.forEach(
                            function lineToChild(childId) {

                                var child = null;
                                $scope.nodes.forEach(
                                    function getById(node){
                                        if(node.id == childId){
                                            child = node;
                                        }
                                    }
                                );

                                $scope.map.lines.push({
                                    visible: true,
                                    points:[
                                        node.questLocation,
                                        child.questLocation
                                    ]
                                });
                            });

                        $scope.connections[node.id].parents.forEach(
                            function lineToParent(parentId) {

                                var parent = null;
                                $scope.nodes.forEach(
                                    function getById(node){
                                        if(node.id == parentId){
                                            parent = node;
                                        }
                                    }
                                );

                                $scope.map.lines.push({
                                    visible: true,
                                    points:[
                                        node.questLocation,
                                        parent.questLocation
                                    ]
                                });
                            });

                    });

            }).error(function (data, status, headers, config) {
                $log.log(data, status, headers, config);
            });
    };

    $scope.markerType = "quest";
    $scope.switchMarkers = function () {

        if ($scope.markerType == "quest") {
            $scope.markerType = "test";
        }
        else if ($scope.markerType == "test") {
            $scope.markerType = "quest";
        }
    }


    $scope.addEvent = function(){
        if($scope.selected == 0){
            return;
        }
        $scope.eventFormHidden = false;
        $scope.events[$scope.current.id]=$scope.event;
        $scope.selected = 0;
    };

    $scope.eventFormHidden = true;
    $scope.event = {
        causes: {}
    };

    $scope.finishEvent= function(){
        $scope.eventFormHidden = true;
    };

});

