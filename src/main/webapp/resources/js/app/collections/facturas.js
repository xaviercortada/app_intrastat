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
		
		findByProveedor : function(flujo, present, idProveedor){
			this.url =  config.baseUrl + "rest/facturas/proveedor/"+idProveedor+"?flujo="+flujo+"&present="+present;

	        this.fetch({
	        	reset : true
	        });
		},

		findByFecha : function(flujo, present, fechaIni, fechaFin){
			this.url =  config.baseUrl + 'rest/facturas/interval?fechaIni='+fechaIni+'&fechaFin='+fechaFin+'&flujo='+flujo+"&present="+present+'&start=0&max=0';
	        this.fetch({
	        	reset : true
	        });
		},
		
		findByCodigo : function(flujo, present, codigo){
			this.url = config.baseUrl + 'rest/facturas/codigo/'+codigo;
	        this.fetch({
	        	reset : true
	        });

		},


		comparator: function(model){
			return model.codigo;
		}
	});
	return Facturas;
});
