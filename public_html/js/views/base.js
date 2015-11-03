define([
    'backbone',
    'tmpl/base',
    'collections/logged',
    'views/main'
], function(
    Backbone,
    tmpl,
    logged,
    main
){
    var View = Backbone.View.extend({
        el: '.corner',
        template: tmpl,
        model: null,
        name: 'base',
        events: {
            "click .corner__btn_logout": "logout"
        },
        initialize: function () {
            var that = this;
            this.model.on('change', that.render.bind(that));
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
            console.log("base render ");
            this.$el.html(this.template(this.model.toJSON()));
            this.delegateEvents();
            //this.check();
            return this;
        },

        logoutBtnHide: function(){
            console.log("logoutHide from base");
            this.trigger("hideLogout", this);
        },

        logoutBtnShow: function(){
            console.log("logoutShow from base");
            this.trigger("showLogout", this);
        },

        logout: function(event){
            //event.preventDefault();
            this.model.trigger('logout');
            this.render();
            //userLogged.set({logged: false});
            //this.render();
        }
    });

    return View;
});