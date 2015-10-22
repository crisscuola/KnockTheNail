define([
    'backbone',
    'tmpl/main',
    'models/user',
    'collections/logged',
    'views/base'
], function(
    Backbone,
    tmpl,
    user,
    logged,
    base
){

    var View = Backbone.View.extend({
        el: '.page',
        template: tmpl,
        collection: logged,
        model: user,
        events: {
            "click .menu__item.button:lt(3)": "hide",
            "click .menu__item_logout": "logout"
        },
        initialize: function () {

        },
        check: function() {
            console.log('model.logged= ' + userLogged.get("logged"));
            if (!userLogged.get("logged")) {//(this.collection.length == 0) {
                this.$el.find(".menu__item_login").show();
                this.$el.find(".menu__item_logout").hide();
            } else {
                this.$el.find(".menu__item_login").hide();
                this.$el.find(".menu__item_logout").show();
            }
        },
        render: function () {
            this.$el.html(this.template);
            this.delegateEvents();
            this.check();
            console.log('main rendered');
            return this;
        },
        show: function () {

        },
        hide: function () {
            //this.$el.find(".square").animate({bottom: '700px', height: "50%"});
        },

        logout: function() {
            userLogged.set({ logged: false });
             this.render();
             base.render();
        }
    });

    return new View();
});