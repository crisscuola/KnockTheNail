define([
    'backbone',
    'models/score'
], function(
    Backbone,
    player
){

    var Players = Backbone.Collection.extend({
    	model: player,
    	comparator: function(player) {
            return -player.get("score");
        }
    });

    return new Players();
});