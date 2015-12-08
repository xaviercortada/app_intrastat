/**
 * 
 */

define(
		[ 'utilities', 'bootstrap', 'plugins', 'router',
				'app/models/transporte', 'app/collections/transportes',
				'app/models/transaccion', 'app/collections/transacciones',
				'app/models/proveedor', 'app/collections/proveedores',
				'app/models/material', 'app/collections/materiales',
				'app/models/pais', 'app/collections/paises',
				'app/models/provincia', 'app/collections/provincias',
				'app/views/desktop/editMaterial',
				'text!../../../../templates/desktop/editFactura.html',
				'validation', 'jqueryui' ],
		function(utilities, bootstrap, plugins, router, Transporte,
				Transportes, Transaccion, Transacciones, Proveedor,
				Proveedores, Material, Materiales, Pais, Paises, Provincia,
				Provincias, MaterialView, htmlTemplate) {

			var EditFacturaView = Backbone.View
					.extend({
						tagName : "div",

						initialize : function() {
							this.model.buttonCaption = "save";
							this.template = _.template(htmlTemplate);

							Backbone.Validation.bind(this);

							var x = this;

							require([ 'router' ], function(item) {
								x.router = item;
							});

						},

						helpers : function() {

							this.model
									.bind(
											'remove:materiales',
											function() {
												console
														.log("Fired emp > remove:dependents...");
											});

							this.provincias = new Provincias();

							this.provincias.on("reset", this.fillProvincias,
									this);
							this.provincias.fetch({
								reset : true
							});

							this.proveedores = new Proveedores();

							this.proveedores.on("reset", this.fillProveedores,
									this);
							this.proveedores.fetch({
								reset : true
							});

							this.transportes = new Transportes();

							this.transportes.on("reset", this.fillTransportes,
									this);
							this.transportes.fetch({
								reset : true
							});

							this.transacciones = new Transacciones();

							this.transacciones.on("reset",
									this.fillTransacciones, this);
							this.transacciones.fetch({
								reset : true
							});

							this.paises = new Paises();

							this.paises.on("reset", this.fillPaises, this);
							this.paises.fetch({
								reset : true
							});
						},

						events : {
							'click .save' : 'update',
							'click .addMat' : 'addMat',
							'change .flujo' : 'flujoChanged'
						},

						flujoChanged : function(e) {
							var flujo = $(e.target);
							this.fillFlujo(flujo);
						},

						fillFlujo : function(flujo) {
							$("#regimen option[value!='-1']").remove();
							var sel = this.$el.find("#regimen");

							if (flujo.val() == 'I') {
								sel
										.append("<option value='1'>Llegadas de mercancías comunitarias con destino final en el Estado miembro de introducción</option>");
								sel
										.append("<option value='2'>Llegadas temporales de mercancías comunitarias para ser reexpedidas al Estado miembro de procedencia o a otro Estado miembro, en el mismo estado en que llegaron</option>");
								sel
										.append("<option value='3'>Llegadas temporales de mercancías comunitarias para ser reexpedidas al Estado miembro de procedencia o a otro Estado miembro, después de sufrir una operación de transformación</option>");
								sel
										.append("<option value='4'>Llegada de mercancías comunitarias, devueltas en el mismo estado en el que fueron previamente expedidas al Estado miembro de procedencia o a otros Estados miembros</option>");
								sel
										.append("<option value='5'>Llegada de mercancías comunitarias, devueltas después de haber sufrido una operación de reparación o transformación, previamente expedidos al Estado miembro de procedencia o a otro Estado miembro</option>");
							} else {
								sel
										.append("<option value='1'>Salida de mercancías comunitarias con destino final en el Estado miembro de destino</option>");
								sel
										.append("<option value='2'>Salida temporal de mercancías comunitarias para ser reintroducidas con posterioridad desde el Estado miembro de destino o desde otro Estado miembro en el mismo estado en que son expedidas</option>");
								sel
										.append("<option value='3'>Salida temporal de mercancías comunitarias para ser reintroducidas con posterioridad, desde el Estado miembro de destino o desde otro Estado miembro después de haber sufrido una operación de reparación o transformación</option>");
								sel
										.append("<option value='4'>Salida de mercancías comunitarias, que se devuelven en el mismo estado en el que previamente llegaron procedentes del Estado miembro de destino o procedentes de otro Estado miembro</option>");
								sel
										.append("<option value='5'>Salida de mercancías comunitarias, que se devuelven después de haber sufrido una operación de transformación, previamente recibidas del Estado miembro de destino o de otro Estado miembro</option>");
							}

							var x = this.model.get("regimen");
							if (x) {
								sel.find("option").filter(function() {
									return this.value == x;
								}).prop('selected', true);
							}
						},

						fillProvincias : function() {
							var p = this.model.get('provincia');
							var i = '-1';
							if (p != undefined)
								i = p.get('codigo');

							var opts = this.provincias.map(function(item) {
								var opt = item.toJSON();
								var eti = "<option value='" + opt.codigo + "'";
								if (opt.codigo == i) {
									eti = eti + "selected = 'true'";
								}
								return eti + ">" + opt.name + "</option>";
							});
							this.$el.find("#provincia").append(opts);
						},

						fillProveedores : function() {
							var p = this.model.get('proveedor');
							var i = '-1';
							if (p != undefined)
								i = p.get('id');

							var opts = this.proveedores.map(function(item) {
								var opt = item.toJSON();
								var eti = "<option value='" + opt.id + "'";
								if (opt.id == i) {
									eti = eti + "selected = 'true'";
								}
								return eti + ">" + opt.name + "</option>";
							});
							this.$el.find("#proveedor").append(opts);
						},

						fillTransportes : function() {
							var p = this.model.get('transporte');
							var i = '-1';
							if (p != undefined)
								i = p.get('codigo');

							var opts = this.transportes.map(function(item) {
								var opt = item.toJSON();
								var eti = "<option value='" + opt.codigo + "'";
								if (opt.codigo == i) {
									eti = eti + "selected = 'true'";
								}
								return eti + ">" + opt.name + "</option>";
							});
							this.$el.find("#transporte").append(opts);
						},

						fillTransacciones : function() {
							var p = this.model.get('transaccion');
							var i = '-1';
							if (p != undefined)
								i = p;

							var opts = this.transacciones.map(function(item) {
								var opt = item.toJSON();
								if (opt.grupo) {
									var eti = "<optgroup label='" + opt.codigo
											+ " - " + opt.texto + "'/>";

								} else {
									var eti = "<option value='" + opt.id + "'";
									if (opt.id == i) {
										eti = eti + "selected = 'true'";
									}
									eti += ">" + opt.codigo + " - " + opt.texto
											+ "</option>";
								}
								return eti;
							});
							this.$el.find("#transaccion").append(opts);
						},

						fillPaises : function() {
							var opts = this.paises.map(function(item) {
								var opt = item.toJSON();

								var img = $('<img>', {
									src : 'resources/img/flags/16/' + opt.sigla
											+ '.png'
								});
								var x = $('<li>', {
									'data-codigo' : opt.codigo
								});
								x.append(img);
								x.append('<span class="pais-name">' + opt.name
										+ '</span>');
								return x;// eti + ">"+opt.name+"</option>";
							});
							$(".dropdown-paises").append(opts);
							$(".dropdown").dropDown();

							$(".dropdown").hide();

							$("li").click(function() {
								selectPais(this);
								$(this).parent().trigger('hide');
							});

							$(".selectbox").click(function() {
								$(this).next().show();
							});

							var p = this.model.get('pais');
							$("#pais").next('ul').find('li').filter(
									function() {
										return $(this).data('codigo') == p
												.get('codigo');
									}).trigger("click");

						},

						addMat : function() {
							var mat = new Material();
							this.model.get("materiales").push(mat);
							var tb = $("#tb_materiales");
							var view = new MaterialView({
								model : mat,
								index : 1
							});
							view.render();
							tb.append(view.el);

						},
						
						removeMat : function(item){
							console.log("Fired emp > remove:dependents...");	
						},

						update : function() {
							var fecha = new Date($("#fecha").val());
							this.model.set({
								flujo : $(".flujo:checked").val(),
								codigo : $("#codigo").val(),
								entrega : $("#entrega").val(),
								fecha : fecha,
								proveedor : {
									id : $("#proveedor").val()
								},
								transporte : {
									codigo : $("#transporte").val()
								},
								pais : {
									codigo : $('#pais').data('codigo')
								},
								transaccion : $("#transaccion").val(),
								regimen : $("#regimen").val()
							});

							this.model.set({
								origen : null
							})
							var po = $('#pais_origen').data('codigo');
							if (po != undefined)
								this.model.set({
									origen : {
										codigo : po
									}
								});

							var prov = $('#provincia').val();
							if (prov != -1)
								this.model.set({
									provincia : {
										codigo : prov
									}
								});

							var items = new Materiales();
							$(".materialItem").each(function(i, e) {
								items.add(Material.findOrCreate({
									"id" : $(e).find("#id").val(),
									"codigo" : "",
									"importe" : $(e).find("#importe").val(),
									"peso" : $(e).find("#peso").val(),
									"unidades" : $(e).find("#unidades").val(),
									"vestadistico" : $(e).find("#vestadistico").val(),
									"nomenclature" : {
										code : $(e).find("#nomenclature").val()
									}
								}))
							});

							this.model.set({
								materiales : items
							});

							var router = this.router;

							if (this.model.isValid(true)) {
								this.model.save(null, {
									error : function(model, response) {
										console.log(response.responseText);
										router.navigate('/', true);
									},
									success : function(model, response) {
										router.navigate('/partidas', true);
									}
								});

								Backbone.Validation.unbind(this);

								this.remove();
								this.unbind();
							}

							return false;
						},

						render : function() {

							this.$el.html(this.template({
								model : this.model.toJSON(),
							}));

							var vflujo = this.model.get("flujo");
							var sel = this.$el.find(".flujo").filter(
									function() {
										return this.value == vflujo;
									});
							if (sel) {
								sel.prop('checked', true);
								this.fillFlujo(sel);
							}

							// this.el.innerHTML = this.template;
							this.$el.find("#tb_materiales tbody tr").remove();
							var body = this.$el.find("#tb_materiales tbody");
							var that = this;
							this.model.get("materiales").forEach(
									function(item) {
										
										var childView = new MaterialView({
											model : item
										})

										that.listenTo(childView, "removeMat", that.removeMat);
										
										body.append(childView.render().el);
									});

							// this.$el.find('#fecha').datepicker( {
							// dateFormat: "yy-mm-dd"
							// });

							// this.$el.find("#pais").msDropDown();

							var entrega = this.model.get('entrega');
							this.$el.find('#entrega option').filter(
									function(index, e) {
										return e.value == entrega;
									}).prop("selected", true);

							return this;
						},

						close : function() {

							console.log('Kill: ', this);

							this.unbind(); // Unbind all local event bindings
							this.model.unbind('change', this.render, this); // Unbind
																			// reference
																			// to
																			// the
																			// model
							this.options.parent.unbind('close:all', this.close,
									this); // Unbind reference to the parent
											// view

							this.remove(); // Remove view from DOM

							delete this.$el; // Delete the jQuery wrapped
												// object variable
							delete this.el; // Delete the variable reference to
											// this node

						}

					});
			return EditFacturaView;
		});
