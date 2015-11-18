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

            if (!localStorage.top10 || localStorage.top10.length < 2) {
//                for (var i = 0; i < 10; i++) {
//                    var rand_name = Math.random().toString(36).substr(2, 5);
//                    var rand_score = Math.floor(Math.random()*(100));
//                    this.collection.push(new this.player({name: rand_name, win: rand_score, lose: rand_score}));
//                }
                //this.collection.fetch();
//                this.collection.sort('win');
//                console.log(this.collection.toJSON());
            } else {
//                var top10 = JSON.parse(localStorage['top10']);
//                _.each(top10, function(element){
//                    this.collection.push(new this.player({name: element.name, win: element.win, lose: element.lose}));
//                }, this);
            }

            //console.log(this.collection.toJSON());
            //this.on('hide', this.saveLocalStorage);
        },
        render: function () {
            this.$el.html(this.template(this.collection.toJSON()));
            this.delegateEvents();
            return this;
        },
        show: function () {
            this.trigger("show", this);
            console.log(this.collection);
            this.render();
            if (!localStorage.top10 || localStorage.top10.length <= 2) {
//                            for (var i = 0; i < 10; i++) {
//                                var rand_name = Math.random().toString(36).substr(2, 5);
//                                var rand_score = Math.floor(Math.random()*(100));
//                                this.collection.push(new this.player({name: rand_name, win: rand_score, lose: rand_score}));
//                            }
                            this.collection.fetch();
                            console.log(this);
//                            this.collection.sort('win');
//                            console.log(this.collection.toJSON());
                        } else {
                            var top10 = JSON.parse(localStorage['top10']);
                            _.each(top10, function(element){
                                this.collection.push(new this.player({name: element.name, win: element.win, lose: element.lose}));
                            }, this);
                        }
            var that  = this;
            this.collection.fetch({success: function(){console.log('Success');}});
            console.log(this.collection);
        },
        hide: function () {
            this.saveLocalStorage();
            this.$el.hide();
            this.collection.sort();
//            _.each(this.collection, function(element, index){
//                    this.collection.remove(element);
//            }, this);
        },
        saveLocalStorage: function(){
            if(!localStorage.top10 || localStorage.top10.length <= 2) {
                console.log(this.collection);
                localStorage.top10 = JSON.stringify([]);
                var top10 = JSON.parse(localStorage['top10']);
                this.collection.sort();
                _.each(this.collection, function(element, index){
                        top10.push({'name': this.collection.at(index).get('name'), 'win': this.collection.at(index).get('win'),
                        'lose': this.collection.at(index).get('lose')});
                }, this);
                localStorage["top10"] = JSON.stringify(top10);
            }
        },

    });

    return View;
});