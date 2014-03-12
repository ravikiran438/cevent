'use strict';

/* Controllers */

ceventApp.controller('GuestBookAddCtrl', ['$scope', '$http', 'SaveMessageService', '$state',
    function ($scope, $http, SaveMessageService, $state) {
		$scope.save = function() {
			SaveMessageService.saveMessage($scope.user);
			$state.go('guestbook.list');
		};
	}]);

ceventApp.controller('GuestBookListCtrl', ['$scope', '$http', 'ListMessagesService',
      function ($scope, $http, ListMessagesService) {
			ListMessagesService.getAllMessages().then(function(data) {
				 $scope.messages = data;
			});
  	}]);

ceventApp.controller('VenueCtrl', ['$scope',
        function ($scope) {
		  var latlng = new google.maps.LatLng(17.446117,78.564191);
		  var latlng2 = new google.maps.LatLng(14.4243832,79.9622908);
		  
		  var mapOptions = {
		    zoom: 12,
		    center: latlng
		  };
		  var mapOptions2 = {
		    zoom: 12,
		    center: latlng2
		  };
		  var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
		  var map2 = new google.maps.Map(document.getElementById('map-canvas2'), mapOptions2);
		  var contentString = '<div id="content">'+
	      '<div id="siteNotice">'+
	      '</div>'+
	      '<h4 id="firstHeading" class="firstHeading">Sai Gardens</h4>'+
	      '<div id="bodyContent">'+
	      '<p>Narasimha Colony, Nacharam, <br/>Secunderabad, Andhra Pradesh 500076, India</p>'+
	      '</div>'+
	      '</div>';
		  
		  var contentString2 = '<div id="content">'+
	      '<div id="siteNotice">'+
	      '</div>'+
	      '<h4 id="firstHeading" class="firstHeading">Sri Tirupati Naidu A/c., Kalyanamandapam</h4>'+
	      '<div id="bodyContent">'+
	      '<p>G.N.T. Road, Opp. Govt. District Head Quarters Hospital, Nellore, Andhra Pradesh, India</p>'+
	      '</div>'+
	      '</div>';

		  var infowindow = new google.maps.InfoWindow({
		      content: contentString
		  });
		  
		  var infowindow2 = new google.maps.InfoWindow({
		      content: contentString2
		  });

		  var marker = new google.maps.Marker({
		          map: map,
		          position: latlng,
		          title:"Sai Gardens",
		          animation: google.maps.Animation.DROP
		  });
		  var marker2 = new google.maps.Marker({
	          map: map2,
	          position: latlng2,
	          title:"Sri Tirupati Naidu A/c., Kalyanamandapam",
	          animation: google.maps.Animation.DROP
		  });
		  google.maps.event.addListener(marker, 'click', function() {
			    infowindow.open(map,marker);
			  });
		  google.maps.event.addListener(marker2, 'click', function() {
			    infowindow2.open(map2,marker2);
			  });
	}]);