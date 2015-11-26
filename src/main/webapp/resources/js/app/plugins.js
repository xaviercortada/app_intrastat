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
	$(".selectbox").find('img').remove();
	$('.selectbox').find('.pais-default').remove();
	$('.selectbox').find('.pais-name').remove();
	$('.selectbox').data('codigo', $(selected).data('codigo'));
	$(".selectbox").append($(selected).find('img').clone());
	$(".selectbox").append($(selected).find('.pais-name').clone());	        	
};



