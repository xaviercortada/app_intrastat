/**
 * 
 */

define([
    'app/models/favorito',
    'configuration'
], function(Favorito, config){

	var Favoritos = Backbone.Collection.extend({
		url: config.baseUrl + "rest/favoritos",
		model: Favorito,
		id: "code",
		
		findAll : function(){
			this.url =  config.baseUrl + "rest/favoritos/";
	        this.fetch({
	        	reset : true
	        });
		},

		comparator: function(model){
			return model.codigo;
		}
	});
	return Favoritos;
});
