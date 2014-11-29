angular.module('starter.controllers', [])

.controller('IndexCtrl', function($scope, BankService) {
  $scope.accounts = BankService.allAcc();
  $scope.cards = BankService.allCards();
})

.controller('DetailCtrl', function($scope, $stateParams, BankService) {
  $scope.account = BankService.get($stateParams.id);
})

.controller('TxCtrl', function($scope, $stateParams, BankService) {
  $scope.tx = BankService.getTx($stateParams.id);
});
