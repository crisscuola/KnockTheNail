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
        url: '/check',
        defaults: {
            name: "def",
            password: "",
            score: 0,
            logged: false,
            view: base,
            isMain: null,
        },

        initialize: function() {
            console.log("model init");
            console.log("this.mainView:"+this.view);
//            this.save({}, {
//                 function(response){
//                    console.log(response);
//                    if(response.success) {
//                        this.logged = response.success;
//                        this.name = response.name;
//                        this.set({name: response.name, logged: true});
//                    }
//                    else {
//                        this.logged = false;
//                        this.name = '';
//                    }
//                    console.log(this.get("logged"));
//                }
//
//                });
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

            this.on('logout', function(){
                console.log('LOGOUT EVENT');
                this.logout();
            });
        //});
//            this.on("change:logged", function(model){
//                //this.trigger('changed');
//                console.log(model);
////                //var newModel  = new model.view();
////                if (!model.get("logged"))
////                    this.logout();
////                this.test();
//            });
        },

        test: function(){
            console.log("this.mainView:"+this.view);
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