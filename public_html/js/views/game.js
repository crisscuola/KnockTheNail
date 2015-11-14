define([
    'backbone',
    'tmpl/game',
    'gameSocket'
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
        socket: new gameSocket(),
        events: {
            'click .square__reset': 'reset',
            'click .game-form .game-form__btn1': 'btn1Click',
            'click .game-form .game-form__btn2': 'btn2Click',
            'click .game-form .game-form__btn3': 'btn3Click',
            'click .button_back': 'backClick'
        },
        initialize: function () {
        },
        render: function () {
            this.$el.html(this.template(this.model.toJSON()));
            this.delegateEvents();
            return this;
        },
        show: function () {
            this.trigger("show", this);
            this.socket.onGameStart();
        },
        hide: function () {
            this.$el.hide();
        },
        reset: function() {
            $nail = this.$el.find('.square__nail').animate({ top: '110px'}, 'fast');
            this.$el.find('.square__button-group').attr("disabled", false);
        },
        btn1Click: function() {
            this.socket.sendForce(5);
        },
        btn2Click: function() {
            this.socket.sendForce(10);
        },
        btn3Click: function() {
            this.socket.sendForce(20);
        },
        backClick: function(){
            ws.close();
        }

    });

    return View;
});