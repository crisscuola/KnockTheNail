define([
    'backbone',
    'views/main',
    'views/scoreboard',
    'views/game',
    'views/login',
    'views/registration',
    'views/viewManager'
], function(
    Backbone,
    main,
    scoreboard,
    game,
    login,
    registration,
    viewManager
){

    var Router = Backbone.Router.extend({
        routes: {
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            'registration': 'registrationAction',
            '*default': 'defaultActions'
        },

//        initialize: function(options){
//            this.appView = viewManager.appView;
//         },

        showView: function (view) {
            if (this.currentView) {
                this.currentView.hide();
                this.currentView.close();
            }

            this.currentView = view;
            $(".wrapper .container").append(this.currentView.el);
            this.currentView.render();
        },
        defaultActions: function () {
            this.showView(main);
            //main.render();
        },
        scoreboardAction: function () {
            this.showView(scoreboard);
            //scoreboard.render();
        },
        gameAction: function () {
            this.showView(game);
            //game.render();
        },
        loginAction: function () {
            this.showView(login);
            //login.render();
        },
        registrationAction: function () {
            this.showView(registration);
            //registration.render();
        },

    });

    return new Router();
});