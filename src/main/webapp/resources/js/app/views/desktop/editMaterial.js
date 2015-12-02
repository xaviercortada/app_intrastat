/**
 * 
 */

define([
	'utilities',
	'bootstrap',
	'datepicker',
	'router',
	'app/models/material',
	'app/collections/materiales',
	'app/models/favorito',
	'app/collections/favoritos',
	'text!../../../../templates/desktop/editMaterial.html'
], function (
		utilities,
		bootstrap,
		datepicker,
		router,
		Material,
		Materiales,
		Nomenclator,
		Nomenclators,
		htmlTemplate) {
		
		var EditMaterialView = Backbone.View.extend({
			tagName: "tr",
			className: "materialItem",
			
			initialize: function() {
		        this.template = _.template(htmlTemplate);
		        		        
		        this.nomenclators = new Nomenclators();
		        
		        this.nomenclators.on("reset", this.fillNomenclators, this);
		        this.nomenclators.fetch({
		        	reset : true
		        });
		        	        
		    },
		    events: {
		    	'click .delete' : 'destroy'
		    },
		    
		    fillNomenclators : function(){
		    	var p = this.model.get('nomenclature');
		    	var i = -1;
		    	if(p != undefined) i = p.id;
		    	
				var opts = this.nomenclators.map(function(item) {
					var opt = item.toJSON();
					return "<option value='"+opt.code+"'>"+opt.code+' - '+opt.description+"</option>";
				});
				this.$el.find("#nomenclature").append(opts);		

				var selection = this.model.get('nomenclature');
				if(selection != undefined){
					this.$el.find('#nomenclature option').filter(function(index, e){
						return e.value == selection.get('code');
					}).prop("selected", true);
				}

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

				this.$el.html(this.template({
					model : this.model.toJSON(),
					index: this.index
				}));
								
				return this;
		},
	});
	return EditMaterialView;
});
