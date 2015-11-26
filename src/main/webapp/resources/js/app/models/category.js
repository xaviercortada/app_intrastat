/**
 * 
 */

define([
    'configuration',
    'backbone',
    'relational'
    ],function(config){
		var Category = Backbone.RelationalModel.extend({
			urlRoot: config.baseUrl + 'rest/categories',
			idAttribute: "id",
			defaults:{
				"id" : undefined,
				"codigo":"",
				"name":"",
				"descripcion":""
			}
	});
	
	return Category;
	
});