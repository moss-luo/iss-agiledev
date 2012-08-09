(function( $ ) {
$.widget("ui.upload", {
	options: {
		field:'photo',
		url:'list',
		title:'',
		filters:[{title:'图像文件',extensions:'jpg,gif,png'}],
		path:'',
		callback:null
	},
	_create:function(){

		var self = this,
		options = self.options,
		uiUploadPanel = (self.uiUploadPanel = $('<div></div>'))
		.appendTo(document.body)
		.hide()
		.addClass("agiledev-panel agiledev-upload-panel")
		.html('<div>您的浏览器不支持文件上传.</div>');
		
		if(self.element.get(0).tagName == "BUTTON" || self.element.get(0).tagName == "SPAN"){
			uiUploadHandle = (self.uiUploadHandle = self.element)
			uiUploadHandle.text("浏览");
		}else{
			uiUploadHandle = (self.uiUploadHandle = $('<span class="_uploadButton">浏览</span>').insertAfter(self.element))
		}
			
	},
	_init:function(){
		var self = this,
		options = self.options,
		uploadConfig = {
				runtimes :($.browser.msie?'flash,html5,html4':'html5,html4'),
				url : options.url,
				file_data_name : options.field,
				unique_names : true,
				multiple_queues:true,
				multipart_params:{_r:options.path},
				filters : options.filters,
				flash_swf_url : '../resources/agiledev/plupload/js/plupload.flash.swf',
				callback:function(file){
							this.bind('FileUploaded', function(uploader, file, response) {
								!!options.callback?options.callback.call(self,response):"";
							});
							this.trigger("FileUploaded");
						}
		};
		$("<link rel='stylesheet' href='../resources/agiledev/plupload/css/plupload.queue.css' type='text/css' media='screen'/>").insertBefore($("link:eq(0)"));
		$.getScript("../resources/agiledev/plupload/src/javascript/plupload.js",function(){
			$.getScript("../resources/agiledev/plupload/src/javascript/plupload.flash.js",function(){
				$.getScript("../resources/agiledev/plupload/src/javascript/plupload.html4.js",function(){
					$.getScript("../resources/agiledev/plupload/src/javascript/plupload.html5.js",function(){
						$.getScript("../resources/agiledev/plupload/src/javascript/jquery.plupload.queue.js",function(){
							$.getScript("../resources/agiledev/plupload/src/javascript/i18n/zh_CN.js",function(){
								self.uiUploadHandle.click(function(){
									var uiUploadPanel = (self.uiUploadPanel = $('<div></div>'))
									.appendTo(document.body)
									.hide()
									.addClass("agiledev-panel agiledev-upload-panel")
									.html('<div>您的浏览器不支持文件上传.</div>');
									uiUploadPanel.show().find("div").pluploadQueue(uploadConfig)
									.end().dialog({
													title:options.title||"upload",
													closable:true,
													onClose:function(){
														self.close();
													}
											});
								});
							});
						});
					});
				});
			});
		});
		
	},
	close:function(){
		//this.uiUploadPanel.closest(".window").remove();
	}
});
})( jQuery );