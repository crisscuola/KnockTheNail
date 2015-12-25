define([
    'backbone',
], function(
    Backbone
){

    var Socket = Backbone.View.extend({
        el: '.game',
        name: 'name',
        initialize: function(){
        },
        model: null,
        ws: null,
        commonscore: null,
        init: function(){
            this.ws = new WebSocket("ws://localhost:8080/gameplay");
            this.nail_y = 0;
            this.nail_dy = 10;
            this.nail(this.nail_y);
            this.table();
            var that = this;

            this.ws.onopen = function (event) {
                console.log("Web Socket opened");
            }

            this.ws.onmessage = function (event) {
                var data = JSON.parse(event.data);

                if(data.status == "start"){
                    that.table();
                    that.nail(that.nail_y);
                    document.getElementById("wait").style.display = "none";
                    document.getElementById("gameplay").style.display = "block";
                    document.getElementById("enemyName").innerHTML = data.enemyName;
                    if(data.shouldClick){
                        that.$el.find('#waitOpponent').hide();
                        that.$el.find('.game-form').show();
                    } else {
                        that.$el.find('#waitOpponent').show();
                        that.$el.find('.game-form').hide();
                    }
                }

                if(data.status == "disconnect"){
                    document.getElementById("disconnect").style.display = "block";
                    document.getElementById("gameplay").style.display = "none";
                }

                if(data.status == "finish"){
                    document.getElementById("gameOver").style.display = "block";
                    document.getElementById("game").style.display = "none";
                    document.getElementById("gameplay").style.display = "none";
                    if(data.win) {
                        document.getElementById("win").innerHTML = "win!";
                    } else {
                        document.getElementById("win").innerHTML = "lose.";
                    }
                }

                if(data.status == "increment_myscore"){
                    if(data.shouldClick){
                        that.$el.find('#waitOpponent').hide();
                        that.$el.find('game-form').show();
                    } else {
                        that.$el.find('#waitOpponent').show();
                        that.$el.find('.game-form').hide();
                    }
                }

                if(data.status == "increment_enemyscore"){
                    if(!data.shouldClick){
                        that.$el.find('#waitOpponent').hide();
                        that.$el.find('.game-form').show();
                    } else {
                        that.$el.find('#waitOpponent').show();
                        that.$el.find('.game-form').hide();
                    }
                }

                if (data.status == "increment" ){
                    that.commonscore = data.commonScore;
                    var movement = data.commonScore ;
                    that.knock(movement);
                }

            }

            this.ws.onclose = function (event) {
               console.log("WebSocket closed");
               that.ws.close();
            }
        },
        sendForce: function(force, name) {
            var message = {"force": force, "name": name};
            this.ws.send(JSON.stringify(message));
        },

        onGameStart: function() {
            $("body").find(".square_game").load(this.init());

        },

        clearCanvas: function() {
            canvas = document.getElementById('canvas');
            context = canvas.getContext('2d');
            context.clearRect(0,0,canvas.width,canvas.height);
        },

        table: function() {
            canvas = document.getElementById('canvas');
            context = canvas.getContext('2d');
            context.fillStyle = '#000';
            context.beginPath();
            context.rect(50,100,200,50);
            context.fill();
        },

        nail: function(y) {
            canvas = document.getElementById('canvas');
            context = canvas.getContext('2d');
            var pic = new Image();
            pic.src    = '/design/nail.jpg';
            context.drawImage(pic,140,y, 20, 100);
        },

        knock: function(y) {
            canvas = document.getElementById('canvas');
            context = canvas.getContext('2d');
            this.clearCanvas();
            this.nail_y += y;
            if(this.nail_y > 150)
                this.nail_y = 20;
            this.nail(y);
            this.table();
        }

    });

    return Socket;
});