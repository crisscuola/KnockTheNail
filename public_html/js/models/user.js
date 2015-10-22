define([
    'backbone',
    "views/main"
], function(
    Backbone,
    main
){
    var Model = Backbone.Model.extend({

        defaults: {
            name: "",
            password: "",
            score: 0,
            logged: false
        },
        initialize: function() {
            console.log("model main:" + main);
            this.on("change:logged", function(model){
                if (!model.get("logged"))
                    this.logout();
            });
        },
        logout: function() {
            var data = userLogged.get("name");
            $.ajax({
                type: "POST",
                url: "/logout",
                data: {name: data}
            }).done(function(obj) {
                var answer = JSON.parse(obj);
                if (answer.success) {
                    userLogged.set({name: ""});
                    location.href = "#";
                    alert(data + answer.message);
                } else {
                    alert(answer.message);
                }
            });
        }

    });


    return  new Model();
});