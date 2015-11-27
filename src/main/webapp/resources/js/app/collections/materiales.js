/**
 * 
 */

define([
    'app/models/material',
    'configuration'
], function(Material, config){

	var Materiales = Backbone.Collection.extend({
		url: config.baseUrl + "rest/materiales",
		model: Material,
		id: "id",
		comparator: function(model){
			return model.id;
		}
	});
	return Materiales;
});
