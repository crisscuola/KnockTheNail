define([
    'backbone',
    'models/user'
], function(
    Backbone,
    user
){

    var LoggedUsers = Backbone.Collection.extend({
    	model: user
    });

    return new LoggedUsers();
});