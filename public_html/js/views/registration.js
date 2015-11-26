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
            'click .button_back': function() { localStorage.setItem('regName', ''); $(".register-form").trigger('reset');}
        },
        initialize: function () {
        },
        render: function () {
            this.$el.html(this.template);
            this.delegateEvents();
            $(".register-form__input[name='name']").val(localStorage.getItem('regName'));
            return this;
        },
        saveRegName: function() {
            var data =  $(".register-form__input:first-child").val();
            localStorage.setItem('regName', data);
        },
        show: function () {
            this.trigger('show', this);
            $(".corner__btn_reg").hide();
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
                this.model.save({}, {
                    url: "/signup",
                    data: data
                })
            }
            $(".register-form")[0].reset();
        }

    });

    return View;
});