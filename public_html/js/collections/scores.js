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
            return -player.get("win");
        },
        parse: function(response) {
            console.log(response[1]);
            _.each(response, function(element, index){
                this.push({'name': index.name, 'win': index.win,'lose': index.lose});
                }, this);
            return response;
        }
    });

    return new Players();
});