/**
 * 
 */

define([
    'configuration',
    'backbone',
    'relational'
    ],function(config){
		var Nomenclature = Backbone.RelationalModel.extend({
			urlRoot: config.baseUrl + 'rest/nomenclatures',
			idAttribute: "code",
			defaults:{
				"code" : undefined,
				"level":"",
				"section":"",
				"description":""
			}
	});
	
	return Nomenclature;
	
});