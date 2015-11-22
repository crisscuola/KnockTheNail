require.config({
    urlArgs: "_=" + (new Date()).getTime(),
    baseUrl: "js",
    paths: {
        jquery: "lib/jquery",
        underscore: "lib/underscore",
        backbone: "lib/backbone",
        notie: "lib/notie"
    },
    shim: {
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'underscore': {
            exports: '_'
        },
        'notie': {
            exports: 'notie'
        }
    }
});

define([
    'backbone',
    'router',
    'models/user',
    'views/main',
    'views/base',
    'notie'
], function(
    Backbone,
    router,
    user,
    main,
    base,
    notie
){
    Backbone.View.prototype.close = function(){
      this.remove();
    }
    console.log(notie);


    Backbone.history.start();
});
