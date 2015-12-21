/**
 * 
 */

define([
    	'utilities',
    	'bootstrap',
    	'app/collections/nomenclatures',
    	'app/collections/nomenclatures_texto',
    	'app/collections/nomenclatures_code',
    	'app/collections/capitulos',
    	'app/views/desktop/nomenclature',
    	'text!templates/desktop/nomenclatures.html'
    ], function (
    		utilities,
    		bootstrap,
    		Nomenclatures,
    		NomenclaturesP,
    		NomenclaturesC,
    		Capitulos,
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
			    	'click .search' : 'searchTexto',			    	
			    	'click .search_codigo' : 'searchCodigo',			    	
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
			    
			    searchTexto : function(e){
    				this.items = new NomenclaturesP();
			    	this.items.on("reset", this.fillItems, this);
			    	
			    	var q = $("#texto").val();
			    	
			    	this.items.queryParams.texto = q;
			    	
			    	this.search(e, q);
			    },
			    
			    searchCodigo : function(e){
    				this.items = new NomenclaturesC();
			    	this.items.on("reset", this.fillItems, this);
			    	
			    	var q = $("#codigo").val();
			    	
			    	this.items.queryParams.codigo = q;
			    	
			    	this.search(e, q);
			    },

			    search : function(e, q){
			    	$(".table tbody tr").remove();
    				$(".pagination .pageno").remove();

			    	if(q.length < 4){
			            $group = $(e.target).closest('.form-group');				        
				        $group.addClass('has-error');
				        $group.find('.help-block').html('texto de busqueda minimo 5 caracteres').removeClass('hidden');
			    	}else{
			            $group = $(e.target).closest('.form-group');				        
				        $group.removeClass('has-error');
				        $group.find('.help-block').html('').addClass('hidden');

				    	if(this.capitulo == -1){
				    		this.items.getFirstPage({reset : true});
				    	}else{
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


			    fillItems : function(){
			    	
			    	
			    	$("ul li.tree").remove();
			    	var ul = this.$el.find("ul.tree");
    				this.items.forEach( function(item) {
    					ul.append(new NomenclatureView({model: item}).render().el);
    				});  
    				
    				$(".pagination .pageno").remove();
    				var iniPage = Math.max(0, this.items.state.currentPage - 5);
    				var totalPages = Math.ceil(this.items.state.totalRecords / this.items.state.pageSize);
    				var pages = Math.min(10, totalPages) - 1;
    				for(var i=iniPage; i <= (iniPage+pages); i++){
    					var li = $("<li/>",{class: "page pageno"}).append($("<a/>").attr("data-page",i).text(i));
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
