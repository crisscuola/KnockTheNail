define([
    'backbone',
    'views/main',
    'views/scoreboard',
    'views/login',
    'views/game',
    'views/registration',
    'views/base',
    'models/user'
], function(
    Backbone,
	main,
	scoreboard,
	login,
	game,
	reg,
	base,
	user
){

    var ViewManager = Backbone.View.extend({
        views: [],

        userLogged: null,
        currentView: null,

        initialize: function() {
        },

        add: function(view){
            this.views.push(view);
            view.render();
        },

        hideLogout: function(view){
            view.model.set({isMain: true});
        },

        showLogout: function(view){
            view.model.set({isMain: false});
        },

        hideExceptOne: function(view){
            console.log("hide");
            _.each(this, function(iterView){
                if (iterView.name != 'base'){
                    if(iterView.name != view.name)
                        iterView.hide();
                    else {
                        iterView.$el.delay(20).show().find(".square").css('bottom', '900px')
                            .animate({bottom: 0});
                    }
                }
            }, this);

        },
    });

    return ViewManager;
});