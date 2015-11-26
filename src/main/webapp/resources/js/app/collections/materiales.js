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
		},
		refresh: function () {
			this.fetch();
			setTimeout(this.refresh.bind(this), this.wait);
		}
	});
	return Materiales;
});
