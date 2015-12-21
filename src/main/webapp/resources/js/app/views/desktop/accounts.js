/**
 * 
 */

define([
    	'utilities',
    	'bootstrap',
    	'router',
    	'app/collections/accounts',
    	'app/views/desktop/account',
    	'text!templates/desktop/accounts.html'
    ], function (
    		utilities,
    		bootstrap,
    		router,
    		Accounts,
    		AccountView,
    		template) {
    		
    		var AccountsView = Backbone.View.extend({
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
    					ul.append(new AccountView({model: item}).render().el);
    				});    				 				
    				return this;
    		}

		});
    	return AccountsView;
    });
