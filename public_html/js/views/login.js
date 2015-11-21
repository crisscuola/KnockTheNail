define([
    'backbone',
    'tmpl/login'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        el: '.login',
        name: 'login',
        template: tmpl,
        model: null,
        events: {
            'submit': 'onSubmit',
        },
        initialize: function () {

        },
        render: function () {
            this.$el.html(this.template);
            this.delegateEvents();
            return this;
        },
        show: function () {
            this.trigger("show", this);
        },
        hide: function () {
            this.$el.hide();
        },

        onSubmit: function(event) {
            var $loginForm = $('.login-form__input');
            if (!$loginForm[0].checkValidity() ||
                !$loginForm[1].checkValidity()) {
                    $loginForm.find('.login-form__button').click();
            } else {
                event.preventDefault();
                var data =  $(".login-form").serialize();
                this.model.save({}, {url: "/signin", data: data});
            }
            $(".login-form")[0].reset();
        }

    });

    return View;
});