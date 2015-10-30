define([
    'backbone',
    'tmpl/game'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        el: '.game',
        template: tmpl,
        name: 'game',
        events: {
            'click .button-group__button:first': 'knock1',
            'click .button-group__button:nth-child(2)': 'knock2',
            'click .button-group__button:nth-child(3)': 'knock3',
            'click .square__reset': 'reset'
        },
        initialize: function () {
        },
        render: function () {
            this.$el.html(this.template);
            this.delegateEvents();
            return this;
        },
        show: function () {
            this.trigger("show", this);
        },
        hide: function () {
            this.$el.hide();
        },
        knock1: function() {
            if(this.$el.find('.square__button-group').attr("disabled") != "disabled") {
                var $nail = this.$el.find('.square__nail');
                $nail.animate({ top: "+=10"});
                console.log(parseInt($nail.css('top'), 10));
                if((parseInt($nail.css('top'), 10) > 245)) {
                    alert("WINNER");
                    this.$el.find('.square__button-group').attr("disabled", "disabled");
                }
            }
        },
        knock2: function() {
            if(this.$el.find('.square__button-group').attr("disabled") != "disabled") {
                var $nail = this.$el.find('.square__nail');
                $nail.animate({ top: "+=15"});
                console.log(parseInt($nail.css('top'), 10));
                if((parseInt($nail.css('top'), 10) > 245)) {
                    alert("WINNER");
                    this.$el.find('.square__button-group').attr("disabled", "disabled");
                }
            }
        },
        knock3: function() {
            if(this.$el.find('.square__button-group').attr("disabled") != "disabled") {
                var $nail = this.$el.find('.square__nail');
                $nail.animate({ top: "+=25"});
                console.log(parseInt($nail.css('top'), 10));
                if((parseInt($nail.css('top'), 10) > 245)) {
                    alert("WINNER");
                    this.$el.find('.square__button-group').attr("disabled", "disabled");
                }
            }
        },
        reset: function() {
            $nail = this.$el.find('.square__nail').animate({ top: '110px'}, 'fast');
            this.$el.find('.square__button-group').attr("disabled", false);
        }

    });

    return  new View();
});