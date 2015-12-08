/**
 * 
 */

define([ 'utilities', 
         'bootstrap', 
         'app/views/desktop/confirmModal',
         'text!templates/desktop/factura.html' ], function(
		utilities, bootstrap, ConfirmModalView, htmlTemplate) {

	var FacturaView = Backbone.View.extend({
		tagName : "tr",

		initialize : function() {
			this.template = _.template(htmlTemplate);
		},
		events : {
			'click .delete' : 'destroy'
		},

		destroy : function(e) {
			e.preventDefault();
			var confirmModal = new ConfirmModalView();
			
			this.listenTo(confirmModal, "confirm", this.remove);
			
			confirmModal.render();//show();

			// this.model.destroy();
			// this.remove();
		},

		remove : function() {
//			this.model.destroy();
//
//			this.$el.fadeOut(Backbone.View.prototype.remove.bind(this));
			return false;
		},

		render : function() {
			// this.$el[0].setAttribute("href",'rest/categories/'+this.model.id);
			this.$el.html(this.template(this.model.toJSON()));
			return this;
		},
	});
	return FacturaView;
});
