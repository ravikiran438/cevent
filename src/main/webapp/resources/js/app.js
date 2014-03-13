var ceventApp = angular.module('ceventApp', ['ui.bootstrap', 'ui.router', 'xeditable', 'ngAnimate']);

    ceventApp.config(function($stateProvider, $urlRouterProvider){
      
      // For any unmatched url, send to /route1
      $urlRouterProvider.otherwise("/home");
      
      $stateProvider
      .state('home', {
          url: "/home",
          templateUrl: "views/home.html"
      	})
        .state('route1', {
            url: "/route1",
            templateUrl: "views/route1.html"
        })
          .state('route1.list', {
              url: "/list",
              templateUrl: "views/route1.list.html",
              controller: function($scope){
                $scope.items = ["A", "List", "Of", "Items"];
              }
          })
          
        .state('route2', {
            url: "/route2",
            templateUrl: "views/route2.html"
        })
          .state('route2.list', {
              url: "/list",
              templateUrl: "views/route2.list.html",
              controller: function($scope){
                $scope.things = ["A", "Set", "Of", "Things"];
              }
          })
          
          .state('guestbook', {
            url: "/guestbook",
            templateUrl: "views/guestbook.html",
        })
        .state('guestbook.add', {
              url: "/add",
              templateUrl: "views/guestbook.add.html",
              controller: 'GuestBookAddCtrl'
          })
        .state('guestbook.list', {
              url: "/list",
              templateUrl: "views/guestbook.list.html",
              controller: 'GuestBookListCtrl'
          })
        .state('venue', {
              url: "/venue",
              templateUrl: "views/venue.html",
              controller: 'VenueCtrl'
          });
    });
    
    ceventApp.run(function(editableOptions, $rootScope) {
    	  editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
    	  $rootScope.toggleNav = function() {
    		  $('.row-offcanvas').toggleClass('active');
    	  };
    	});