/**
 * 
 */

define([
    'configuration',
    'backbone',
    'relational'
    ],function(config){
		var Provincia = Backbone.RelationalModel.extend({
			urlRoot: config.baseUrl + 'rest/provincias',
			idAttribute: "codigo",
			defaults:{
				"codigo":"",
				"name":""
			}
	});
	
	return Provincia;
	
});