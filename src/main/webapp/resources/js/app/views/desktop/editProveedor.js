/**
 * 
 */

define([
	'utilities',
	'bootstrap',
	'router',
	'text!../../../../templates/desktop/editProveedor.html'
], function (
		utilities,
		bootstrap,
		router,
		htmlTemplate) {
		
		var EditProveedorView = Backbone.View.extend({
			tagName: "div",
			
			initialize: function() {
				this.model.buttonCaption = "save";
		        this.template = _.template(htmlTemplate);
		        
		        var x = this;
		        
		        require(['router'],function(item){
			        x.router = item;			        	
		        });
		    },
		    events: {
		    	'click .save' : 'update'
		    },
		    
		    update: function(){
		    	this.model.set({
		    		name:$("#name").val(),
		    		codigo: $("#codigo").val(),
		    		documento: $("#documento").val(),
		    		descripcion: $("#descripcion").val()
		    	});
		    	
		    	var router = this.router;
		    	
		    	this.model.save(null,{
		    			error: function(model, response){
		    				console.log(response.responseText);
		    				router.navigate('/', true);
		    			},
		    			success: function(model, response){
		    				router.navigate('/proveedores', true);
		    			}
		    	});
		    	
				this.remove();
				this.unbind();

				return false;
		    },
		    
			render:function () {
				//this.$el[0].setAttribute("href",'rest/categories/'+this.model.id); 
				this.$el.html(this.template(this.model.toJSON()));
				return this;
		},
	});
	return EditProveedorView;
});
