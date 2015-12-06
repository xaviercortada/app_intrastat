/**
 * 
 */

define([
    'configuration',
    'backbone',
    'relational'
    ],function(config){
		var Transaccion = Backbone.RelationalModel.extend({
			urlRoot: config.baseUrl + 'rest/resources/transaccion',
			idAttribute: "id",
			defaults:{
				"codigo":"",
				"texto":"",
				"grupo":""
		}
	});
	
	return Transaccion;
	
});