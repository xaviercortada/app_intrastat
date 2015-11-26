/**
 * 
 */

define([
    'app/models/proveedor',
    'configuration'
], function(Proveedor, config){

	var Proveedores = Backbone.Collection.extend({
		url: config.baseUrl + "rest/proveedores",
		model: Proveedor,
		id: "id",
		comparator: function(model){
			return model.codigo;
		},
		refresh: function () {
			this.fetch();
			setTimeout(this.refresh.bind(this), this.wait);
		}
	});
	return Proveedores;
});
