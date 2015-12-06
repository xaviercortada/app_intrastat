/**
 * 
 */

define([
    'app/models/provincia',
    'configuration'
], function(Provincia, config){

	var Provincias = Backbone.Collection.extend({
		url: config.baseUrl + "rest/resources/provincias",
		model: Provincia,
		id: "codigo",
		comparator: function(model){
			return model.codigo;
		}
	});
	return Provincias;
});
