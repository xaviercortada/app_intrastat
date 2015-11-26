/**
 * 
 */

define([
    'configuration',
    'backbone',
    'relational'
    ],function(config){
		var Transporte = Backbone.RelationalModel.extend({
			urlRoot: config.baseUrl + 'rest/trasportes',
			idAttribute: "codigo",
			defaults:{
				"codigo":"",
				"name":""
		}
	});
	
	return Transporte;
	
});