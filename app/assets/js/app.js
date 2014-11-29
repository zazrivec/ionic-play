angular.module('starter', ['ionic', 'starter.services', 'starter.controllers', 'starter.filters'])

.run(function($ionicPlatform) {
  'use strict';
  $ionicPlatform.ready(function() {
    if(window.StatusBar) {
      StatusBar.hide();
    }
  });
})

.config(function($stateProvider, $urlRouterProvider) {
  'use strict';
  $stateProvider

    // setup an abstract state for the tabs directive
    .state('app', {
      url: '/app',
      abstract: true,
      templateUrl: '/assets/templates/tabs.html'
    })

    // the pet tab has its own child nav-view and history
    .state('app.index', {
      url: '/home',
      views: {
        'home-tab': {
          templateUrl: '/assets/templates/home.html',
          controller: 'IndexCtrl'
        }
      }
    })

    .state('app.account', {
      url: '/account/:id',
      views: {
        'accounts': {
          templateUrl: '/assets/templates/detail.html',
          controller: 'DetailCtrl'
        }
      }
    })

    .state('app.account-trans', {
      url: '/account/:id/trans',
      views: {
        'accounts': {
          templateUrl: '/assets/templates/tx-index.html',
          controller: 'TxCtrl'
        }
      }
    })

    .state('app.card-detail', {
      url: '/card/:id',
      views: {
        'accounts': {
          templateUrl: '/assets/templates/detail.html',
          controller: 'DetailCtrl'
        }
      }
    })

    .state('app.payment', {
      url: '/payment',
      views: {
        'payment-tab': {
          templateUrl: '/assets/templates/payment.html',
          controller: 'IndexCtrl'
        }
      }
    })

    .state('app.about', {
      url: '/about',
      views: {
        'about-tab': {
          templateUrl: '/assets/templates/about.html'
        }
      }
    });

  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/app/home');

});

