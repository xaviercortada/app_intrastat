/**
* A module for the router of the desktop application
*/


define("router", [
	'jquery',
	'underscore',
	'configuration',
	'utilities',
	'app/models/nomenclature',
	'app/collections/nomenclatures',
	'app/views/desktop/nomenclatures',
	'app/models/proveedor',
	'app/collections/proveedores',
	'app/views/desktop/editProveedor',
	'app/views/desktop/proveedores',
	'app/models/factura',
	'app/models/account',
	'app/collections/accounts',
	'app/views/desktop/accounts',
	'app/views/desktop/editAccount',
	'app/collections/facturas',
	'app/views/desktop/editFactura',
	'app/views/desktop/facturas',
	'app/views/desktop/home',
	'app/views/desktop/register',
	'app/views/desktop/signin',
	'text!../templates/desktop/main.html'],function ($,
			_,
			config,
			utilities,
			Nomenclature,
			Nomenclatures,
			NomenclaturesView,
			Proveedor,
			Proveedores,
			EditProveedorView,
			ProveedoresView,
			Factura,
			Account,
			Accounts,
			AccountsView,
			EditAccountView,
			Facturas,
			EditFacturaView,
			FacturasView,
			HomeView,
			RegisterView,
			SigninView,
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
		"proveedores":"proveedores",
		"proveedor/:id":"proveedor",
		"account/:id":"account",
		"addProveedor" : "addProveedor",
		"partidas":"partidas",
		"factura/:id":"factura",
		"addFactura" : "addFactura",
		"report" : "report",
		"export" : "export",
		"nomenclatures" : "nomenclatures",
		"register" : "register",
		"signin" : "signin",
		"admin" : "accounts"
	},
	home : function(){
		//utilities.viewManager.showView(new HomeView({el:$("#content")}));
		var homeView = new HomeView();
		
		$('#content').empty();

		$('#content').append( homeView.render().$el );
		
	},
	
	nomenclatures: function(){
		var nomenclatures = new Nomenclatures();
		
		var nomenclaturesView = new NomenclaturesView({
			collection : nomenclatures		
		});

		$('#content').empty();
	    
//		nomenclatures.on("reset",
//			function(){
//				$('#content').append( nomenclaturesView.render().$el );
//		}).fetch({
//			reset : true
//		});
		
		$('#content').append( nomenclaturesView.render().$el );
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
	
	accounts: function(){
		
		var accounts = new Accounts();
		
		var accountsView = new AccountsView({
			collection : accounts		
		});

		//proveedores.fetch();

		$('#content').empty();
	    
		accounts.on("reset",
			function(){
				$('#content').append( accountsView.render().$el );
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
		
		$('#content').append( editProveedorView.render().$el );
	
	},
	
	account : function(id){
		var account = Account.findOrCreate({id: id});
		
	
		var editAccountView = new EditAccountView({
			model : account
		});

		$('#content').empty();
		
		$('#content').append( editAccountView.render().$el );
	
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
	partidas: function(){
		
		
		var facturasView = new FacturasView({
			collection : []		
		});

		$('#content').empty();
	    
		$('#content').append( facturasView.render().$el );
	},	
	
	factura : function(id){
		
		var factura = Factura.findOrCreate({id: id});
		
		var editFacturaView = new EditFacturaView({
			model : factura
		});

		$('#content').empty();

		factura.fetch({
		    success: function(response){
		    	console.log(response.toString());
				//$('#content').append( editFacturaView.render().$el );
		    	editFacturaView.helpers();
				$('#content').append( editFacturaView.render().$el );
		    }
		});
		
		//Backbone.Relational.store.unregister(factura);
		


	},
	
	addFactura : function(){
		var factura = new Factura();			

		var editFacturaView = new EditFacturaView({
			model : factura
			//el:$("#content")
		});
		
		$('#content').empty();

    	editFacturaView.helpers();

    	$('#content').append( editFacturaView.render().$el );
		
	},
	
	register: function(){
		var account = new Account();
		
		var modal = new RegisterView({
			model: account
		});
		
		modal.render();
		
	},
		
	signin: function(){
		var account = new Account();
		
		var modal = new SigninView({
			model: account
		});
		
		modal.render();
		
	},

	report: function(){
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'rest/report/excel/1', true);
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
	},
	
	export: function(){
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'rest/report/csv/1', true);
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
            a.download = 'intrastat.csv';
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
