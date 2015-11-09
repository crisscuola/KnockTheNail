define(function(){

    function Socket(){

        ws = null;

        this.myName = "";
        this.enemyName = "";

        this.init = function () {
           ws = new WebSocket("ws://localhost:8080/gameplay");
           ws.onopen = function (event) {
           console.log("Web Socket opened");
        }

        ws.onmessage = function (event) {
           var data = JSON.parse(event.data);
           if(data.status == "start"){
           document.getElementById("wait").style.display = "none";
           document.getElementById("gameplay").style.display = "block";
           document.getElementById("enemyName").innerHTML = data.enemyName;
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

           if(data.status == "increment" && data.name == document.getElementById("myName").innerHTML){
               document.getElementById("myScore").innerHTML = data.score;

           }

           if(data.status == "increment" && data.name == document.getElementById("enemyName").innerHTML){
               document.getElementById("enemyScore").innerHTML = data.score;

           }

           if (data.status == "increment" ){
               document.getElementById("commonScore").innerHTML = data.score;
               //document.getElementById("frictionRate").innerHTML = data.frictionRate;
           }

        //   document.getElementById("test1").value ="Btn1 = " + data.button1;
        //   document.getElementById("test2").value ="Btn2 =  " + data.button2;
        //   document.getElementById("test3").value ="Btn3 =  " + data.button3;

        }

        ws.onclose = function (event) {
           console.log("WebSocket closed");
           ws.close();
        }

        };

        this.sendForce = function(force) {
            var message = {"force": force};
            ws.send(JSON.stringify(message));
            console.log(message);
        }

        this.onGameStart = function() {
            $("body").find(".square_game").load(this.init());
        }

    }

    return Socket;

});