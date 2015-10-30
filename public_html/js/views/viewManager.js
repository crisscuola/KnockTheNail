define([
    'backbone',
    'views/main',
    'views/scoreboard',
    'views/login',
    'views/game',
    'views/registration',
    'views/base'
], function(
    Backbone,
	main,
	scoreboard,
	login,
	game,
	reg,
	base
){

    var ViewManager = Backbone.View.extend({
        views: [
                main,
                scoreboard,
                login,
                game,
                reg
        ],

        getViews: function(){
            return this.views;
        },

        currentView: null,

        initialize: function () {
            _.each(this.views, function(iterView){
                iterView.render();
                base.render();
            });
            main.on("show", this.hideExceptOne.bind(this.views));
            game.on("show", this.hideExceptOne.bind(this.views));
            scoreboard.on("show", this.hideExceptOne.bind(this.views));
            login.on("show", this.hideExceptOne.bind(this.views));
            reg.on("show", this.hideExceptOne.bind(this.views));
            base.on("show", this.hideExceptOne.bind(this.views));
        },

        hideExceptOne: function(view){
            console.log("1: " + view.name);
            _.each(this, function(iterView){
                if(iterView.name != view.name)
                    iterView.hide();
                else {
                    iterView.$el.show().find(".square").css('bottom', '900px')
                                                                    .animate({bottom: 0});
                }
            }, this);
        },
    });

    return ViewManager;
});