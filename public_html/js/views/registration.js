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
                $.ajax({
                    type: "POST",
                    url: "/signup",
                    data: data
                }).done(function(obj) {
                    console.log("SERVER ANSWER : " + obj);
                    var answer = JSON.parse(obj);
                    if (answer.success) {
                        location.href = "#";
                        alert(answer.name +" " +answer.message);
                    } else {
                        alert(answer.name + " " +answer.message);
                    }
                });
            }
        }

    });

    return new View();
});