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
            main.render();
        },
        scoreboardAction: function () {
            scoreboard.render();
        },
        gameAction: function () {
            game.render();
        },
        loginAction: function () {
            login.render();
        },
        registrationAction: function () {
            registration.render();
        },


    });

    return new Router();
});