/**
 * 
 */

define([
	'utilities',
	'bootstrap',
	'text!templates/desktop/proveedor.html'
], function (
		utilities,
		bootstrap,
		htmlTemplate) {
		
		var ProveedorView = Backbone.View.extend({
			tagName: "tr",
			
			initialize: function() {
		        this.template = _.template(htmlTemplate);
		    },
		    events: {
		    	'click .delete' : 'destroy'
		    },
		    
		    destroy: function(e){
		    	e.preventDefault();
		    	this.model.destroy();
		    	this.remove();		    	
		    } ,
		    
		    remove: function () {
				 this.$el.fadeOut(Backbone.View.prototype.remove.bind(this));
				 return false;
		    },
			    
			render:function () {
				//this.$el[0].setAttribute("href",'rest/categories/'+this.model.id); 
				this.$el.html(this.template(this.model.toJSON()));
				return this;
		},
	});
	return ProveedorView;
});
