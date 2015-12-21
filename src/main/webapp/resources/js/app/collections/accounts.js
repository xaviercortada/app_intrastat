/**
 * 
 */

define([
    'app/models/account',
    'configuration'
], function(Account, config){

	var Accounts = Backbone.Collection.extend({
		url: config.baseUrl + "rest/accounts",
		model: Account,
		id: "id",
		comparator: function(model){
			return model.id;
		}
	});
	return Accounts;
});
