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
            this.on('logout', function() {
                this.save({url: "/logout"});
                console.log(this);
            });
        },

        requestType: function(url){
            console.log(url);
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
        parse: function(response){
            console.log(response);
            return response;
        },

        sync: function(method, model, options) {
            if (!options.data)
                options.data = this.toJSON();
            console.log(options.url);
            console.log(options.data);
            console.log(this.requestType(options.url));
            var xhr = $.ajax({
                type: this.requestType(options.url),
                url: options.url,
                data: options.data,
                context: this
            }).done(function(obj) {
                console.log("SERVER ANSWER : " + obj);
                var answer = JSON.parse(obj);
                if (answer.success) {
                    console.log(answer);
                    if (answer.method == "logout") {
                        this.set({name: "", logged: false});
                        location.href = "#";
                        notie.alert(1, answer.name + " " + answer.message, 2 );
                        //alert(answer.name + " " + answer.message);
                    } else if (answer.method == "logout") {
                        this.set({name: "", logged: false});
                        location.href = "#";
                        notie.alert(1, answer.name + " " + answer.message, 2 );
                        //alert(answer.name + " " + answer.message);
                    } else if (answer.method == "signin") {
                        this.set({id: answer.id, name: answer.name, logged: true});
                        location.href = "#";
                        notie.alert(1, answer.name + " " + answer.message, 2);
                        //alert(answer.name + " " + answer.message);
                    } else if (answer.method == "check"){
                            this.set({id: answer.id, name: answer.name, logged: true});
                            location.href = "#";
                    }
                } else {
                    if (answer.method != "check")
                    notie.alert(3, answer.name + " " + answer.message, 2.5);
                    //alert(answer.status);
                }
            });
        },

        save: function(attributes, options){
                console.log(options);
                console.log(attributes);
            return Backbone.Model.prototype.save.call(this, attributes, options);
        },

        winGame: function(){
            //this.set({'win': this.get('win') + 1});
            this.save({}, {'win': this.get('win') + 1, url: '/scores',
                success: function(model, response) {
                    console.log('SUCCESS:');
                    console.log(response);
                },
                error: function(model, response) {
                    console.log('FAIL:');
                    console.log(response);
            }});
        },
        loseGame: function(){
            this.save({}, {'lose': this.get('lose') + 1, url: '/scores'});
        }

    });

    return new Model();
});