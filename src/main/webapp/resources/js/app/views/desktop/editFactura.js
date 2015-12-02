/**
 * 
 */

define([
	'utilities',
	'bootstrap',
	'datepicker',
	'plugins',
	'router',
	'app/models/transporte',
	'app/collections/transportes',
	'app/models/proveedor',
	'app/collections/proveedores',
	'app/models/material',
	'app/collections/materiales',
	'app/models/pais',
	'app/collections/paises',
	'app/views/desktop/editMaterial',
	'text!../../../../templates/desktop/editFactura.html'
], function (
		utilities,
		bootstrap,
		datepicker,
		plugins,
		router,
		Transporte,
		Transportes,
		Proveedor,
		Proveedores,
		Material,
		Materiales,
		Pais,
		Paises,
		MaterialView,
		htmlTemplate) {
		
		var EditFacturaView = Backbone.View.extend({
			tagName: "div",
			
			initialize: function() {
				this.model.buttonCaption = "save";
		        this.template = _.template(htmlTemplate);
		        
		        var x = this;
		        
		        require(['router'],function(item){
			        x.router = item;			        	
		        });
		        
		        this.proveedores = new Proveedores();
		        
		        this.proveedores.on("reset", this.fillProveedores, this);
		        this.proveedores.fetch({
		        	reset : true
		        });

		        this.transportes = new Transportes();

		        this.transportes.on("reset", this.fillTransportes, this);
		        this.transportes.fetch({
		        	reset : true
		        });
		        	        
		        this.paises = new Paises();

		        this.paises.on("reset", this.fillPaises, this);
		        this.paises.fetch({
		        	reset : true
		        });
		    },
		    events: {
		    	'click .save' : 'update',
		    	'click .addMat' : 'addMat',
		    },		    
		    
		    fillProveedores : function(){
		    	var p = this.model.get('proveedor');
		    	var i = '-1';
		    	if(p != undefined) i = p.get('id');
		    	
				var opts = this.proveedores.map(function(item) {
					var opt = item.toJSON();
					var eti = "<option value='"+opt.id+"'";
					if(opt.id == i){
						eti = eti + "selected = 'true'";
					}
					return eti + ">"+opt.name+"</option>";
				});
				this.$el.find("#proveedor").append(opts);		
		    },
		    
	        fillTransportes : function(){
		    	var p = this.model.get('transporte');
		    	var i = '-1';
		    	if(p != undefined) i = p.get('codigo');

				var opts = this.transportes.map(function(item) {
					var opt = item.toJSON();
					var eti = "<option value='"+opt.codigo+"'";
					if(opt.codigo == i){
						eti = eti + "selected = 'true'";
					}
					return eti + ">"+opt.name+"</option>";
				});
				this.$el.find("#transporte").append(opts);
	        },
	        
	        
	        fillPaises : function(){
		    	var p = this.model.get('pais');
		    	var i = '-1';
		    	if(p != undefined) i = p.get('codigo');

				var opts = this.paises.map(function(item) {
					var opt = item.toJSON();
					
					var img = $('<img>', {src: 'resources/img/flags/16/'+opt.sigla+'.png'});
					var x = $('<li>', {'data-codigo': opt.codigo});
					x.append(img);
					x.append('<span class="pais-name">'+opt.name+'</span>');

					if(opt.codigo == i){
						selectPais(x);
					}

					return x;//eti + ">"+opt.name+"</option>";
				});
				$(".dropdown-paises").append(opts);
				$(".dropdown").dropDown();
				
				$(".dropdown").hide();

				
				$("li").click(function(){
					selectPais(this);
					$(".dropdown").trigger('hide');
				});
				
				$(".selectbox").click(function(){
					$(".dropdown-paises").show();
				});

	        },
	        
	        addMat :function(){
	        	var mat = new Material();
	        	this.model.get("materiales").push(mat);
				var tb = $("#tb_materiales");
				var view = new MaterialView({model: mat, index: 1});
				view.render();
				tb.append(view.el);

	        },

		    update: function(){
		    	var fecha = new Date($("#fecha").val());
		    	this.model.set({
		    		name:$("#name").val(),
		    		codigo: $("#codigo").val(),
		    		entrega: $("#entrega").val(),
		    		fecha: fecha,
		    		proveedor: {id: $("#proveedor").val()},
		    		transporte: {codigo: $("#transporte").val()},
		    		pais: {codigo: $('.selectbox').data('codigo')}
		    	});
		    	
		    	var items = new Materiales();
		    	$(".materialItem").each(function(i, e){
		    		items.add(Material.findOrCreate({
						"id" : $(e).find("#id").val(),
						"codigo": "",
						"importe": $(e).find("#importe").val(),
						"peso": $(e).find("#peso").val(),
						"unidades" : $(e).find("#unidades").val(),
						"nomenclature" : {code : $(e).find("#nomenclature").val()}
		    		}))
		    	});
		    	
		    	this.model.set({
		    		materiales : items
		    	});
		    	
		    	var router = this.router;
		    	
		    	this.model.save(null,{
		    			error: function(model, response){
		    				console.log(response.responseText);
		    				router.navigate('/', true);
		    			},
		    			success: function(model, response){
		    				router.navigate('/facturas', true);
		    			}
		    	});
		    	
				this.remove();
				this.unbind();

				return false;
		    },
		    
			render:function () {

				this.$el.html(this.template({
					model : this.model.toJSON(),
				}));
				
				//this.el.innerHTML = this.template;
				this.$el.find("#tb_materiales tbody tr").remove();
				var body = this.$el.find("#tb_materiales tbody");
				this.model.get("materiales").forEach( function(item) {
					body.append(new MaterialView({model: item}).render().el);
				});    				 				

				
				this.$el.find('#fecha').datepicker({
						format: 'mm-dd-yyyy'
				});
				
				//this.$el.find("#pais").msDropDown();
				
				var entrega = this.model.get('entrega');
				this.$el.find('#entrega option').filter(function(index, e){
					return e.value == entrega;
				}).prop("selected", true);
				
				return this;
		},
	});
	return EditFacturaView;
});
