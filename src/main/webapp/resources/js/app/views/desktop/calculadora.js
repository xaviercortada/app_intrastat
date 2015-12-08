/**
 * 
 */

define([ 'jquery', 
         'underscore',
         'backbone', 
     	'app/models/material',
		'text!templates/desktop/calculadora.html',
		'jqueryui'
         ], function($, _, Backbone, Material, htmlTemplate) {

	
	var CalculadoraView = Backbone.View.extend({
//		className: "modal fade",
//		  attributes: {
//		    tabindex: "-1",
//		    role: "dialog",
//		  },
		  
		initialize : function() {
			_.bindAll(this, "render");

		},

		show : function() {
			 $(document.body).append(this.render().el);
		},


	    events: {
	        'click .calcular': 'calcular',
	        'click .discard': 'discard',
	        'click .ui-button': 'login'
	      },

		discard : function() {
			this.$el.modal("close");
		},

		calcular : function() {	
			var kmnacionales = this.$el.find('#kmnacionales').val();
			var kmtotales = this.$el.find('#kmtotales').val();
			var transporte = this.$el.find('#transporte').val();
			
			var ve = this.calcularValorEstadistico( Number.parseInt(this.model.get('importe')),
					Number.parseInt(transporte),
					this.model.get('peso'),
					this.model.get('peso'),
					Number.parseInt(kmtotales),
					Number.parseInt(kmnacionales)
					);
			
			this.model.set('vestadistico', ve);
			
			this.trigger('done', ve);
			
			this.$el.dialog('close');
			
			Backbone.View.prototype.remove.bind(this)
		},
		
		calcularValorEstadistico : function(mercancia, 
				transporte, 
				pesototal, 
				pesopartida, 
				kmtotales,
				kmnacionales) {

			var valorestadistico = Math.round(mercancia + (transporte * kmnacionales) / kmtotales);
            if (kmtotales > kmnacionales && kmnacionales > 0) {
                return valorestadistico;
            } else {
            	return 0;
            }
            
		},

		
	    render: function () {
	        var template =  _.template(htmlTemplate);// _.template( $("#dialog_template").html(), {} );
	        this.$el.html(template).dialog({
	             title:"Valor estadístico",
	             autoOpen: true, 
	             closeOnEscape: false,
	             resizable: false,
	             draggable: true,
	             dialogClass: "no-close",
	             modal: true,
	             buttons: [
		             {
			             text: "Confirmar",
			             icons: {
			               primary: "ui-icon-heart"
			             },
			             dialogClass: "confirm"
		             }
	             ]
	         });
	        
	        this.delegateEvents(this.events);
	        
	        return this;
	     },  

		render2 : function() {
			this.$el.html(this.template(this.model)).modal();

			return this;
		},

	});

	return CalculadoraView;
});
