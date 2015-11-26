/**
 * 
 */

define([
    'configuration',
    'backbone',
    'app/models/proveedor',
    'app/models/transporte',
    'app/models/pais',
    'app/models/material',
	'app/collections/materiales',
    'relational'
    ],function(config, backbone, Proveedor, Transporte, Pais, Material, Materiales, relational){
		var Factura = Backbone.RelationalModel.extend({
			relations: [
			{
				type: Backbone.HasOne,
				key: 'proveedor',
				relatedModel: Proveedor
			},
			{
				type: Backbone.HasOne,
				key: 'transporte',
				relatedModel: Transporte
			},
			{
				type: Backbone.HasOne,
				key: 'pais',
				relatedModel: Pais
			},
			{
				type: Backbone.HasMany,
				key: 'materiales',
				relatedModel: Material,
				collectionType: Materiales
			}],
			urlRoot: config.baseUrl + 'rest/facturas',
			idAttribute: "id",
			defaults:{
				"id" : undefined,
				"codigo":"",
				"entrega":"",
				"fecha":"",
				"proveedor":{},
				"materiales": []
			}
	});
	
	return Factura;
	
});