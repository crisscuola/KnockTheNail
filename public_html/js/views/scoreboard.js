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
        el: '.page',
        template: tmpl,
        collection: players,
        model: player,
        initialize: function () {
            for (var i = 0; i < 10; i++) {
            var rand_name = Math.random().toString(36).substr(2, 5);
            var rand_score = Math.floor(Math.random()*(100));
            this.collection.push(new this.model({name: rand_name, score: rand_score}));
            }
            this.collection.sort('score');
        },
        render: function () {
            this.$el.html(this.template(this.collection.toJSON()));
            this.delegateEvents();
            this.$el.find(".square").css('bottom', '700px')
                .animate({bottom: 0});
            return this;
        },
        show: function () {

        },
        hide: function () {

        }

    });

    return new View();
});