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
        manager: null,

        initialize: function() {
            this.manager = new viewManager();
         },

        defaultActions: function () {
            main.show();
        },
        scoreboardAction: function () {
            scoreboard.show();
        },
        gameAction: function () {
            game.show();
        },
        loginAction: function () {
            login.show();
        },
        registrationAction: function () {
            registration.show();
        },

    });

    return new Router();
});