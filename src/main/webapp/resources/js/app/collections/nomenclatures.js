/**
 * 
 */

define([
    'app/models/nomenclature',
    'configuration'
], function(Nomenclature, config){

	var Nomenclatures = Backbone.Collection.extend({
		model: Nomenclature,
		id: "code",
		
		findByLevel : function(level){
			this.url =  config.baseUrl + "rest/nomenclatures/level/"+level;
	        this.fetch({
	        	reset : true
	        });
		},

		findByCapitulo : function(codigo){
			this.url =  config.baseUrl + "rest/nomenclatures/capitulos/"+codigo;
	        this.fetch({
	        	reset : true
	        });
		},
		
		findByTexto : function(capitulo, texto){
			this.url =  config.baseUrl + "rest/nomenclatures/capitulo/"+capitulo+"/items/"+texto;
	        this.fetch({
	        	reset : true
	        });
		},

		search : function(texto){
			this.url =  config.baseUrl + "rest/nomenclatures/search/texto/"+texto;
	        this.fetch({
	        	reset : true
	        });
		},

		search_codigo : function(codigo){
			this.url =  config.baseUrl + "rest/nomenclatures/search/codigo/"+codigo;
	        this.fetch({
	        	reset : true
	        });
		},

		comparator: function(model){
			return model.codigo;
		}
	});
	return Nomenclatures;
});
