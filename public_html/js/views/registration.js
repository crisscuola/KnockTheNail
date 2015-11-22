define([
    'backbone',
    'tmpl/registration'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        el: '.registration',
        template: tmpl,
        name: 'registration',
        events: {
            'submit': 'onSubmit'
        },
        initialize: function () {
        },
        render: function () {
            this.$el.html(this.template);
            this.delegateEvents();
            return this;
        },
        show: function () {
            this.trigger('show', this);
            //this.find(".register-form")[0].reset();
        },
        hide: function () {
            this.$el.hide();
        },

        onSubmit: function(event) {
            var $registerForm = $('.register-form__input');
            if (!$registerForm[0].checkValidity() ||
                !$registerForm[1].checkValidity()) {
                    $registerForm.find('.register-form__button').click();
            } else {
                event.preventDefault();
                var data =  $(".register-form").serialize();
                this.model.save({}, {url: "/signup", data: data})
            }
            $(".register-form")[0].reset();
        }

    });

    return View;
});