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
        collection: new players(),
        player: player,
        name: 'scoreboard',
        model: null,
        initialize: function () {
        },
        events: {
            'click #top10': function() {this.setModelScoreboard(event, 10)},
            'click #all': function() {this.setModelScoreboard(event, 'all')}
        },

        render: function () {

            this.$el.html(this.template(this.collection.toJSON()));
            this.delegateEvents();
            return this;
        },

        show: function () {
            this.getScoreboard();
            this.trigger("show", this);
        },

        hide: function () {
            this.$el.hide();
        },

        setModelScoreboard: function(event, str) {
            event.preventDefault();
            this.model.set({'scoreboardPick': str});
            this.getScoreboard();
        },

        getScoreboard: function() {
            var that = this;
            if (localStorage.scores) {
                var scores = localStorage.getItem('scores');
                this.collection.fetch({url: this.collection.getUrl(scores),
                    success: function(response){console.log('Fetched!'); that.render();},
                    error: function(response) { console.log("ERROR"); localStorage.clear(); that.render();}
                    });
            } else {
                this.collection.fetch({
                success: function(response){console.log('Fetched!'); that.render();},
                error: function(response){console.log(response);}
                });
            }
        },



    });

    return View;
});