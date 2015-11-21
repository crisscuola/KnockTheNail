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
            }
        },
        parse: function(response){
            console.log(response);
            return response;
        },

        sync: function(method, model, options) {
            //console.log(method +': ' +model.url());
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
                        alert(answer.name + " " + answer.message);
                    } else if (answer.method == "signin") {
                          this.set({id: answer.id, name: answer.name, logged: true});
                          location.href = "#";
                          alert(answer.name + " " + answer.message);
                    } else if (answer.method == "check"){
                            this.set({id: answer.id, name: answer.name, logged: true});
                            location.href = "#";
                    } else {
                        alert(answer.status);
                    }
                }
            });

//            if (method == "create" && data.url == "/score") {
//                var xhr = $.ajax({
//                    type: "POST",
//                    url: "/scores",
//                    data: data,
//                    context: this
//                }).done(function(obj) {
//                    var answer = JSON.parse(obj);
//                    if (answer.success) {
//                        //this.save({'win': this.get('win') + 1}, {success:{}});
//                        console.log(answer);
//                        //this.set({name: "", logged: false});
//                    } else {
//                        alert(answer.status);
//                    }
//                });
//            } else if (method == "update" && data.url == "/score"){
//                var xhr = $.ajax({
//                    type: "PUT",
//                    url: "/scores",
//                    data: data,
//                    context: this
//                }).done(function(obj) {
//                    var answer = JSON.parse(obj);
//                    if (answer.success) {
//                        //this.save({'win': this.get('win') + 1}, {success:{}});
//                        console.log(answer);
//                        //this.set({name: "", logged: false});
//                    } else {
//                        alert(answer.status);
//                    }
//                });
//            } else if (data.url == "/logout") {
//                var data = this.get("name");
//                $.ajax({
//                    type: "POST",
//                    url: "/logout",
//                    data: {name: data},
//                    context: this
//                }).done(function(obj) {
//                    var answer = JSON.parse(obj);
//                    if (answer.success) {
//                        this.set({name: "", logged: false});
//                        alert(answer.name + " " + answer.message);
//                        location.href = "#";
//                    } else {
//                        alert(answer.name + " " + answer.message);
//                    }
//                });
//            } else if (data.url == "/signin") {
//                console.log(options.data);
//                $.ajax({
//                    type: "POST",
//                    url: "/signin",
//                    context: this,
//                    data: options.data
//                }).done(function(obj) {
//                    console.log("SERVER ANSWER : " + obj);
//                    var answer = JSON.parse(obj);
//                    if (answer.success) {
//                        //this.model.save({id: answer.id, name: answer.name, logged: true});
//                        //console.log(this.model);
//                        this.set({id: answer.id, name: answer.name, logged: true});
//                        location.href = "#";
//                        alert(answer.name + " " + answer.message);
//                    } else {
//                        alert(answer.message);
//                    }
//                });
//            }
        },

        save: function(attributes, options){
                console.log(options);
                console.log(attributes);
            return Backbone.Model.prototype.save.call(this, attributes, options);
        },

        initialize: function() {
            this.save({}, {url: "/check"});
//            $.ajax({
//                type: "POST",
//                url: "/check",
//                context: this
//            }).done(function(obj) {
//                var answer = JSON.parse(obj);
//                if (answer.success) {
//                    this.set({name: answer.name, logged: true});
//                    console.log("LOGGED");;
//                } else {
//                console.log("NOT LOGGED");
//                }
//                location.href = "#";
//            });

            this.on('logout', function() {
                //this.logout();
                this.save({url: "/logout"});
                console.log(this);
            });
        },

        logout: function() {
            var data = this.get("name");
            $.ajax({
                type: "POST",
                url: "/logout",
                data: {name: data},
                context: this
            }).done(function(obj) {
                var answer = JSON.parse(obj);
                if (answer.success) {
                    this.save({name:"", logged:false}, {success:{}});
                    console.log(this);
                    //this.set({name: "", logged: false});
                    location.href = "#";
                    alert(data + answer.message);
                } else {
                    alert(answer.message);
                }
            });
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
//            var data = this.toJSON();
//            $.ajax({
//                type: "POST",
//                url: "/scores",
//                data: data,
//                context: this
//            }).done(function(obj) {
//                var answer = JSON.parse(obj);
//                if (answer.success) {
//                    this.save({'win': this.get('win') + 1}, {success:{}});
//                    console.log(this);
//                    //this.set({name: "", logged: false});
//                    alert(data + answer.status);
//                } else {
//                    alert(answer.status);
//                }
//            });
        },
        loseGame: function(){
            //this.set({'lose': this.get('lose') + 1, url: '/scores'});
            this.save({}, {'lose': this.get('lose') + 1, url: '/scores'});
        }

    });

    return new Model();
});