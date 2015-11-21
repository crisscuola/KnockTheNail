define([
    'backbone',
    'models/score'
], function(
    Backbone,
    score
){

    var Players = Backbone.Collection.extend({
    	model: score,
    	url: '/scores',
//    	comparator: function(score) {
//            return -score.get("win");
//        },
    });

    return Players;
});