define([
    'backbone',
    'tmpl/main'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        el: '.page',
        template: tmpl,
        initialize: function () {

            console.log("main view is initialized");
        },
        render: function () {
            this.$el.html(this.template);

            return this;
        },
        show: function () {
            
        },
        hide: function () {
            this.$el.find(".button_back").hide();
        }

    });

    return new View();
});