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

        VM: null,

        initialize: function() {
            this.VM = new viewManager();
         },

        showView: function(viewKey) {
            console.log('showView key: ' + viewKey)
            this.VM.presentView(viewKey);
        },

        defaultActions: function () {
            this.showView(this.VM.mainView);
        },
        scoreboardAction: function () {
            this.showView(this.VM.scoreboardView);
        },
        gameAction: function () {
            this.showView(this.VM.gameView);
        },
        loginAction: function () {
            this.showView(this.VM.loginView);
        },
        registrationAction: function () {
            this.showView(this.VM.regView);
        },

    });

    return new Router();
});