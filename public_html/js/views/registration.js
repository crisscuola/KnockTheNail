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
        },
        render: function () {
            this.$el.html(this.template);
            this.$el.find(".square").css('bottom', '700px')
                .animate({bottom: 0});
            return this;
        },
        show: function () {

        },
        hide: function () {

        },

        onSubmit: function(event) {
            event.preventDefault();
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