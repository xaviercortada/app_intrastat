/**
 * 
 */

define([
    	'utilities',
    	'bootstrap',
    	'router',
    	'app/collections/categories',
    	'app/views/desktop/category',
    	'text!../../../../templates/desktop/categories.html'
    ], function (
    		utilities,
    		bootstrap,
    		router,
    		Categories,
    		CategoryView,
    		template) {
    		
    		var CategoriesView = Backbone.View.extend({
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
			    	this.router.navigate('/addCategory', true);
			    	
			    },
    			render:function () {	
    				this.el.innerHTML = this.template;
    				var ul = this.$el.find("table");
    				this.collection.forEach( function(item) {
    					ul.append(new CategoryView({model: item}).render().el);
    				});    				 				
    				return this;
    		}

		});
    	return CategoriesView;
    });
