/**
 * 
 */

define([
    'app/models/nomenclature',
    'configuration'
], function(Nomenclature, config){

	var Capitulos = Backbone.Collection.extend({
		model: Nomenclature,
		id: "code",
		urlxxx: function(){
			return config.baseUrl + "rest/nomenclatures/capitulos/"+this.codigo;
		},
		
		findByCapitulo : function(codigo){
			this.url =  config.baseUrl + "rest/nomenclatures/capitulos/"+codigo;
	        this.fetch({
	        	reset : true
	        });
		},

		comparator: function(model){
			return model.code;
		}
	});
	return Capitulos;
});
