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
        url: '/scores',
        defaults: {
            id: null,
            name: "def",
            password: "",
            win: 0,
            lose: 0,
            logged: false,
            //view: base,
            isMain: null,
            shouldClick: true
        },

        parse: function(response){
            console.log(response);
        },

        sync: function(method, model,options) {
            console.log(method +': ' +model.url);
            var data = this.toJSON();
            console.log(data.url);

            if (method == "create" && data.url == "/score") {
                var xhr = $.ajax({
                    type: "POST",
                    url: "/scores",
                    data: data,
                    context: this
                }).done(function(obj) {
                    var answer = JSON.parse(obj);
                    if (answer.success) {
                        //this.save({'win': this.get('win') + 1}, {success:{}});
                        console.log(answer);
                        //this.set({name: "", logged: false});
                    } else {
                        alert(answer.status);
                    }
                });
            } else if (method == "update" && data.url == "/score"){
                var xhr = $.ajax({
                    type: "PUT",
                    url: "/scores",
                    data: data,
                    context: this
                }).done(function(obj) {
                    var answer = JSON.parse(obj);
                    if (answer.success) {
                        //this.save({'win': this.get('win') + 1}, {success:{}});
                        console.log(answer);
                        //this.set({name: "", logged: false});
                    } else {
                        alert(answer.status);
                    }
                });
            } else if (data.url == "/logout") {
                console.log("from /logout sync");
                var data = this.get("name");
                $.ajax({
                    type: "POST",
                    url: "/scores",
                    data: {name: data},
                    context: this
                }).done(function(obj) {
                    var answer = JSON.parse(obj);
                    if (answer.success) {
                        //this.save({name:"", logged:false}, {success:{}});
                        console.log(answer);
                        //this.set({name: "", logged: false});
                        location.href = "#";
                    } else {
                        alert(answer.message);
                    }
                });
            }
        },

//        save: function(attributes, options){
//                console.log("Saved?");
//            return Backbone.Model.prototype.save.call(this, attributes, options);
//        },

        initialize: function() {
            //this.save({},{success:function(model,response){console.log(response);}, error:function(model,response){console.log(response);}});
//            this.on("change:win", function(){console.log('Win changed?')});
//            this.on("change:lose", this.save());
            $.ajax({
                type: "POST",
                url: "/check",
                context: this
            }).done(function(obj) {
                var answer = JSON.parse(obj);
                if (answer.success) {
                    this.set({name: answer.name, logged: true});
                    console.log("LOGGED");;
                } else {
                console.log("NOT LOGGED");
                }
                location.href = "#";
            });

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
            this.save({'win': this.get('win') + 1, url: '/score'}, {
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
            this.save({'lose': this.get('lose') + 1, url: '/scores'});
        }

    });

    return new Model();
});