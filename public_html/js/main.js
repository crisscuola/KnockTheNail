require.config({
    urlArgs: "_=" + (new Date()).getTime(),
    baseUrl: "js",
    paths: {
        jquery: "lib/jquery",
        underscore: "lib/underscore",
        backbone: "lib/backbone"
    },
    shim: {
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'underscore': {
            exports: '_'
        }
    }
});

define([
    'backbone',
    'router',
    'models/user',
    'collections/logged',
    'views/main'
], function(
    Backbone,
    router,
    user,
    logged,
    main
){
    var loggedIn = logged;
    var userLogged =  new user();
    $.ajax({
        type: "POST",
        url: "/check"
    }).done(function(obj) {
        console.log("SERVER ANSWER : " + obj);
        var answer = JSON.parse(obj);
        if (answer.success) {
            userLogged.name = answer.name;
            userLogged.logged = true;
            loggedIn.push(userLogged);
            console.log("LOGGED");
        } else {
        console.log("NELOGGED");
        }
        var mainView = main.render();
    });
    Backbone.history.start();
});
