<!DOCTYPE html>
<html>
<head>
    <style>
        #map_canvas {
            width: 500px;
            height: 400px;
        }
    </style>

    <script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript">

        var markers = new Array();
        var map, myLatLng, mapOptions, marker;


        function addMarker(event, map) {

            var marker = new google.maps.Marker({
                map: map,
                visible: true,
                position: event.latLng,
                title: 'Untitled checkpoint'
            });
            marker.setPosition(event.latLng);
            var n = markers.length.toString();

            markers.push({
                Marker: marker,
                parrent: -1,
                child: -1
            });

        }


        function initialize() {

            var someValue = "${someValue}";
            alert(someValue);

            myLatlng = new google.maps.LatLng(45.81497, 15.97851);
            mapOptions = {
                zoom: 15,
                center: myLatlng,
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                disableDoubleClickZoom: true,
                draggableCursor: 'default'
            }
            map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

            google.maps.event.addListener(map, 'click', function (event) {
                addMarker(event, map);
            });
        }

        google.maps.event.addDomListener(window, 'load', initialize);

    </script>

</head>
<body>
<div id="map_canvas"></div>
</body>
</html>

