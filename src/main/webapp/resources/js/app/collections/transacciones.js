/**
 * 
 */

define([
    'app/models/transaccion',
    'configuration'
], function(Transaccion, config){

	var Transacciones = Backbone.Collection.extend({
		url: config.baseUrl + "rest/resources/transacciones",
		model: Transaccion,
		id: "id",
		comparator: function(model){
			return model.codigo;
		}
	});
	return Transacciones;
});
