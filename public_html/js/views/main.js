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
        events: {
            "click .menu__item": "hide"
        },
        initialize: function () {
        },
        render: function () {
            this.$el.html(this.template);
            this.$el.find(".square").css('bottom', '700px')
                .animate({bottom: 0});
            return this;
        },
        show: function () {
        },
        hide: function () {
            this.$el.find(".square").animate({bottom: '700px'});
        }

    });

    return new View();
});