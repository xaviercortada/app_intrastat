/**
 * 
 */

define([
    'configuration',
    'backbone',
    'relational'
    ],function(config){
		var Favorito = Backbone.RelationalModel.extend({
			url: config.baseUrl + 'rest/favoritos',
			//idAttribute: "code",
			defaults:{
				"code" : undefined,
				"level":"",
				"section":"",
				"description":""
			}
	});
	
	return Favorito;
	
});