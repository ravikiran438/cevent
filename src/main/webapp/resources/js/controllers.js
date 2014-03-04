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