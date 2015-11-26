/**
 * 
 */

define([
    'app/models/pais',
    'configuration'
], function(Pais, config){

	var Paises = Backbone.Collection.extend({
		url: config.baseUrl + "rest/paises",
		model: Pais,
		id: "codigo",
		comparator: function(model){
			return model.codigo;
		},
		refresh: function () {
			this.fetch();
			setTimeout(this.refresh.bind(this), this.wait);
		}
	});
	return Paises;
});
