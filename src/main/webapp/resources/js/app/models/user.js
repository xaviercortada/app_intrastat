/**
 * 
 */

define([
    'configuration',
    'backbone',
    'relational'
    ],function(config){
		var User = Backbone.RelationalModel.extend({
			idAttribute: "id",
			defaults:{
				"id" : undefined,
				"firstName":"",
				"lastName":""
			},
			validation : {
				firstName : {
					required : true,
					minLength: 5
				},
				lastName : {
					required : true,
					minLength: 5
				}
			},
	});
	
	return User;
	
});