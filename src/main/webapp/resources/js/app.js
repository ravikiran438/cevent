var ceventApp = angular.module('ceventApp', ['ui.bootstrap', 'ui.router', 'xeditable']);

    ceventApp.config(function($stateProvider, $urlRouterProvider){
      
      // For any unmatched url, send to /route1
      $urlRouterProvider.otherwise("/home");
      
      $stateProvider
      .state('home', {
          url: "/home",
          templateUrl: "views/home.html"
      	})
        .state('bride', {
            url: "/bride",
            templateUrl: "views/bride.html",
            controller: 'CarouselBhanuCtrl'
        })
          
        .state('groom', {
            url: "/groom",
            templateUrl: "views/groom.html",
            controller: 'CarouselPraveenCtrl'
        })
        
        .state('family', {
            url: "/family",
            templateUrl: "views/family.html"
        })
        .state('cards', {
            url: "/cards",
            templateUrl: "views/cards.html",
            controller: 'CarouselCardsCtrl'
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
    		  $('.navbar-collapse').toggleClass('in');
    	  };
    	});