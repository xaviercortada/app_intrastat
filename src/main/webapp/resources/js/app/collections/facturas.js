/**
 * 
 */

define([
    'app/models/factura',
    'configuration'
], function(Factura, config){

	var Facturas = Backbone.Collection.extend({
		url: config.baseUrl + "rest/facturas",
		model: Factura,
		id: "id",
		comparator: function(model){
			return model.codigo;
		}
	});
	return Facturas;
});
