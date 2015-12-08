/**
 * 
 */

define(
		[ 'configuration', 'app/models/nomenclature', 'backbone', 'relational' ],
		function(config, Nomenclature, backbone, relational) {
			var Material = Backbone.RelationalModel.extend({
				relations : [ {
					type : Backbone.HasOne,
					key : 'nomenclature',
					relatedModel : Nomenclature
				} ],
				idAttribute : "id",
				defaults : {
					"id" : undefined,
					"codigo" : "",
					"importe" : 0,
					"peso" : 0,
					"unidades" : 0,
					"nomenclature" : {}
				},
				getCustomUrl : function(method) {
					switch (method) {
					case 'read':
						return config.baseUrl + 'rest/facturas/materiales'
								+ this.id;
						break;
					case 'create':
						return config.baseUrl + 'rest/facturas/materiales';
						break;
					case 'update':
						return config.baseUrl + 'rest/facturas/materiales'
								+ this.id;
						break;
					case 'delete':
						return config.baseUrl + 'rest/facturas/'+this.get('factura').get('id')+'/materiales/'
								+ this.id;
						break;
					}
				},

				sync : function(method, model, options) {
					options || (options = {});
					options.url = this.getCustomUrl(method.toLowerCase());

					// Lets notify backbone to use our URLs and do follow
					// default course
					return Backbone.sync.apply(this, arguments);
				}
			});

			return Material;

		});