define([
    'backbone',
    'views/main',
    'views/scoreboard',
    'views/login',
    //'views/game',
    'views/registration',
    'views/base',
    'models/user'
], function(
    Backbone,
	main,
	scoreboard,
	login,
	//game,
	reg,
	base,
	user
){

    var ViewManager = Backbone.View.extend({
        views: [],

        userLogged: null,
        currentView: null,

        initialize: function() {
            //this.userLogged = user;
//            _.each(this.views, function(iterView){
//                 new iterView().render();
//            });
//            base.render();
//            this.views.mainView.on("show", this.hideExceptOne.bind(this.views));
//            game.on("show", this.hideExceptOne.bind(this.views));
//            scoreboard.on("show", this.hideExceptOne.bind(this.views));
//            login.on("show", this.hideExceptOne.bind(this.views));
//            reg.on("show", this.hideExceptOne.bind(this.views));
            //console.log("vm init "+this.userLogged.get("name"));
        },

        add: function(view){
            this.views.push(view);
            view.render();
        },

        hideLogout: function(view){
            view.model.set({isMain: true});
            console.log(view.model.isMain);
            //view.$el.find(".corner__btn_logout").hide();
        },

        showLogout: function(view){
            view.model.set({isMain: false});
            console.log(view.model.isMain);
            //view.$el.find(".corner__btn_logout").show();
        },

        hideExceptOne: function(view){
//            if (view.name = 'main')
//                this.baseView.$el.find(".corner__btn_logout").hide();
//            else
//                this.baseView.$el.find(".corner__btn_logout").show();
            _.each(this, function(iterView){
                if (iterView.name != 'base'){
                    if(iterView.name != view.name)
                        iterView.hide();
                    else {
                        iterView.$el.show().find(".square").css('bottom', '900px')
                            .animate({bottom: 0});
                    }
                }
            }, this);

        },
    });

    return ViewManager;
});