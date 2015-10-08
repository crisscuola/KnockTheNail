define([
    'backbone'
], function(
    Backbone
){

    var Player = Backbone.Model.extend({
    	urlRoot: '/player',
    	defaults: {
    		name: '',
    		score: 0
    	},
    	initialize: function(){
    		console.log("Player has been created.");
    	},

    });

    return Player;
});