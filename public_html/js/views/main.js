define([
    'backbone',
    'tmpl/main',
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
        events: {
            "click .menu__item.button:lt(3)": "hide",
            "click .menu__item_logout": "logout"
        },
        initialize: function () {
            console.log('main view initialized')
        },
        check: function() {
            console.log('collections.length = ' + this.collection.length);
            if (this.collection.length == 0) {
                this.$el.find(".menu__item_login").show();
                this.$el.find(".menu__item_logout").hide();
                this.$el.find(".corner__username").text("");
            } else {
                this.$el.find(".menu__item_login").hide();
                this.$el.find(".menu__item_logout").show();
                this.$el.find(".corner__username").text(this.collection.at(0).name);
            }
        },
        render: function () {
            this.$el.html(this.template(this.collection.toJSON()));
            this.check();
//            this.$el.find(".square").css('bottom', '700px')
//                .animate({bottom: 0});
//          return this;
        },
        show: function () {

        },
        hide: function () {
            this.$el.find(".square").animate({bottom: '700px', height: "50%"});
        },
        logout: function() {
            var data = this.collection.at(0).name;
            console.log("logout clicked");
            this.collection.remove(this.collection.at(0));
            $.ajax({
                type: "POST",
                url: "/logout",
                data: {name: data}
            }).done(function(obj) {
                console.log("SERVER ANSWER : " + obj);
                var answer = JSON.parse(obj);
                if (answer.success) {
                    location.href = "#";
                    alert(data + answer.message);
                } else {
                    alert(answer.message);
                }
            });
            this.render();
        }
    });

    return new View();
});