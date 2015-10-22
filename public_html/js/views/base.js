define([
    'backbone',
    'tmpl/base',
    'models/user',
    'collections/logged',
    'views/main'
], function(
    Backbone,
    tmpl,
    user,
    logged,
    main
){

    var View = Backbone.View.extend({
        el: '.corner',
        template: tmpl,
        collection: logged,
        model: user,
        events: {
            "click .corner__btn_logout": "logout"
        },
        initialize: function () {
            //console.log("view: " + main);
        },
        check: function() {
            if (!userLogged.get("logged")) {
            //if (this.collection.length == 0) {
                this.$el.find(".corner__btn_reg").show();
                this.$el.find(".corner__btn_logout").hide();
                this.$el.find(".corner__username").hide();
            } else {
                this.$el.find(".corner__btn_reg").hide();
                this.$el.find(".corner__btn_logout").show();
                this.$el.find(".corner__username").show();
                this.$el.find(".corner__username").text("You are logged as " + userLogged.get("name"));
            }
        },
        render: function () {
            this.$el.html(this.template);
            this.delegateEvents();
            this.check();
            return this;
        },
        show: function () {

        },
        hide: function () {
            //this.$el.find(".square").animate({bottom: '700px', height: "50%"});
        },

        logout: function(event){
            event.preventDefault();
            userLogged.set({logged: false});
            this.render();
        }
    });

    return new View();
});