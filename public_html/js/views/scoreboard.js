define([
    'backbone',
    'tmpl/scoreboard',
    'models/score',
    'collections/scores'
], function(
    Backbone,
    tmpl,
    player,
    players
){

    var View = Backbone.View.extend({
        el: '.scoreboard',
        template: tmpl,
        collection: players,
        player: player,
        name: 'scoreboard',
        initialize: function () {
            for (var i = 0; i < 10; i++) {
            var rand_name = Math.random().toString(36).substr(2, 5);
            var rand_score = Math.floor(Math.random()*(100));
            this.collection.push(new this.player({name: rand_name, score: rand_score}));
            }
        },
        render: function () {
            this.$el.html(this.template(this.collection.toJSON()));
            this.delegateEvents();
            return this;
        },
        show: function () {
            this.trigger("show", this);
        },
        hide: function () {
            this.$el.hide();
        }

    });

    return View;
});