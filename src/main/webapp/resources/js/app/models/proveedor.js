/**
 * 
 */

define([
    'configuration',
    'backbone',
    'relational'
    ],function(config){
		var Proveedor = Backbone.RelationalModel.extend({
			urlRoot: config.baseUrl + 'rest/proveedores',
			idAttribute: "id",
			defaults:{
				"id" : undefined,
				"codigo":"",
				"name":"",
				"documento":""
			}
	});
	
	return Proveedor;
	
});