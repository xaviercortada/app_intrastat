/**
 * 
 */

define([
    'app/models/transporte',
    'configuration'
], function(Transporte, config){

	var Transportes = Backbone.Collection.extend({
		url: config.baseUrl + "rest/transportes",
		model: Transporte,
		id: "codigo",
		comparator: function(model){
			return model.codigo;
		},
		refresh: function () {
			this.fetch();
			setTimeout(this.refresh.bind(this), this.wait);
		}
	});
	return Transportes;
});
