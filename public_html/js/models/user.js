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
            name: "def",
            password: "",
            win: 0,
            lose: 0,
            logged: false,
            //view: base,
            isMain: null,
            shouldClick: true
        },

        sync: function(method, model) {
            console.log(method +': ' +model.url);
        },

        save: function(attributes, options){
                console.log(options);
                console.log("Saved?");
            return Backbone.Model.prototype.save.call(this, attributes, options);
        },

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
                this.logout();
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
            this.set({'win': this.get('win') + 1});
        },
        loseGame: function(){
            this.set({'lose': this.get('lose') + 1});
        }

    });

    return new Model();
});