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
    	getUrl: function(limit) {
    	    if (limit == 'all')
    	        return '/scores';
            else
                return '/scores?limit=' + limit;
        },
    });

    return Players;
});