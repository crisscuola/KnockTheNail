define([
    'backbone',
    "views/main",
    'views/base'
], function(
    Backbone,
    main,
    base
){
    var Model = Backbone.Model.extend({
        url: function() { return ("/scores/" + this.id) },
        defaults: {
            id: null,
            name: "",
            password: "",
            win: 0,
            lose: 0,
            logged: false,
            isMain: null,
            shouldClick: true
        },

        initialize: function() {
            this.save({}, {url: "/check"});
            this.on('logout', function() { this.save({url: "/logout" });
            });
        },

        requestType: function(url){
            switch (url) {
                case "/scores":
                    return "POST"
                case "/score/"+/.+/:
                    return "PUT"
                case "/signin":
                    return "POST"
                case "/logout":
                    return "POST"
                case "/check":
                    return "POST"
                case "/signup":
                    return "POST"
            }
        },

        sync: function(method, model, options) {
            if (!options.data)
                options.data = this.toJSON();
            var xhr = $.ajax({
                type: this.requestType(options.url),
                url: options.url,
                data: options.data,
                context: this
            }).done(function(obj) {
                console.log("SERVER ANSWER : " + obj);
                var answer = JSON.parse(obj);
                if (answer.success) {
                    if (answer.method == "check") {
                        this.set({id: answer.id, name: answer.name, logged: true});
                        return;
                    } else if (answer.method == "logout") {
                        this.set({name: "", logged: false});
                    } else if (answer.method == "logout") {
                        this.set({name: "", logged: false});
                    } else if (answer.method == "signin") {
                        this.set({id: answer.id, name: answer.name, logged: true});
                    }
                    notie.alert(1, answer.name + " " + answer.message, 2);
                    location.href = "#";
                } else {
                    if (answer.method != "check" && answer.method != "signin" && answer.method != "scores")
                        notie.alert(3, answer.name + " " + answer.message, 2.5);
                    else if (answer.method == "signin")
                        notie.alert(3, answer.message, 2.5);
                }
            });
        },

        save: function(attributes, options){
            return Backbone.Model.prototype.save.call(this, attributes, options);
        },

        winGame: function(){
            this.save({}, {'win': this.get('win') + 1, url: '/scores'});
        },
        loseGame: function(){
            this.save({}, {'lose': this.get('lose') + 1, url: '/scores'});
        }

    });

    return new Model();
});