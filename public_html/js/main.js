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
    'views/main',
    'views/base'
], function(
    Backbone,
    router,
    user,
    logged,
    main,
    base
){
    Backbone.View.prototype.close = function(){
      this.remove();
    }
    var userLogged = new user();
    var tmp = userLogged.save({}, {
    success: function(userLogged,response){
        if (response.success){
            userLogged.logged = true;
            userLogged.name = response.name;
        }
        else
          userLogged.logged = false;
    }
    });
    console.log(userLogged)
    console.log(tmp);
//    var loggedIn = logged;
//    userLogged =  user;
//    $.ajax({
//        type: "POST",
//        url: "/check"
//    }).done(function(obj) {
//        var answer = JSON.parse(obj);
//        if (answer.success) {
//            userLogged.set({name: answer.name, logged: true});
//            console.log("LOGGED");
//        } else {
//        console.log("NOT LOGGED");
//        }
//        var mainView = main.render();
//        var baseView = base.render();
//        location.href = "#";
//    });
    Backbone.history.start();
});
