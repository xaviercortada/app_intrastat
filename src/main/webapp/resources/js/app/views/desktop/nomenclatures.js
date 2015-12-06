/**
 * 
 */

define([
    	'utilities',
    	'bootstrap',
    	'app/collections/nomenclatures',
    	'app/collections/nomenclatures_p',
    	'app/collections/capitulos',
    	'app/models/favorito',
    	'app/views/desktop/nomenclature',
    	'text!../../../../templates/desktop/nomenclatures.html'
    ], function (
    		utilities,
    		bootstrap,
    		Nomenclatures,
    		NomenclaturesP,
    		Capitulos,
    		Favorito,
    		NomenclatureView,
    		template) {
    		
    		var NomenclaturesView = Backbone.View.extend({
    			tagName: "div",
    			className: 'container',
    			
			   initialize: function() {

			        this.template = template;
			        
			        this.capitulo = -1;
			        
			        this.nomenclatures = new Nomenclatures();
			        //this.nomenclatures.level = 1;
			        
			        this.nomenclatures.on("reset", this.fillNomenclatures, this);
			        this.nomenclatures.findByLevel(1);

			   },
			    
			    fillNomenclatures : function(){		    	
					var opts = this.nomenclatures.map(function(item) {
						var opt = item.toJSON();
						var eti = "<option value='"+opt.code+"'";
						return eti + ">"+opt.description+"</option>";
					});
					this.$el.find("#seccion").append(opts);	
					
			    },

			    fillCapitulos : function(){		    	
					var opts = this.capitulos.map(function(item) {
						var opt = item.toJSON();
						var eti = "<option value='"+opt.code+"'";
						return eti + ">"+opt.description+"</option>";
					});
					this.$el.find("#capitulo").append(opts);		
			    },
			    
			    events: {
			    	'change #seccion' : 'sectionSelected',
			    	'change #capitulo' : 'capituloSelected',
			    	'click .search' : 'search',			    	
			    	'click .favorito' : 'addFavorito',
			    	'click .pageno' : 'goToPage',
			    	'click .next' : 'nextPage',
			    	'click .prev' : 'prevPage'
			    },
			    
			    sectionSelected : function(e){
			    	$("#capitulo option[value!='-1']").remove();
			        this.capitulos = new Nomenclatures();
			        //this.capitulos.codigo = e.target.value;
			        
			        this.capitulos.on("reset", this.fillCapitulos, this);
//			        this.capitulos.fetch({
//			        	reset : true
//			        });
			        
			        this.capitulos.findByCapitulo(e.target.value);
			    	
			    },
			    
			    capituloSelected : function(e){
			    	this.capitulo = e.target.value;
			    },
			    
			    search : function(e){
			    	$(".table tbody tr").remove();
    				$(".pagination .pageno").remove();

    				this.items = new NomenclaturesP();
			    	this.items.on("reset", this.fillItems, this);
			    	
			    	var q = $("#texto").val();
			    	if(q.length < 5){
			            $group = $(e.target).closest('.form-group');				        
				        $group.addClass('has-error');
				        $group.find('.help-block').html('texto de busqueda minimo 5 caracteres').removeClass('hidden');
			    	}else{
			            $group = $(e.target).closest('.form-group');				        
				        $group.removeClass('has-error');
				        $group.find('.help-block').html('').addClass('hidden');

				    	this.items.queryParams.texto = q;
				    				    	
				    	if(this.capitulo == -1){
				    		//this.items.search($("#texto").val());
				    		this.items.getFirstPage({reset : true});
				    	}else{
				    		//this.items.findByTexto(this.capitulo, $("#texto").val());
				    		this.items.getFirstPage();
				    	}
			    	}
			    },
			    
			    goToPage : function(e){
			    	var pageEl = e.target;
			    	var page = parseInt($(pageEl).attr('data-page'));
			    	this.items.getPage(page, {reset : true});
			    },
			    
			    nextPage : function(e){
			    	var x = $(e.target);
			    	if(!x.closest("li").hasClass('disabled')){
				    	this.items.getNextPage({reset : true});			    		
			    	}
			    },

			    prevPage : function(e){
			    	var x = $(e.target);
			    	if(!x.closest("li").hasClass('disabled')){
			    		this.items.getPreviousPage({reset : true});
			    	}
			    },

			    addFavorito : function(e){
			    	e.preventDefault();
			    	var code = $(e.target).data('codigo');
			    	var found = this.items.find(function(item){
			            return (item.get('code')) == code;
			    	});
			    	var favorito = Favorito.findOrCreate(found.toJSON());

			    	favorito.save();
			    },

			    fillItems : function(){
			    	$("table tbody tr").remove();
			    	var ul = this.$el.find("table");
    				this.items.forEach( function(item) {
    					ul.append(new NomenclatureView({model: item}).render().el);
    				});  
    				
    				$(".pagination .pageno").remove();
    				var iniPage = Math.max(0, this.items.state.currentPage - 5);
    				var totalPages = Math.ceil(this.items.state.totalRecords / this.items.state.pageSize);
    				var pages = Math.min(10, totalPages) - 1;
    				for(var i=iniPage; i <= (iniPage+pages); i++){
    					var li = $("<li/>",{class: "pageno"}).append($("<a/>").attr("data-page",i).text(i));
    					if(i == this.items.state.currentPage){
    						li.addClass("active");
    					}
    					$(".pagination .next").before(li);    					
    				}
					$(".prev").addClass('disabled');
					$(".next").addClass('disabled');
    				if(this.items.state.currentPage > 0){
    					$(".prev").removeClass('disabled');
    				}
    				if(this.items.state.currentPage < pages){
    					$(".next").removeClass('disabled');
    				}

			    },

    			render:function () {	
    				this.el.innerHTML = this.template;
    				var ul = this.$el.find("table");
    				this.collection.forEach( function(item) {
    					ul.append(new NomenclatureView({model: item}).render().el);
    				});    				 				
    				return this;
    		}

		});
    	return NomenclaturesView;
    });
