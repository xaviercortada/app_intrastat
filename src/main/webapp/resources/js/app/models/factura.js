/**
 * 
 */

define([
    'configuration',
    'backbone',
    'app/models/proveedor',
    'app/models/transporte',
    'app/models/pais',
    'app/models/provincia',
    'app/models/material',
	'app/collections/materiales',
    'relational'
    ],function(config, backbone, Proveedor, Transporte, Pais, Provincia, Material, Materiales, relational){
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
				type: Backbone.HasOne,
				key: 'origen',
				relatedModel: Pais
			},
			{
				type: Backbone.HasOne,
				key: 'provincia',
				relatedModel: Provincia
			},
			{
				type: Backbone.HasMany,
				key: 'materiales',
				relatedModel: Material,
				collectionType: Materiales,
				reverseRelation: {
				      key: 'factura'
				}
			}],
			urlRoot: config.baseUrl + 'rest/facturas',
			idAttribute: "id",
			autoFetch: true,
			defaults:{
				"id" : undefined,
				"flujo":"",
				"codigo":"",
				"entrega":"",
				"fecha":"",
				"proveedor":{},
				"transporte":{},
				"pais":{},
				"materiales": []
			},
			validation : {
				codigo : {
					required : true,
					minLength: 5
				},
				flujo : {
					required: true
				},
				transaccion :{
					fn : 'validateValue'
				},
				regimen :{
					fn : 'validateValue'
				},
				fecha : {
					fn: 'validateFecha'
				},
				transporte : {
					fn : 'validateModel'
				},
				proveedor : {
					fn : 'validateModel'
				},
				entrega : {
					fn : 'validateValue'
				},
				pais : {
					fn : 'validatePais'
				}
			},
			
			validateFecha : function(value, attr, computedState){
				if(value == undefined && !(value instanceof Date) && isNaN(value.getSeconds())){
					return 'fecha no valida';
				}
			},
			
			validateModel: function(value, attr, computedState){
				if(value.id == "-1"){
					return 'debe seleccionar un valor';
				}
			},
			
			validateValue: function(value, attr, computedState){
				if(value == "-1"){
					return 'debe seleccionar un valor';
				}
			},

			validatePais: function(value, attr, computedState){
				if(value.get("codigo") == ""){
					return 'debe seleccionar un pais';
				}
			}
	});
	
	return Factura;
	
});