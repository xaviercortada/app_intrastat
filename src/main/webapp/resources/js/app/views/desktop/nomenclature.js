/**
 * 
 */

define([
	'utilities',
	'bootstrap',
	'app/models/favorito',
	'app/collections/nomenclatures_detail',
	'text!templates/desktop/nomenclature.html'
], function (
		utilities,
		bootstrap,
		Favorito,
		NomenclaturesD,
		htmlTemplate) {
		
		var NomenclatureView = Backbone.View.extend({
			tagName: "li",
			className: "tree",
			
			initialize: function() {
		        //this.template = _.template("<a class='list-group-item' href='rest/categories/{{id}}'>{{codigo}}</a>");
		        //this.template = template;//_.template("<td>{{id}}</td><td>{{codigo}}</td><td>{{name}}</td>");
		        this.template = _.template(htmlTemplate);
		        //this.render();
		    },
		    
		    events: {
		    	'click .delete' : 'destroy',
		    	'click .detail' : 'detail',
		    	'click .favorito' : 'addFavorito'
		    },
		    
		    addFavorito : function(e){
		    	e.preventDefault();
//		    	var code = $(e.target).data('codigo');
//		    	var found = this.items.find(function(item){
//		            return (item.get('code')) == code;
//		    	});
		    	$(e.target).attr('disabled', true);
		    	var favorito = Favorito.findOrCreate(this.model.toJSON());

		    	favorito.save();
		    },

		    
		    detail : function(e){
		    	e.preventDefault();
		    	var mat = $(e.target);
		    	
		    	this.childs = new NomenclaturesD();
		    	this.childs.on("reset", this.fillDetail, this);
		    	this.childs.search(mat.data('codigo'));
		    	
		    	mat.toggleClass("detail");
		    	
		    },
		    
		    fillDetail : function(){
		    	var x = this.$el;
		    	if(this.childs == undefined || this.childs.length == 0){
		    		x.addClass("collapse");
					x.removeClass("expand");
					x.find("a").unbind('click');
					x.find("a").click(function(e){
						e.preventDefault();						
					});
		    	}else{
			    	x.append('<ul>');
					this.childs.forEach( function(item) {
						x.find('ul').append(new NomenclatureView({model: item}).render().el);
					});  		    	
		    	}
		    },

		    	    
		    destroy: function(e){
		    	e.preventDefault();
		    	this.model.destroy();
		    	this.remove();		    	
		    } ,
		    
		    remove: function () {
				 this.$el.fadeOut(Backbone.View.prototype.remove.bind(this));
				 return false;
		    },
			    
			render:function () {
				//this.$el[0].setAttribute("href",'rest/categories/'+this.model.id);
				var item = this.model.toJSON();
				item.codigo = item.section;
				if(item.section.length == 0){
					item.codigo = item.codeCN8;
				}
				this.$el.html(this.template(item));
				
				this.$el.find("a").treeView();
				if(item.section.length == 0){
					this.$el.find("a").toggleClass('detail');
					this.$el.find("a").unbind('click');
					this.$el.find("a").click(function(e){
						e.preventDefault();						
					});

					this.$el.removeClass("expand");
					this.$el.addClass("collapse");
				}
				
				return this;
		},
	});
	return NomenclatureView;
});
