require.config({
    urlArgs: "_=" + (new Date()).getTime(),
    baseUrl: "js",
    waitSeconds: 20,
    paths: {
        jquery: "lib/jquery",
        underscore: "lib/underscore",
        backbone: "lib/backbone",
        notie: "lib/notie",
        bootstrap: "lib/bootstrap.min"
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
        },
        'bootstrap': {
            deps: ['jquery']
         }
    }
});

define([
    'backbone',
    'router',
    'notie',
    'jquery',
    'bootstrap'
], function(
    Backbone
){
    Backbone.View.prototype.close = function(){
      this.remove();
    }

    Backbone.history.start();
});
