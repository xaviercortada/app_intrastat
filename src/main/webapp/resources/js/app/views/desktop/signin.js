/**
 * 
 */

define([ 'jquery', 
         'underscore',
         'backbone', 
         'router',
		'text!templates/desktop/login.html',
		'jqueryui'
         ], function($, _, Backbone, router, htmlTemplate) {

	
	var SigninView = Backbone.View.extend({
//		className: "modal fade",
//		  attributes: {
//		    tabindex: "-1",
//		    role: "dialog",
//		  },
		  
		initialize : function() {
			_.bindAll(this, "render");
			
			Backbone.Validation.bind(this);

			var x = this;

			require([ 'router' ], function(item) {
				x.router = item;
			});


		},

		show : function() {
			 $(document.body).append(this.render().el);
		},


	    events: {
	        'click .close': 'close',
	        'click .login': 'login',
	      },

		login : function() {
			
			this.model.set({
				user:{
					firstName : $("#firstname").val(),
					lastName : $("#lastname").val(),
				},
				userName : $("#username").val(),
				password : $("#password").val()
			});
			
			//if (this.model.isValid(true)) {
				this.model.save(null, {
					error : function(model, response) {
						console.log(response.responseText);
					},
					success : function(model, response) {
					}
				});
				
				this.close();


		},
		
		close : function(){
			this.$el.dialog("close");

			Backbone.Validation.unbind(this);

			this.remove();
			this.unbind();

			this.router.navigate('/', true);
			
		},

		
	    render: function () {
	        var template =  _.template(htmlTemplate);
	        this.$el.html(template).dialog({
	             title:"Login",
	             autoOpen: true, 
	             closeOnEscape: false,
	             resizable: false,
	             draggable: true,
	             dialogClass: "no-close",
	             modal: true
	         });
	        
	        this.delegateEvents(this.events);
	        
	        return this;
	     }  


	});

	return SigninView;
});
