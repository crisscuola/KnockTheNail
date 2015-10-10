define([
    'backbone',
    'tmpl/game'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        el: '.page',
        template: tmpl,
        events: {
            'click .button-group__button:first': 'knock1',
            'click .button-group__button:nth-child(2)': 'knock2',
            'click .button-group__button:nth-child(3)': 'knock3',
            'click .square__reset': 'reset'
        },
        initialize: function () {
            console.log("game view is initialized");
        },
        render: function () {
            this.$el.html(this.template);
            this.$el.find(".square").css('right', '700px')
                .animate({right: 0});
            return this;
        },
        show: function () {

        },
        hide: function () {

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

    return new View();
});