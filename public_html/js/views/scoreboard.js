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
            if (!localStorage.top10 || localStorage.top10.length == 0) {
                for (var i = 0; i < 10; i++) {
                    var rand_name = Math.random().toString(36).substr(2, 5);
                    var rand_score = Math.floor(Math.random()*(100));
                    this.collection.push(new this.player({name: rand_name, score: rand_score}));
                }
                this.collection.sort('score');
            } else {
                var top10 = JSON.parse(localStorage['top10']);
                _.each(top10, function(element){
                    this.collection.push(new this.player({name: element.name, score: element.score}));
                }, this);
            }

            this.on('hide', this.saveLocalStorage);
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
            this.trigger('hide');
            this.collection.sort();
            _.each(this.collection, function(element, index){
                    this.collection.remove(element);
            }, this);
        },
        saveLocalStorage: function(){
            if(!localStorage.top10) {
                localStorage.top10 = JSON.stringify([]);
                var top10 = JSON.parse(localStorage['top10']);
                this.collection.sort();
                _.each(this.collection, function(element, index){
                        top10.push({'name': this.collection.at(index).get('name'), 'score': this.collection.at(index).get('score')});
                }, this);
                localStorage["top10"] = JSON.stringify(top10);
            }
        },

    });

    return View;
});