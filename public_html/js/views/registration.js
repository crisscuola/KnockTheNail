define([
    'backbone',
    'tmpl/registration',
    'models/registration'
], function(
    Backbone,
    tmpl,
    registration
){

    var View = Backbone.View.extend({
        el: '.page',
        template: tmpl,
        model: new registration(),
        events: {
            'submit': 'onSubmit'
        },
        initialize: function () {
            new registration();
            console.log("registration view is initialized");
        },
        render: function () {
            this.$el.html(this.template);
            return this;
        },
        show: function () {
            this.$el.find(".button_back").show();
        },
        hide: function () {
            // TODO
        },
        onSubmit: function(event) {
                var $registerForm = $('.register-form__input');
                if (!$registerForm[0].checkValidity() ||
                 !$registerForm[1].checkValidity()) {
                    $registerForm.find('.button_reg').click();
                } else {
                    event.preventDefault();
                this.model.onSubmit();
                }
        }

    });

    return new View();
});