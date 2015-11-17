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
            if (view.name != 'base' && view.name != 'main')
                view.hide();
        },

        hideLogout: function(view){
            view.model.set({isMain: true});
        },

        showLogout: function(view){
            view.model.set({isMain: false});
        },

        hideExceptOne: function(view){
            if (view.name == 'game'){
                if (view.model.get('logged') == true)
                    view.trigger('allowedToPlay', view)
                else {
                    alert('Please log in to play.');
                    return;
                }
            }
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