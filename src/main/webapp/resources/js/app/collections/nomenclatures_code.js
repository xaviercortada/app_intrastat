/**
 * 
 */

define([
    'app/models/nomenclature',
    'configuration',
    'paginator'
], function(Nomenclature, config){

	var Nomenclatures_p = Backbone.PageableCollection.extend({
		model: Nomenclature,
		url : config.baseUrl + "rest/nomenclatures/search/codigo/",
		
		texto : "xxxxx",

		 state: {
		    firstPage: 0,
		    currentPage: 2,
		    pageSize : 10,
		    totalRecords: 200
		  },
		  
		  queryParams: {

		    currentPage: "current_page",
		    pageSize: "page_size",
		    codigo: "codigo"
		  },
		  
		  parseState: function (resp, queryParams, state, options) {
			  
			    return {totalRecords: parseInt(options.xhr.getResponseHeader("X-total"))};
		  },
		  
		  comparator: function (model) { 
			  return model.get("code"); 
		  }
	});
	return Nomenclatures_p;
});
