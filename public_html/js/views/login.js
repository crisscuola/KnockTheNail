define([
    'backbone',
    'tmpl/login',
    'models/login'
], function(
    Backbone,
    tmpl,
    login
){

    var View = Backbone.View.extend({
        el: '.page',
        template: tmpl,
        model: login,
        events: {
            'click .login-form__button': 'onSubmit',
        },
        initialize: function () {

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
            this.$el.find(".square").animate({bottom: '700px'});
        },

        onSubmit: function(event) {
            event.preventDefault();
            var $loginForm = $('.login-form__input');
            if (!$loginForm[0].checkValidity() ||
             !$loginForm[1].checkValidity()) {
                $loginForm.find('.login-form__button').click();
            } else {
                event.preventDefault();
                var data =  $(".login-form").serialize();
                $.ajax({
                    type: "POST",
                    url: "/signin",
                    data: data
                }).done(function(obj) {
                    console.log("SERVER ANSWER : " + obj);
                    var answer = JSON.parse(obj);
                    if (answer.success) {
                        var usr = new user();
                        usr.name = answer.name;
                        usr.logged = true;
                        location.href = "#";
                        alert(answer.name +" " +answer.message);
                    } else {
                        alert(answer.message);
                    }
                });
            }
        }

    });

    return new View();
});