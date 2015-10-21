define([
    'backbone',
    'tmpl/base',
    'models/user',
    'collections/logged',
    'views/main'
], function(
    Backbone,
    tmpl,
    user,
    logged,
    main
){

    var View = Backbone.View.extend({
        el: '.corner',
        template: tmpl,
        collection: logged,
        events: {
            "click .corner__btn_logout": function(event){ event.preventDefault(); main.logout();}
        },
        initialize: function () {
            console.log('base view initialized')
        },
        check: function() {
            if (this.collection.length == 0) {
                this.$el.find(".corner__btn_reg").show();
                this.$el.find(".corner__btn_logout").hide();
                this.$el.find(".corner__username").hide();
            } else {
                this.$el.find(".corner__btn_reg").hide();
                this.$el.find(".corner__btn_logout").show()
                this.$el.find(".corner__username").show();
                this.$el.find(".corner__username").text("You are logged as " + this.collection.at(0).name);
            }
        },
        render: function () {
            this.$el.html(this.template(this.collection.toJSON()));
            this.delegateEvents();
            this.check();
//            this.$el.find(".square").css('bottom', '700px')
//                .animate({bottom: 0});
            return this;
        },
        show: function () {

        },
        hide: function () {
            //this.$el.find(".square").animate({bottom: '700px', height: "50%"});
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