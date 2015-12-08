/**
 * 
 */

define([
	'utilities',
	'bootstrap',
	'router',
	'app/models/nomenclature',
	'app/models/material',
	'app/collections/materiales',
	'app/models/favorito',
	'app/collections/favoritos',
	'text!../../../../templates/desktop/editMaterial.html'
], function (
		utilities,
		bootstrap,
		router,
		Nomenclature,
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
		        
				this.nomen = this.model.get("nomenclature");
		        		        
		        this.nomenclators = new Nomenclators();
		        
		        this.nomenclators.on("reset", this.fillNomenclators, this);
		        this.nomenclators.fetch({
		        	reset : true
		        });
		        	        
		    },
		    events: {
		    	'click .delete' : 'destroy',
		    	'change #nomenclature' : 'codigoChanged'
		    },
		    
		    codigoChanged : function(e){
		    	this.nomen = Nomenclature.findOrCreate({
		    		code : $(e.target).val()
		    	});
		    	this.listenTo(this.nomen, 'change', this.nomenChanged());
		    },
		    
		    fillNomenclators : function(){
		    	var p = this.model.get('nomenclature');
		    	var i = -1;
		    	if(p != undefined) i = p.id;
		    	
				var opts = this.nomenclators.map(function(item) {
					var opt = item.toJSON();
					return "<option class='ttip' value='"+opt.code+"' title='"+opt.description+"'>"+opt.code+' - '+opt.description+"</option>";
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
		    	this.trigger('removeMat', this.model);
		    	this.model.destroy();
		    	this.remove();		    	
		    } ,
		    
		    remove: function () {
				 this.$el.fadeOut(Backbone.View.prototype.remove.bind(this));
				 return false;
		    },
		    
			render:function () {
				var mat = this.model.toJSON();
				
				mat.sunit = this.nomen.get('sunit');
				mat.sunitDesc = this.updateSunit(this.nomen.get('sunitDesc'));
				

				this.$el.html(this.template({
					model : mat,
					index: this.index
				}));
				
				
				this.$el.find(".ttip").tooltip();
				
				this.$el.find('.nomen').tooltip({
					position: { my: "center bottom-10", at: "center top" },
			        content: function(){
			            return $(this).find("option:selected").text();
			        }
			    });
				
				return this;
		},
		
		nomenChanged : function(){
			var sunit = this.nomen.get('sunit');
			var sunitDesc = this.updateSunit(this.nomen.get('sunitDesc'));
			$('#sunit').val(sunit);
			$('#sunitDesc').val(sunitDesc);
		},
		
		updateSunit : function(sunitDesc){
			if(sunitDesc != undefined && sunitDesc.length > 0){
				var p = sunitDesc.indexOf('EN:');
				if(p>0){
					var t = sunitDesc.indexOf(';', p);
					if(t > 0){
						return sunitDesc.substr(p+4, t-p-4);
					}
				}
			}
			return "";
			
		}
	});
	return EditMaterialView;
});
