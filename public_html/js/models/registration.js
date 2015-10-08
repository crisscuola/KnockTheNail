define([
    'backbone'
], function(
    Backbone
){

    var Model = Backbone.Model.extend({
    	initialize: function(){	
            console.log("Registration model initialized.");
    	},
        onSubmit: function() {
            var data =  $(".register-form").serialize();
            $.ajax({
                type: "POST",
                url: "/signup",
                data: data
            }).done(function(obj) {
                console.log("SERVER ANSWER : " + obj);
                var answer = JSON.parse(obj);
                if (answer.success) {
                    location.href = "#";
                    alert(answer.name +" " +answer.message);
                } else {
                    alert(answer.name + " " +answer.message);
                }
            });

	   }

    });

    return Model;
});
