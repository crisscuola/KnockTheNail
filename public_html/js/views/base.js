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
            this.model.on('change:logged', that.render.bind(that));
        },
        render: function () {
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

        regBtnHide: function(){
            this.$el.find(".corner__btn_reg").hide();
        },

        logout: function(event){
            //this.model.trigger('logout');
            this.model.save({}, {url: "/logout"});
        }
    });

    return View;
});