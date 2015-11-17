define([
    'backbone',
    'tmpl/base',
    'views/main'
], function(
    Backbone,
    tmpl,
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
            return this;
        },

        logoutBtnHide: function(){
            this.trigger("hideLogout", this);
        },

        logoutBtnShow: function(){
            this.trigger("showLogout", this);
        },

        logout: function(event){
            this.model.trigger('logout');
        }
    });

    return View;
});