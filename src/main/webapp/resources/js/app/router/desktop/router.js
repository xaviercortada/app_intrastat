/**
* A module for the router of the desktop application
*/


define("router", [
	'jquery',
	'underscore',
	'configuration',
	'utilities',
	'app/models/category',
	'app/collections/categories',
	'app/views/desktop/editCategory',
	'app/views/desktop/categories',
	'app/models/proveedor',
	'app/collections/proveedores',
	'app/views/desktop/editProveedor',
	'app/views/desktop/proveedores',
	'app/models/factura',
	'app/collections/facturas',
	'app/views/desktop/editFactura',
	'app/views/desktop/facturas',
	'app/views/desktop/home',
	'text!../templates/desktop/main.html'],function ($,
			_,
			config,
			utilities,
			Category,
			Categories,
			EditCategoryView,
			CategoriesView,
			Proveedor,
			Proveedores,
			EditProveedorView,
			ProveedoresView,
			Factura,
			Facturas,
			EditFacturaView,
			FacturasView,
			HomeView,
			MainTemplate) {
				$(document).ready(new function() {
					utilities.applyTemplate($('body'), MainTemplate)
})


/**
* The Router class contains all the routes within the application -
* i.e. URLs and the actions that will be taken as a result.
*
* @type {Router}
*/
var Report = Backbone.Model.extend({
	urlRoot: config.baseUrl + 'rest/report',
	idAttribute: "periodo"

});

var Router = Backbone.Router.extend({
	initialize: function() {
		Backbone.history.start();
	},
	routes:{
		"":"home",
		"about":"home",
		"categories":"categories",
		"category/:id":"category",
		"addCategory" : "addCategory",
		"proveedores":"proveedores",
		"proveedor/:id":"proveedor",
		"addProveedor" : "addProveedor",
		"facturas":"facturas",
		"factura/:id":"factura",
		"addFactura" : "addFactura",
		"report" : "report"
	},
	home : function(){
		//utilities.viewManager.showView(new HomeView({el:$("#content")}));
		var homeView = new HomeView();
		
		$('#content').empty();

		$('#content').append( homeView.render().$el );
		
	},
	
	categories: function(){
		var categories = new Categories();
		
		var categoriesView = new CategoriesView({
			collection : categories		
		});

		//categories.fetch();

		$('#content').empty();
	    
		categories.on("reset",
			function(){
				$('#content').append( categoriesView.render().$el );
		}).fetch({
			reset : true
		});
	},
	
	category : function(id){

		var category = Category.findOrCreate({id: id});
		
		var editCategoryView = new EditCategoryView({
			model : category
		});

		$('#content').empty();
		
		category.fetch({
		    success: function (bookResponse) {
				$('#content').append( editCategoryView.render().$el );
		        console.log("Found the book: " + bookResponse.get("codigo"));
		    }
		});

		category.on("reset",
			function(){
				$('#content').append( editCategoryView.render().$el );
		}).fetch({
			reset: true,
			error: function(){
				utilities.displayAlert("Failed to retrive categories from the server");
			}
		});
	
	},
	
	addCategory : function(){
		var category = new Category();			

		var editCategoryView = new EditCategoryView({
			model : category
			//el:$("#content")
		});
		
		$('#content').empty();

		$('#content').append( editCategoryView.render().$el );
		
	},
	
	proveedores: function(){
		
		var proveedores = new Proveedores();
		
		var proveedoresView = new ProveedoresView({
			collection : proveedores		
		});

		//proveedores.fetch();

		$('#content').empty();
	    
		proveedores.on("reset",
			function(){
				$('#content').append( proveedoresView.render().$el );
		}).fetch({
			reset : true
		});
	},
	
	proveedor : function(id){
		var proveedor = Proveedor.findOrCreate({id: id});
		
		var editProveedorView = new EditProveedorView({
			model : proveedor
		});

		$('#content').empty();
		
		proveedor.fetch({
		    success: function (bookResponse) {
				$('#content').append( editProveedorView.render().$el );
		        console.log("Found the book: " + bookResponse.get("codigo"));
		    }
		});

		proveedor.on("reset",
			function(){
				$('#content').append( editProveedorView.render().$el );
		}).fetch({
			reset: true,
			error: function(){
				utilities.displayAlert("Failed to retrive categories from the server");
			}
		});
	
	},
	
	addProveedor : function(){
		var proveedor = new Proveedor();			

		var editProveedorView = new EditProveedorView({
			model : proveedor
			//el:$("#content")
		});
		
		$('#content').empty();

		$('#content').append( editProveedorView.render().$el );
		
	},
	facturas: function(){
		
		var facturas = new Facturas();
		
		var facturasView = new FacturasView({
			collection : facturas		
		});

		//facturas.fetch();

		$('#content').empty();
	    
		facturas.on("reset",
			function(){
				$('#content').append( facturasView.render().$el );
		}).fetch({
			reset : true
		});
	},	
	factura : function(id){
		var factura = Factura.findOrCreate({id: id});
		
		var editFacturaView = new EditFacturaView({
			model : factura
		});

		$('#content').empty();
		
		$('#content').append( editFacturaView.render().$el );

	},
	
	addFactura : function(){
		var factura = new Factura();			

		var editFacturaView = new EditFacturaView({
			model : factura
			//el:$("#content")
		});
		
		$('#content').empty();

		$('#content').append( editFacturaView.render().$el );
		
	},
		
	report: function(){
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'rest/report/1', true);
		xhr.responseType = 'blob';
		 
		xhr.onload = function(e) {
		  if (this.status == 200) {
		    // get binary data as a response
		    var blob = this.response;
            var a = document.createElement('a');
            a.style = 'display : none';
    		//var blob = new Blob([data.content], {type : 'application/vnd.oasis.opendocument.spreadsheet'});
            var objectUrl = window.URL.createObjectURL(blob);
            a.href = objectUrl;
            a.download = 'intrastat.xls';
            a.click();
            window.URL.revokeObjectURL(objectUrl);
		  }
		};
		
		 
		xhr.send();
	}
	
});

// Create a router instance
var router = new Router();
	return router;
});
