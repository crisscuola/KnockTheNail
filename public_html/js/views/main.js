define([
    'backbone',
    'tmpl/main',
    'views/base'
], function(
    Backbone,
    tmpl,
    base
){

    var View = Backbone.View.extend({
        el: '.main',
        name: "main",
        template: tmpl,
        model: null,
        events: {
            "click .menu__item:first-child": "game",
            "click .menu__item_logout": "logout"
        },
        initialize: function () {
            var that = this;
            this.model.on('change:logged', that.render.bind(that));
        },
        render: function () {
            this.$el.html(this.template(this.model.toJSON()));
            this.delegateEvents();
            return this;
        },
        show: function () {
            this.trigger('show', this);
        },
        hide: function () {
            this.$el.hide();
        },
        logout: function() {
            this.model.save({},{url: "/logout"});
        },
        game: function(event){
            if(!this.model.get("logged")){
                event.preventDefault();
                notie.alert(3, "Please log in to play!", 2.5);
            }
        }
    });

    return View;
});