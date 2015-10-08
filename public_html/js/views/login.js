define([
    'backbone',
    'tmpl/login',
    'models/login'
], function(
    Backbone,
    tmpl,
    login
){

    var View = Backbone.View.extend({
        el: '.page',
        template: tmpl,
        model: new login(),
        events: {
            'click .js-submit': 'validateForm',
            'load': 'show'            
        },
        initialize: function () {
            console.log("login view is initialized");

        },
        render: function () {
            this.$el.html(this.template);
            return this;
        },
        show: function () {
            this.$el.find(".button_back").show();
        },
        hide: function () {
            // TODO
        },
        validateForm: function(event){
        }
    });

    return new View();
});