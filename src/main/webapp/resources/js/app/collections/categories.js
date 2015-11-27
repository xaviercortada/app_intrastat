/**
 * 
 */

define([
    'app/models/category',
    'configuration'
], function(Category, config){

	var Categories = Backbone.Collection.extend({
		url: config.baseUrl + "rest/categories",
		model: Category,
		id: "id",
		comparator: function(model){
			return model.codigo;
		}
	});
	return Categories;
});
