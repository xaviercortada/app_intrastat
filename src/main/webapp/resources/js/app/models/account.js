/**
 * 
 */

define([
    'app/models/user',
    'configuration',
    'backbone',
    'relational'
    ],function(User, config){
		var Account = Backbone.RelationalModel.extend({
			urlRoot: config.baseUrl + 'rest/accounts',
			idAttribute: "id",
			autoFetch: true,
			defaults:{
				"id" : undefined,
				"user": new User(),
				"userName":"",
				"password":""
			},
			relations: [
				{
					type: Backbone.HasOne,
					key: 'user',
					relatedModel: User
				}
			],

			validation : {
				userName : {
					required : true,
					minLength: 5
				},
				password : {
					required : true,
					minLength: 5
				}
			},
	});
	
	return Account;
	
});