define([
    'backbone'
], function(
    Backbone
){
    var Player = Backbone.Model.extend({
    	defaults: {
    		id: null,
    		name: '',
    		wons: 0,
    		loses: 0
    	},
    	initialize: function(){
    	},

    });

    return Player;
});