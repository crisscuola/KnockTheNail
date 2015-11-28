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
            'submit': 'onSubmit',
            'keyup .register-form__input': 'saveRegName',
            'click .button_back': 'clearStorage'
        },
        initialize: function () {
        },
        render: function () {
            this.$el.html(this.template);
            this.delegateEvents();
            this.$el.find(".register-form__input[name='name']").val(localStorage.getItem('regName'));
            return this;
        },
        saveRegName: function() {
            var data =  this.$el.find(".register-form__input:first-child").val();
            localStorage.setItem('regName', data);
        },
        show: function () {
            this.trigger('show', this);

        },
        hide: function () {
            this.$el.hide();
        },
        clearStorage: function() {
            localStorage.setItem('regName', '');
            this.$el.find(".register-form").trigger('reset');
        },

        onSubmit: function(event) {
            var $registerForm = this.$el.find('.register-form__input');
            if (!$registerForm[0].checkValidity() ||
                !$registerForm[1].checkValidity()) {
                    $registerForm.find('.register-form__button').click();
            } else {
                event.preventDefault();
                var data =  this.$el.find(".register-form").serialize();
                this.model.save({}, {
                    url: "/signup",
                    data: data
                })
            }
            this.$el.find(".register-form")[0].reset();
        }

    });

    return View;
});