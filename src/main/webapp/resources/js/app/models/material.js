/**
 * 
 */

define([
    'configuration',
    'app/models/nomenclature',
    'backbone',
    'relational'
    ],function(config, Nomenclature, backbone, relational){
		var Material = Backbone.RelationalModel.extend({
			relations: [{
				type: Backbone.HasOne,
				key: 'nomenclature',
				relatedModel: Nomenclature
			}],
			urlRoot: config.baseUrl + 'rest/materiales',
			idAttribute: "id",
			defaults:{
				"id" : undefined,
				"codigo":"",
				"importe": 0,
				"peso": 0,
				"unidades" : 0,
				"nomenclature" : {}
			}
	});
	
	return Material;
	
});