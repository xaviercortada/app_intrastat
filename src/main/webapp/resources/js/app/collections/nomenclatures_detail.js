/**
 * 
 */

define([
    'app/models/nomenclature',
    'configuration'
], function(Nomenclature, config){

	var Nomenclatures_d = Backbone.Collection.extend({
		model: Nomenclature,
		id: "code",
		
		search : function(codigo){
			this.url =  config.baseUrl + "rest/nomenclatures/detailo/"+codigo;
	        this.fetch({
	        	reset : true
	        });
		},

		comparator: function(model){
			return model.codigo;
		}
	});
	return Nomenclatures_d;
});
