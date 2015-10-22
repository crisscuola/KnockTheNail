define([
    'backbone',
    'tmpl/registration'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        el: '.page',
        template: tmpl,
        events: {
            'submit': 'onSubmit'
        },
        initialize: function () {
        },
        render: function () {
            console.log("from register");
            this.$el.html(this.template);
            this.delegateEvents();
            //this.listenTo(this.el, "click", this.onSubmit);
            //this.$el.find(".register-form").on("submit", this.onSubmit);
//            this.$el.find(".square").css('bottom', '700px')
//                .animate({bottom: 0});
            return this;
        },
        show: function () {

        },
        hide: function () {

        },

        onSubmit: function(event) {
            console.log("from reg submit");
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