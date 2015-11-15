define([
    'backbone',
    'tmpl/game',
    'gameSocket',
    'views/gameSocket'
], function(
    Backbone,
    tmpl,
    gameSocket,
    gamesocket
){

    var View = Backbone.View.extend({
        el: '.game',
        template: tmpl,
        name: 'game',
        model: null,
        socket: new gameSocket(),
        gamesocket: new gamesocket({model: this.model}),
        events: {
            'click .square__reset': 'reset',
            'click .game-form .game-form__btn1': 'btn1Click',
            'click .game-form .game-form__btn2': 'btn2Click',
            'click .game-form .game-form__btn3': 'btn3Click',
            'click .button_back': 'backClick'
        },
        initialize: function () {
//            this.gamesocket.on('message', function(){
//                        console.log('EVENTTTTTTTTT');
//                    });
            //this.gamesocket.trigger('message');
        },
        render: function () {
            this.$el.html(this.template(this.model.toJSON()));
            this.delegateEvents();
            return this;
        },
        show: function () {
            this.trigger("show", this);
            //this.socket.onGameStart();
            this.gamesocket = new gamesocket({model: this.model});
            this.gamesocket.onGameStart();

        },
        hide: function () {
            this.$el.hide();
        },
        reset: function() {
            $nail = this.$el.find('.square__nail').animate({ top: '110px'}, 'fast');
            this.$el.find('.square__button-group').attr("disabled", false);
        },
        btn1Click: function() {
            this.gamesocket.sendForce(5, this.model.get('name'));
        },
        btn2Click: function() {
            this.gamesocket.sendForce(10);
        },
        btn3Click: function() {
            this.gamesocket.sendForce(20);
        },
        backClick: function(){
            this.gamesocket.ws.close();
        }

    });

    return View;
});