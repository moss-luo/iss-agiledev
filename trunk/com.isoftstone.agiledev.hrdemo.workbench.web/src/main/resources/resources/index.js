
$(function(){
	var p = $('body').layout('panel','west').panel();
	setTimeout(function(){
		$('body').layout('collapse','east');
	},0);
	
	$('#tt').tree({
		checkbox: false,
		url: 'mvc/menubar/get',
		onClick:function(node){
			$(this).tree('toggle', node.target);
			if($(node.text).attr("hh")==null || $.trim($(node.text).attr("hh"))=="")return;
			var has = false;
			$("ul.tabs").find('.tabs-title').each(function(i,o){
				if ($(this).text() == $(node.text).text()) {
					has = true;
				}
			});
			if (!has) {
				$('#tabs').tabs('add', {
					title: node.text,
					content:'<iframe height="483" width="100%" frameborder="0" scrolling="auto" allowtransparency="true" src="'+$(node.text).attr("hh")+'"></iframe>',
					closable: true,
					selected: true
				});
			}else{
				$('#tabs').tabs("select",node.text);
			}
		},
		onLoadSuccess:function(node,data){
			$("#loading").remove();
		}
	});
	
	
});