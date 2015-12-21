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

$.fn.treeView = function(){
	$(this).click(function(event){
		event.preventDefault();
		$(event.target).parents('li:first').find('>ul').toggle("hidden");
		$(event.target).parents('li:first').toggleClass("collapse");
		$(event.target).parents('li:first').toggleClass("expand");
		$(event.target).parents('li:first').show();
	})
	
	if($(this).html().length == 0){
		$(this).parents('li:first').toggleClass("collapse");		
	}else{
		$(this).parents('li:first').toggleClass("expand");				
	}
	$(this).parents('li:first').show();
}

