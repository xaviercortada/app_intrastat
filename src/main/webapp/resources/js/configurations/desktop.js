/**
 * Shortcut alias definitions - will come in handy when declaring dependencies
 * Also, they allow you to keep the code free of any knowledge about library
 * locations and versions
 */

requirejs.config({
	baseUrl: "resources/js",
	paths: {
		jquery:'libs/jquery-2.0.3',
		jqueryui : 'libs/jquery-ui',
		underscore:'libs/underscore',
		text:'libs/text',
		bootstrap: 'libs/bootstrap',
		backbone: 'libs/backbone',
		relational: 'libs/backbone-relational',
		paginator: 'libs/backbone.paginator',
		validation: 'libs/backbone-validation-min',
		utilities: 'app/utilities',
		plugins: 'app/plugins',
		router:'app/router/desktop/router',
		templates: "../templates"
	},
	
	// We shim Backbone.js and Underscore.js since they don't declare AMD modules
	shim: {
		'backbone': {
			deps: ['jquery', 'underscore'],
			exports: 'Backbone'
		},
		
		'relational': {
			deps: ['backbone']
		},

		'underscore': {
			exports: '_'
		},
		
  	    'jqueryui':{
	        deps:['jquery'],
        },
        
        'paginator':{
        	deps:['backbone']
        },
        
        'validation':{
        	deps:['backbone']
        }
	}
});


define("initializer", ["jquery"],
	function ($) {
		// Configure jQuery to append timestamps to requests, to bypass browser caches
		// Important for MSIE
		$.ajaxSetup({cache:false});
		$('head').append('<link rel="stylesheet" href="resources/css/bootstrap.min.css"	type="text/css" media="all"/>');
		$('head').append('<link rel="stylesheet" href="resources/css/bootstrap-theme.css" type="text/css" media="all"/>');
		$('head').append('<link rel="stylesheet" href="resources/css/screen.css" type="text/css" media="all"/>');
		$('head').append('<link rel="stylesheet" href="resources/css/dropdown.css" type="text/css" media="all"/>');
		$('head').append('<link href="http://fonts.googleapis.com/css?family=Rokkitt" rel="stylesheet" type="text/css">');
		$('head').append('<link rel="stylesheet" href="resources/css/jquery-ui.css" type="text/css" media="all">');
		
	});

// // Now we load the dependencies
// This loads and runs the 'initializer' and 'router' modules. 
require([
	'initializer',
	'router',
	'validation'
	], function(){
			
		_.templateSettings = {
				interpolate: /\{\{(.+?)\}\}/g
		};
		
		_.extend(Backbone.Validation.callbacks, {
		    valid: function (view, attr, selector) {
		        var $el = view.$('[name=' + attr + ']'), 
		            $group = $el.closest('.form-group');
		        
		        $group.removeClass('has-error');
		        $group.find('.help-block').html('').addClass('hidden');
		    },
		    invalid: function (view, attr, error, selector) {
		        var $el = view.$('[name=' + attr + ']'), 
		            $group = $el.closest('.form-group');
		        
		        $group.addClass('has-error');
		        $group.find('.help-block').html(error).removeClass('hidden');
		    }
		});
	
	});


define("configuration", {
	//baseUrl : "http://localhost:8080/appintrastat/"
	baseUrl : "http://appintrastat-xaviercortada.rhcloud.com/"
});
