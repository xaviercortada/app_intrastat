/**
 * 
 */

define([
    	'utilities',
    	'bootstrap',
    	'router',
    	'app/models/factura',
    	'app/collections/facturas',
    	'app/views/desktop/factura',
	    'app/collections/proveedores',
    	'text!templates/desktop/facturas.html'
    ], function (
    		utilities,
    		bootstrap,
    		router,
    		Factura,
    		Facturas,
    		FacturaView,
    		Proveedores,
    		template) {
    		
    		var FacturasView = Backbone.View.extend({
    			tagName: "div",
    			className: 'container',
    			
			   initialize: function() {

			        this.template = template;
			        
			        var x = this;
			        
			        require(['router'],function(item){
				        x.router = item;			        	
			        });
			        
					this.proveedores = new Proveedores();

					this.proveedores.on("reset", this.fillProveedores,
							this);

			    },
			    
				fillProveedores : function() {

					var opts = this.proveedores.map(function(item) {
						var opt = item.toJSON();
						return "<option value='" + opt.id + "'>" + opt.name + "</option>";
					});
					this.$el.find("#proveedor").append(opts);
				},

			    
			    events: {
			    	'click .add' : 'create',
			    	'click .filter' : 'filter'
			    },
			    
			    filter : function(e){
			    	e.preventDefault();
			    	this.collection = new Facturas();
					this.collection.on("reset", this.fillTable,this);

					var flujo = $(".flujo:checked").val();
					var present = $(".present:checked").val();
			    	var codigo = $("#codigo").val();
			    	var fechaIni = $("#fechaIni").val();
			    	var fechaFin = $("#fechaFin").val();
			    	if(codigo){
		    		
			    		this.collection.findByCodigo(flujo, present, codigo);

			    	}else if(fechaIni && fechaFin){
				    	this.collection.findByFecha(flujo, present, fechaIni, fechaFin);
			    	}else{
				    	var idProveedor = $("#proveedor").val();
				    	this.collection.findByProveedor(flujo, present, idProveedor);
			    	}

			    },
			    

			    create: function(){
			    	this.remove();
			    	this.router.navigate('/addFactura', true);
			    	
			    },
    			render:function () {	
    				this.el.innerHTML = this.template;
    				
					this.proveedores.fetch({
						reset : true
					});
					
    				return this;
    			},
    			
    			fillTable : function(){
    				$("table > tbody > tr").remove();
    				var ul = $("table > tbody");
    				this.collection.forEach( function(item) {
    					ul.append(new FacturaView({model: item}).render().el);
    				});    		
    				
    			}

		});
    	return FacturasView;
    });
