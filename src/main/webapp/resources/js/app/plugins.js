/**
 * Xavier Cortada
 */

$.fn.dropDown = function() {

	this.bind("toggle", function(){
	    if(selectbox.hasClass("expanded")){
	        $(this).trigger('hide');
	    }else{
	        $(this).trigger('show');
	    }
	}).bind('show', function(){
	    $(this).slideDown();
	}).bind('hide', function(){
	    $(this).slideUp();
	});
};

$.fn.selectBox =  function(){
	this.click(function(){
		$(".dropdown").show();
	});
};

selectPais = function(selected){
	var sel = $(selected).parent().prev(".selectbox")
	sel.find('img').remove();
	sel.find('.pais-default').remove();
	sel.find('.pais-name').remove();
	sel.data('codigo', $(selected).data('codigo'));
	sel.append($(selected).find('img').clone());
	sel.append($(selected).find('.pais-name').clone());	        	
};



