
ceventApp.factory('SaveMessageService', ['$http',
	 function ($http) {
	     return {
	         saveMessage: function(data) {
	             $http.post('app/messages', data);
	         }
	     };
	 }]);

ceventApp.factory('ListMessagesService', ['$http',
	 function ($http) {
	     return {
	    	 getAllMessages: function() {
	             return $http.get('app/messages').then(function(result) {
	            	 return result.data;
	             });
	         }
	     };
	 }]);
