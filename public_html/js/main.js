require.config({
    urlArgs: "_=" + (new Date()).getTime(),
    baseUrl: "js",
    paths: {
        jquery: "lib/jquery",
        underscore: "lib/underscore",
        backbone: "lib/backbone",
        cookie: "lib/jquery.cookie",
        test: "lib/test"
    },
    shim: {
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'underscore': {
            exports: '_'
        },
        'cookie': {     //<-- cookie depends on Jquery and exports nothing
            deps: ['jquery']
        }
    }
});

define([
    'backbone',
    'router',
    'models/user',
    'views/main',
    'views/base',
    'cookie'
], function(
    Backbone,
    router,
    user,
    main,
    base,
    cookie
){
    Backbone.View.prototype.close = function(){
      this.remove();
    }
    //console.log(Cookies);
    //var userLogged = new user();
//    user.save({}, {
//    success: function(userLogged,response){
//        console.log(response);
//        if(response.success) {
//            user.logged = true;
//            user.name = response.name;
//        }
//        else
//            user.logged = false;
//    }
//    });
    //base.render();
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
