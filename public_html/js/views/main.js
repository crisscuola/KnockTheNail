define([
    'backbone',
    'tmpl/main',
    'collections/logged',
    'views/base'
], function(
    Backbone,
    tmpl,
    logged,
    base
){

    var View = Backbone.View.extend({
        el: '.main',
        name: "main",
        template: tmpl,
        collection: logged,
        model: null,
        events: {
            "click .menu__item.button:lt(3)": "hide",
            "click .menu__item_logout": "logout"
        },
        initialize: function () {
            var that = this;
            this.model.on('change', that.render.bind(that));
        },
        check: function() {
            //console.log('user.logged= ' + user.get("logged"));
//            if (!userLogged.get("logged")) {
//                this.$el.find(".menu__item_login").show();
//                this.$el.find(".menu__item_logout").hide();
//            } else {
//                this.$el.find(".menu__item_login").hide();
//                this.$el.find(".menu__item_logout").show();
//            }
        },
        render: function () {
            //console.log("main user.logged: "+user.get("logged"));
            this.$el.html(this.template(this.model.toJSON()));
            this.delegateEvents();
            this.check();
            return this;
        },
        show: function () {
            this.trigger('show', this);
            console.log("user.name from main " + this.model.get('name'));
            console.log(this.model.toJSON());
            //this.render();
        },
        hide: function () {
            this.$el.hide();
        },

        logout: function() {
            this.model.trigger('logout');
            //userLogged.set({ logged: false });
            //this.render();
            //base.render();
        }
    });

    return View;
});