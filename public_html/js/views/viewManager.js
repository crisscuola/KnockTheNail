define([
    'views/main',
    'views/scoreboard',
    'views/login',
    'views/game',
    'views/registration',
    'views/base'
], function(
	main,
	scoreboard,
	login,
	game,
	reg,
	base
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
            console.log("prView: "+viewKey);

            if (this.currentView) {
                base.close();
                this.currentView.close();
            }

            $(".wrapper .container").append(view.el);
            $(".wrapper ").prepend(base.el);
            view.render();
            base.render();
            if (viewKey == "main") {
                $(".wrapper .corner").find(".corner__btn_logout").hide();
            }
            this.currentView = view;
        }
    });

    return ViewManager;
});