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
//            _.each(response, function(element, index){
//                this.push(new this.model({name: element.name, win: element.win, lose: element.lose}));
//                }, this);
                console.log(response);
            return response;
        }
    });

    return Players;
});