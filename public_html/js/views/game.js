define([
    'backbone',
    'tmpl/game',
    'views/gameSocket'
], function(
    Backbone,
    tmpl,
    gameSocket
){

    var View = Backbone.View.extend({
        el: '.game',
        template: tmpl,
        name: 'game',
        model: null,
        gameSocket: new gameSocket({model: this.model}),
        events: {
            'click .game-form .game-form__btn1': 'btn1Click',
            'click .game-form .game-form__btn2': 'btn2Click',
            'click .game-form .game-form__btn3': 'btn3Click',
            'click .button_back': 'backClick'
        },
        initialize: function () {
            this.on('allowedToPlay', this.startGameSocket);
        },
        render: function () {
            this.$el.html(this.template(this.model.toJSON()));
            this.delegateEvents();
            return this;
        },
        show: function () {
            this.trigger("show", this);
        },
        hide: function () {
            this.$el.hide();
        },
        startGameSocket: function() {
            this.gameSocket = new gameSocket({model: this.model});
            this.gameSocket.onGameStart();
        },
        btn1Click: function() {
            this.gameSocket.sendForce(5, this.model.get('name'));
        },
        btn2Click: function() {
            this.gameSocket.sendForce(10);
        },
        btn3Click: function() {
            this.gameSocket.sendForce(20);
        },
        backClick: function(){
            this.gameSocket.ws.close();
        }

    });

    return View;
});