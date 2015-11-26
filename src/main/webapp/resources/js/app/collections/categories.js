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
		},
		refresh: function () {
			this.fetch();
			setTimeout(this.refresh.bind(this), this.wait);
		}
	});
	return Categories;
});
