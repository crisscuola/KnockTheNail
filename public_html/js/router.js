define([
    'backbone',
    'views/main',
    'views/scoreboard',
    'views/game',
    'views/login',
    'views/registration'
], function(
    Backbone,
    main,
    scoreboard,
    game,
    login,
    registration
){

    var Router = Backbone.Router.extend({
        routes: {
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            'registration': 'registrationAction',
            '*default': 'defaultActions'
        },
        defaultActions: function () {
            console.log("from default");
            var mainView = main;
            mainView.render();
            mainView.hide();
        },
        scoreboardAction: function () {
            console.log("from scoreboard");
            var scoreboardView =  scoreboard;
            scoreboard.render();
            scoreboard.show();
        },
        gameAction: function () {
            console.log("from game");
            var gameView = game;
            gameView.render();
            gameView.show();
        },
        loginAction: function () {
            console.log("from login");
            var loginView = login;
            loginView.render();
            loginView.show();
        },
        registrationAction: function () {
            console.log("from registration");
            var registrationView = registration;
            registrationView.render();
            registration.show();
        }
    });

    return new Router();
});