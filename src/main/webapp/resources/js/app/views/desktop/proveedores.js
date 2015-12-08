/**
 * 
 */

define([
    	'utilities',
    	'bootstrap',
    	'router',
    	'app/collections/proveedores',
    	'app/views/desktop/proveedor',
    	'text!templates/desktop/proveedores.html'
    ], function (
    		utilities,
    		bootstrap,
    		router,
    		Proveedores,
    		ProveedorView,
    		template) {
    		
    		var ProveedoresView = Backbone.View.extend({
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
			    	this.router.navigate('/addProveedor', true);
			    	
			    },
    			render:function () {	
    				this.el.innerHTML = this.template;
    				var ul = this.$el.find("table");
    				this.collection.forEach( function(item) {
    					ul.append(new ProveedorView({model: item}).render().el);
    				});    				 				
    				return this;
    		}

		});
    	return ProveedoresView;
    });
