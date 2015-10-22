define([
    'backbone',
    'tmpl/login',
    'models/user',
    'collections/logged'
], function(
    Backbone,
    tmpl,
    user,
    logged
){

    var View = Backbone.View.extend({
        el: '.page',
        template: tmpl,
        collection: logged,
        model: user,
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

        },
        hide: function () {
            //this.$el.find(".square").animate({bottom: '700px'});
        },

        onSubmit: function(event) {
            var loggedIn = this.collection;
            //var userLogged = this.model;
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
                        userLogged.set({name: answer.name, logged: true});
                        console.log('model.logged login= ' + userLogged.get("logged"));
                        location.href = "#";
                        alert(answer.name + " " + answer.message);
                    } else {
                        alert(answer.message);
                    }
                });
            }
        }

    });

    return new View();
});