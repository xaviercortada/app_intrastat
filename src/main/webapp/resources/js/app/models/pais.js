/**
 * 
 */

define([
    'configuration',
    'backbone',
    'relational'
    ],function(config){
		var Pais = Backbone.RelationalModel.extend({
			urlRoot: config.baseUrl + 'rest/paises',
			idAttribute: "id",
			defaults:{
				"codigo":"",
				"name":"",
				"sigla":""
			}
	});
	
	return Pais;
	
});