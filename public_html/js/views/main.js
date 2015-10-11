define([
    'backbone',
    'tmpl/main',
    'models/user',
    'collections/logged'
], function(
    Backbone,
    tmpl,
    user,
    logged
){

    var View = Backbone.View.extend({
        el: '.page',
        template: tmpl,
        collection: logged,
        events: {
            "click .menu__item.button:lt(3)": "hide",
            "click .menu__item_logout": "logout"
        },
        initialize: function () {
        },
        check: function() {
            console.log('collections.length = ' + this.collection.length);
            if (this.collection.length == 0) {
                this.$el.find(".menu__item_login").show();
                this.$el.find(".menu__item_logout").hide();
            } else {
                this.$el.find(".menu__item_login").hide();
                this.$el.find(".menu__item_logout").show();
            }
        },
        render: function () {
            this.$el.html(this.template);
            this.check();
            this.$el.find(".square").css('bottom', '700px')
                .animate({bottom: 0});
            return this;
        },
        show: function () {

        },
        hide: function () {
            this.$el.find(".square").animate({bottom: '700px'});
        },
        logout: function() {
            this.collection.remove(this.collection.at(0));
            this.render();

        }
    });

    return new View();
});