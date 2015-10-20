define([
    'views/main',
    'views/scoreboard',
    'views/login',
    'views/game',
    'views/registration'
], function(
	main,
	scoreboard,
	login,
	game,
	reg
){

    var ViewManager = Backbone.View.extend({

        currentView: null,

        gameView: "game",
        loginView: "login",
        mainView: "main",
        scoreboardView: "scoreboard",
        regView: "registration",

        views: {
            gameView: null,
            loginView: null,
            mainView: null,
            scoreboardView: null,
            regView: null,
        }, 

        initialize: function () {
            this.views[this.gameView] =  game;
            this.views[this.loginView] = login;
            this.views[this.mainView] = main;
            this.views[this.scoreboardView] = scoreboard;
            this.views[this.regView] = reg;
        },

        presentView: function(viewKey) {
            var view = this.views[viewKey];
            console.log("prView: "+view);

            if (this.currentView) {
                this.currentView.close();
            }

            $(".wrapper .container").append(view.el);
            view.render();
            this.currentView = view;
        }
    });

    return ViewManager;
});