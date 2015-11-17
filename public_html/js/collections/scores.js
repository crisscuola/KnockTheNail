define([
    'backbone',
    'models/score'
], function(
    Backbone,
    player
){

    var Players = Backbone.Collection.extend({
    	model: player,
    	url: '/scores',
    	comparator: function(player) {
            return -player.get("score");
        },
        parse: function(response) {
            console.log(response.name);
            return response;
        }
    });

    return new Players();
});