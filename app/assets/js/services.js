angular.module('starter.services', [])
.factory('BankService', function() {

    'use strict';

      // Some fake testing data
      var accounts = [
        { id: 0, title: 'Demo účet', balance: 987.6, nbr: 2600000000003, iban: 'SK241100000000', description: 'Furry little creatures.',
        tx: [
            {type:'d', note:'Prevod/internet', account: 'SK8311000000002600000000008', process: '2014-11-20', book: '2014-11-20', amount: 9.7},
            {type:'c', note:'Prevod/internet', account: 'SK8311000000002600000000008', process: '2014-11-22', book: '2014-11-22', amount: 9123.7},
            {type:'d', note:'Prevod/internet', account: 'SK8311000000002600000000008', process: '2014-11-23', book: '2014-11-23', amount: 9324.7}
            ]
        },
          { id: 1, title: 'Sporiaci systém', balance: 1612.3, nbr: 50000000003, iban: 'SK241100000000', description: 'Lovable. Loyal.',
        tx: [
            {type:'d', note:'Prevod/internet', account: 'SK8311000000002600000000008', process: '2014-11-20', book: '2014-11-20', amount: 9.7},
            {type:'c', note:'Prevod/internet', account: 'SK8311000000002600000000008', process: '2014-11-22', book: '2014-11-22', amount: 9123.7},
            {type:'d', note:'Prevod/internet', account: 'SK8311000000002600000000008', process: '2014-11-23', book: '2014-11-23', amount: 9324.7}
        ]}
      ];

      var cards = [
        { id: 0, title: 'Hlavná kreditná karta', type: 'credit', description: 'Furry little creatures. Obsessed with plotting assassination, but never following through on it.' },
        { id: 1, title: 'Doplnková kreditná karta', type: 'credit', description: 'Furry little creatures. Obsessed with plotting assassination, but never following through on it.' },
        { id: 2, title: 'Debetná karta', type: 'debet', description: 'Furry little creatures. Obsessed with plotting assassination, but never following through on it.' }
      ];

      return {
        allAcc: function() {
          return accounts;
        },
        allCards: function() {
          return cards;
        },
        get: function(id) {
          // Simple index lookup
          return accounts[id];
        },
        getTx: function(id) {
          // Simple index lookup
          return accounts[id].tx;
        }
      };
    })
;
