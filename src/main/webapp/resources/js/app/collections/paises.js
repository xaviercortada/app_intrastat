/**
 * 
 */

define([
    'app/models/pais',
    'configuration'
], function(Pais, config){

	var Paises = Backbone.Collection.extend({
		url: config.baseUrl + "rest/resources/paises",
		model: Pais,
		id: "codigo",
		comparator: function(model){
			return model.codigo;
		}
	});
	return Paises;
});
