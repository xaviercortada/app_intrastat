/**
 * 
 */

define([
	'utilities',
	'bootstrap',
	'text!templates/desktop/nomenclature.html'
], function (
		utilities,
		bootstrap,
		htmlTemplate) {
		
		var NomenclatureView = Backbone.View.extend({
			tagName: "tr",
			
			initialize: function() {
		        //this.template = _.template("<a class='list-group-item' href='rest/categories/{{id}}'>{{codigo}}</a>");
		        //this.template = template;//_.template("<td>{{id}}</td><td>{{codigo}}</td><td>{{name}}</td>");
		        this.template = _.template(htmlTemplate);
		        //this.render();
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
	return NomenclatureView;
});
