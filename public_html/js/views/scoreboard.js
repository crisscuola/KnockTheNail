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
            //this.collection.on("add", function(event){console.log(event);},this);
            var that = this;
            //this.getScoreboard();


            //console.log(this.collection.toJSON());
            //this.on('hide', this.saveLocalStorage);
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
                this.collection.fetch({success: function(){console.log('Fetched!'); that.render();}});
//                            this.collection.sort('win');
            } else {
                var top10 = JSON.parse(localStorage['top10']);
                _.each(top10, function(element){
                    this.collection.push(new this.player({name: element.name, win: element.win, lose: element.lose}));
                }, this);
                this.render();
            }
        },
        saveLocalStorage: function(){
            if(!localStorage.top10 || localStorage.top10.length < 3) {
                if(this.collection.length != 0) {
                    localStorage.top10 = JSON.stringify([]);
                    var top10 = JSON.parse(localStorage['top10']);
                    this.collection.sort();
                    _.each(this.collection, function(element, index){
                            top10.push({'name': this.collection.at(index).get('name'), 'win': this.collection.at(index).get('win'),
                            'lose': this.collection.at(index).get('lose')});
                    }, this);
                    localStorage["top10"] = JSON.stringify(top10);
                }
            }
        },

    });

    return View;
});