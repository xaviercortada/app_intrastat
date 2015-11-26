/**
 * 
 */

define([
    'configuration',
    'app/models/category',
    'backbone',
    'relational'
    ],function(config, Category, backbone, relational){
		var Material = Backbone.RelationalModel.extend({
			relations: [{
				type: Backbone.HasOne,
				key: 'category',
				relatedModel: Category
			}],
			urlRoot: config.baseUrl + 'rest/materiales',
			idAttribute: "id",
			defaults:{
				"id" : undefined,
				"codigo":"",
				"importe": 0,
				"peso": 0,
				"unidades" : 0,
				"category" : {}
			}
	});
	
	return Material;
	
});