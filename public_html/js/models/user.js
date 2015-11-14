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
        url: '/logout',
        logoutUrl: '/logout',
        defaults: {
            name: "def",
            password: "",
            score: 0,
            logged: false,
            view: base,
            isMain: null,
            shouldClick: true
        },

        sync: function(method, model) {
            console.log(method +': ' +model.url);
        },

        save: function(attributes, options){
                this.set(attributes);
            return Backbone.Model.prototype.save.call(this, attributes, options);
        },

        initialize: function() {
            //this.save({},{success:function(model,response){console.log(response);}, error:function(model,response){console.log(response);}});
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
                //this.save({url: '/logout', logged: false}, {success: function(){alert(response);}});
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
                    this.set({name: "", logged: false});
                    location.href = "#";
                    alert(data + answer.message);
                } else {
                    alert(answer.message);
                }
            });
        }

    });

    return new Model();
});