define([
    'backbone',
    'views/main',
    'views/scoreboard',
    'views/game',
    'views/login',
    'views/registration',
    'views/viewManager',
    'views/base',
    'views/gameSocket',
    'models/user'
], function(
    Backbone,
    main,
    scoreboard,
    game,
    login,
    registration,
    viewManager,
    base,
    socket,
    user
){

    var mainView = new main({model: user});
    var gameView = new game({model: user});
    var loginView = new login({model: user});
    var registrationView = new registration({model: user});
    var scoreboardView = new scoreboard({model: user});
    var baseView = new base({model: user});

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
            this.manager.add(mainView);
            this.manager.add(gameView);
            this.manager.add(loginView);
            this.manager.add(registrationView);
            this.manager.add(scoreboardView);
            this.manager.add(baseView);
            mainView.on("show", this.manager.hideExceptOne.bind(this.manager.views));
            gameView.on("show", this.manager.hideExceptOne.bind(this.manager.views));
            loginView.on("show", this.manager.hideExceptOne.bind(this.manager.views));
            registrationView.on("show", this.manager.hideExceptOne.bind(this.manager.views));
            scoreboardView.on("show", this.manager.hideExceptOne.bind(this.manager.views));
            baseView.on("hideLogout", this.manager.hideLogout);
            baseView.on("showLogout", this.manager.showLogout);

         },

        defaultActions: function () {
            mainView.show();
        },
        scoreboardAction: function () {
            scoreboardView.show();
        },
        gameAction: function () {
            gameView.show();
        },
        loginAction: function () {
            loginView.show();
        },
        registrationAction: function () {
            registrationView.show();
        },

    });

    return new Router();
});