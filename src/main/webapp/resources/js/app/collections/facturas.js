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
		},
		refresh: function () {
			this.fetch();
			setTimeout(this.refresh.bind(this), this.wait);
		}
	});
	return Facturas;
});
