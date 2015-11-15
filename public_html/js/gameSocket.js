define(function(){

    function Socket(){
        this.ws = null;

        this.myName = "";
        this.enemyName = "";

        var canvas = null;
        var context =null;


        this.init = function () {
            this.ws = new WebSocket("ws://localhost:8080/gameplay");
            this.nail_y = 20;
            this.nail_dy = 10;
            this.nail(this.nail_y);

            var that = this;

            this.ws.onopen = function (event) {
                console.log("Web Socket opened");
            }

            this.ws.onmessage = function (event) {
                var data = JSON.parse(event.data);
                //console.log(data);
                if(data.status == "start"){
                    document.getElementById("wait").style.display = "none";
                    document.getElementById("gameplay").style.display = "block";
                    document.getElementById("enemyName").innerHTML = data.enemyName;
//                    if(data.shouldClick)
//                        document.getElementById("waitOpponent").style.display = "none";
//                    else
//                        document.getElementsByClassName("game-form").style.display = "none";
                }

                if(data.status == "disconnect"){
                    document.getElementById("disconnect").style.display = "block";
                    document.getElementById("gameplay").style.display = "none";
                }

                if(data.status == "finish"){
                    document.getElementById("gameOver").style.display = "block";
                    document.getElementById("gameplay").style.display = "none";
                    if(data.win)
                       document.getElementById("win").innerHTML = "winner!";
                    else
                       document.getElementById("win").innerHTML = "loser!";
                }

                if(data.status == "increment_myscore"){
                    console.log(data);
                    document.getElementById("myScore").innerHTML = data.score;
                    if(data.shouldClick){
                        document.getElementById("info").innerHTML = "Your turn!";
                    } else {
                        document.getElementById("info").innerHTML = "Enemy's turn!";
                    }
                }

                if(data.status == "increment_enemyscore"){
                    console.log(data);
                    document.getElementById("enemyScore").innerHTML = data.score;
                    if(!data.shouldClick){
                        document.getElementById("info").innerHTML = "Your turn!";
                    } else {
                        document.getElementById("info").innerHTML = "Enemy's turn!";
                    }
                }

                if (data.status == "increment" ){
                    document.getElementById("commonScore").innerHTML = data.commonScore;
                    document.getElementById("frictionRate").innerHTML = data.frictionRate;
                    var movement = data.button1;
                    that.knock(movement);
                }

            }

            this.ws.onclose = function (event) {
               console.log("WebSocket closed");
               that.ws.close();
            }

        };

        this.sendForce = function(force, name) {
            var message = {"force": force, "name": name};
            this.ws.send(JSON.stringify(message));
        }

        this.onGameStart = function() {
            $("body").find(".square_game").load(this.init());
        }

        this.clearCanvas = function() {
            canvas = document.getElementById('canvas');
            context = canvas.getContext('2d');
            context.clearRect(0,0,canvas.width,canvas.height);
        }

        this.nail = function(y) {
            canvas = document.getElementById('canvas');
            context = canvas.getContext('2d');
            context.fillStyle = '#000';
            context.beginPath();
            context.rect(50,y,5,30);
            context.fill();
        }

        this.knock = function(y) {
            canvas = document.getElementById('canvas');
            context = canvas.getContext('2d');
            this.clearCanvas();
            this.nail_y += y;
            if(this.nail_y > 150)
                this.nail_y = 20;
            this.nail(this.nail_y);
        }

    }

    return Socket;

});