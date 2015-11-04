define([
    'backbone',
    'tmpl/login',
    'collections/logged'
], function(
    Backbone,
    tmpl,
    logged
){

    var View = Backbone.View.extend({
        el: '.login',
        name: 'login',
        template: tmpl,
        collection: logged,
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
            console.log("onSubmit");
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
                    context: this,
                    data: data
                }).done(function(obj) {
                    console.log("SERVER ANSWER : " + obj);
                    var answer = JSON.parse(obj);
                    if (answer.success) {
                        this.model.set({name: answer.name, logged: true});
                        location.href = "#";
                        alert(answer.name + " " + answer.message);
                    } else {
                        alert(answer.message);
                    }
                });
            }
        }

    });

    return View;
});