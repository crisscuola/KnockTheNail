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
            view.render();
        },

        showLogout: function(view){
            view.model.set({isMain: false});
            view.render();
        },

        hideExceptOne: function(view){
            if (view.name == 'game'){
                if (view.model.get('logged') == true) {
                    view.startGameSocket();
                    view.render();
                } else {
                    notie.alert(3, 'Please log in to play.', 2);
                    return;
                }
            } else if ((view.model.get("logged") == true) && (view.name == "login" || view.name == "registration")) {
                return;
            } else if (view.name == "main") {
                this[5].logoutBtnHide();
            } else {
                this[5].logoutBtnShow();
            }
            _.each(this, function(iterView){
                if (iterView.name != 'base'){
                    if(iterView.name != view.name)
                        iterView.hide();
                    else {
                        iterView.$el.delay(200).show().find(".square").css('bottom', '900px')
                            .animate({bottom: 0});
                    }
                }
            }, this);

        },
    });

    return ViewManager;
});