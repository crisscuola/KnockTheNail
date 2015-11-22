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
        initialize: function () {
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
            this.saveLocalStorage();
            if (this.collection.length != 0)
                this.collection.reset();
        },

        getScoreboard: function(){
            if (!localStorage.top10 || localStorage.top10.length < 3) {
                var that = this;
                this.collection.fetch({success: function(response){console.log('Fetched!'); console.log(response); that.render();}});
            } else {
                var top10 = JSON.parse(localStorage['top10']);
                _.each(top10, function(element){
                    this.collection.push(new this.player({name: element.name, wons: element.wons, loses: element.loses, id: element.id}));
                }, this);
                this.render();
            }
        },

        saveLocalStorage: function(){
            if(!localStorage.top10 || localStorage.top10.length < 3) {
                if(this.collection.length != 0) {
                    localStorage.top10 = JSON.stringify([]);
                    var top10 = JSON.parse(localStorage['top10']);
                    //this.collection.sort();
                    _.each(this.collection, function(element, index){
                            top10.push({'name': this.collection.at(index).get('name'), 'wons': this.collection.at(index).get('wons'),
                            'loses': this.collection.at(index).get('loses'), 'id': this.collection.at(index).get('id')});
                    }, this);
                    localStorage["top10"] = JSON.stringify(top10);
                }
            }
        },

    });

    return View;
});