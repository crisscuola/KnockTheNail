define([
    'backbone'
], function(
    Backbone
){
    var Player = Backbone.Model.extend({
    	defaults: {
    		name: '',
    		win: 0,
    		lose: 0
    	},
    	initialize: function(){
    	},

    });

    return Player;
});