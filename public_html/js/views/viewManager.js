define([
    'backbone'
], function(
    Backbone
){
    var ViewManager = Backbone.View.extend({
        function AppView(){

           this.showView(view) {
            if (this.currentView){
              this.currentView.close();
            }

            this.currentView = view;
            this.currentView.render();

            $(".page").html(this.currentView.el);
          }

        }
    });
});
