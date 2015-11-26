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
	'app/models/category',
	'app/collections/categories',
	'text!../../../../templates/desktop/editMaterial.html'
], function (
		utilities,
		bootstrap,
		datepicker,
		router,
		Material,
		Materiales,
		Category,
		Categories,
		htmlTemplate) {
		
		var EditMaterialView = Backbone.View.extend({
			tagName: "tr",
			className: "materialItem",
			
			initialize: function() {
		        this.template = _.template(htmlTemplate);
		        		        
		        this.categories = new Categories();
		        
		        this.categories.on("reset", this.fillCategories, this);
		        this.categories.fetch({
		        	reset : true
		        });
		        	        
		    },
		    events: {
		    	'click .delete' : 'destroy'
		    },
		    
		    fillCategories : function(){
		    	var p = this.model.get('category');
		    	var i = -1;
		    	if(p != undefined) i = p.id;
		    	
				var opts = this.categories.map(function(item) {
					var opt = item.toJSON();
					return "<option value='"+opt.id+"'>"+opt.codigo+' - '+opt.name+"</option>";
				});
				this.$el.find("#category").append(opts);		

				var selection = this.model.get('category');
				if(selection != undefined){
					this.$el.find('#category option').filter(function(index, e){
						return e.value == selection.get('id');
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


		    update: function(){
		    	var fecha = new Date($("#fecha").val());
		    	this.model.set({
		    		name:$("#name").val(),
		    		codigo: $("#codigo").val(),
		    		entrega: $("#entrega").val(),
		    		fecha: fecha,
		    		proveedor: {id: $("#proveedor").val()},
		    		transporte: {codigo: $("#transporte").val()}
		    	});
		    	
		    	var router = this.router;
		    	
		    	this.model.save(null,{
		    			error: function(model, response){
		    				console.log(response.responseText);
		    				router.navigate('/', true);
		    			},
		    			success: function(model, response){
		    				router.navigate('/facturas', true);
		    			}
		    	});
		    	
				this.remove();
				this.unbind();

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
