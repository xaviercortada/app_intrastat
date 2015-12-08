/**
 * 
 */

define([ 'jquery', 
         'underscore',
         'backbone', 
		'text!templates/desktop/login.html',
		'jqueryui'
         ], function($, _, Backbone, htmlTemplate) {

	
	var ConfirmModalView = Backbone.View.extend({
//		className: "modal fade",
//		  attributes: {
//		    tabindex: "-1",
//		    role: "dialog",
//		  },
		  
		initialize : function() {
			_.bindAll(this, "render");

		},

		show : function() {
			 $(document.body).append(this.render().el);
		},


	    events: {
	        'click .confirm': 'confirm',
	        'click .discard': 'discard',
	        'click .ui-button': 'login'
	      },

		discard : function() {
			this.$el.modal("close");
		},

		confirm : function() {		
			this.trigger('confirm');
			
			this.$el.modal("close");
			
			Backbone.View.prototype.remove.bind(this)
		},
		
	    render: function () {
	        var template =  _.template(htmlTemplate);// _.template( $("#dialog_template").html(), {} );
	        this.$el.html(template).dialog({
	             title:"Login",
	             autoOpen: true, 
	             closeOnEscape: false,
	             resizable: false,
	             draggable: true,
	             dialogClass: "no-close",
	             modal: true,
	             buttons: [
	             { 
	            	 Login: this.login
	             },
	             {
		             text: "Confirmar",
		             icons: {
		               primary: "ui-icon-heart"
		             },
		             dialogClass: "confirm"
	             }
	             ]
	         });
	        
	        this.delegateEvents(this.events);
	        
	        return this;
	     },  

		render2 : function() {
			this.$el.html(this.template(this.model)).modal();

			return this;
		},

	});

	return ConfirmModalView;
});
