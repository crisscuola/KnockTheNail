define([
    'backbone',
    'tmpl/game'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        el: '.page',
        template: tmpl,
        initialize: function () {
            console.log("game view is initialized");
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
        }

    });

    return new View();
});