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
            this.on('hide', this.saveLocalStorage);
            this.on('show', this.getLocaStorage);
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
        },
        saveLocalStorage: function(){
        if(!localStorage.test)
            localStorage.test = JSON.stringify([]);
            //localStorage.setItem('test', this.name);
            _.each(this.collection, function(element, index){
                    //console.log(this.collection.at(index));
                  localStorage.setItem(index, this.collection.at(index).get('name'));
            }, this);
        },
        getLocaStorage: function(){
            localStorage.getItem('test');
        }

    });

    return View;
});