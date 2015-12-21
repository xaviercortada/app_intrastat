/**
 * 
 */

define([
    'configuration',
    'backbone',
    'relational'
    ],function(config){
		var Person = Backbone.RelationalModel.extend({
			urlRoot: config.baseUrl + 'rest/users',
			idAttribute: "id",
			defaults:{
				"id" : undefined,
				"documento":"",
				"lastName":"",
				"firstName":"",
				"email":"",
			},
			validation : {
				firtName : {
					required : true,
					minLength: 5
				},
				lastName : {
					required : true,
					minLength: 5
				}
			}

	});
	
	return Person;
	
});