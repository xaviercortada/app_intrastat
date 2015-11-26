/**
 * 
 */

define([
    	'utilities',
    	'bootstrap',
    	'router',
    	'app/collections/facturas',
    	'app/views/desktop/factura',
    	'text!../../../../templates/desktop/facturas.html'
    ], function (
    		utilities,
    		bootstrap,
    		router,
    		Facturas,
    		FacturaView,
    		template) {
    		
    		var FacturasView = Backbone.View.extend({
    			tagName: "div",
    			className: 'container',
    			
			   initialize: function() {

			        this.template = template;
			        
			        var x = this;
			        
			        require(['router'],function(item){
				        x.router = item;			        	
			        });

			    },
			    
			    events: {
			    	'click .add' : 'create'
			    },

			    create: function(){
			    	this.remove();
			    	this.router.navigate('/addFactura', true);
			    	
			    },
    			render:function () {	
    				this.el.innerHTML = this.template;
    				var ul = this.$el.find("table");
    				this.collection.forEach( function(item) {
    					ul.append(new FacturaView({model: item}).render().el);
    				});    				 				
    				return this;
    		}

		});
    	return FacturasView;
    });
