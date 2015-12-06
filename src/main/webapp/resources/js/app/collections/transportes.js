/**
 * 
 */

define([
    'app/models/transporte',
    'configuration'
], function(Transporte, config){

	var Transportes = Backbone.Collection.extend({
		url: config.baseUrl + "rest/resources/transportes",
		model: Transporte,
		id: "codigo",
		comparator: function(model){
			return model.codigo;
		}
	});
	return Transportes;
});
