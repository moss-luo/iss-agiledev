(function($){
$.widget("ui.agiledevForm", {
	options: {
		field:[],
		autoShow:true,
		render:null,
		initUrl:null,
		className:'agiledev-dialog',
		submitUrl:null,
		buttons:{},
		beforeSubmit:function(){return true;},
		success:function(response){;}
	},
	_create:function(){
		var self = this,
			options = self.options,
			uiForm = $(self.uiForm = $("<form method='post' class='agiledev-form "+(options.autoShow?'':'agiledev-hide')+"' onkeydown='if(event.keyCode==13){return false;}'/>"))
					.appendTo(self.element);

		this.globalHtml = this.createField(this.options.className);
		this.uiForm.prepend(this.globalHtml.html);
		
		$.each(options.buttons,function(name,obj){
				var button = $('<a href="#" class="easyui-linkbutton" '+(obj.iconCls?'icon="'+obj.iconCls+'"':'')+'></a>')
					.text(name)
					.click(function() { obj.handler.apply(self, arguments); })
					.appendTo(uiForm);
				if ($.fn.linkbutton) {
					button.linkbutton();
				}
				
		});
	},
	_init:function(){
		this.renderField();
		this.initForm();
		this.createValidate();
	},
	submit:function(){
		var self = this,options = self.options;
		if(!options.beforeSubmit.call(self))return;

		$(self.uiForm).find(":input").each(function(){
			$(this).val(this.value.trim());
		});
		if($(self.uiForm).form('validate')){
			$.ajax({
				url:options.submitUrl,
				type:'post',
				beforeSend:function(xhr){
					xhr.setRequestHeader("agiledev-ajax-request-type",'submit-form');
				},
				data:self.uiForm.serialize(),
				dataType:'json',
				cache:false,
				success:function(d){
					options.success.call(self.uiForm,d);
				},
				error:function (xhr, textStatus, errorThrown) {
				    // 通常 textStatus 和 errorThrown 之中
				    // 只有一个会包含信息
				    this; // 调用本次AJAX请求时传递的options参数
				}
			});
		}
	},
	/**
	 * 根据field类型创建表单元素
	 */
	createField:function(className){
		this.uiForm.empty();
		var global = {html:'',hiddens : $([]),texts :$([]),passwords :$([]),comboboxes : $([]),combotree : $([]),uploads: $([]),
				datetimeboxes : $([]),dateboxes : $([]),textareas : $([]),selects : $([])},random = 0,self = this,
				fields = self.options.field;
		//global.html+='<table>';
		global.html+='<div class="agiledev '+className+'">';
		$.each(fields,function(i,f){
			if(f.field==null)return;
			if(f.formShow == false)return;
			random = self.guid();
			f._id = random;
			if(f.type!="hidden"){
				//global.html += ("<tr><td><div class='agiledev-form-item'>"+
				global.html += ("<div class='agiledev-form-item'>"+
						"<div class='agiledev-form-title'>"+f.title+":</div><div class='agiledev-form-element'>");
				if(f.type === "text" || f.type === "input" || !f.type){
					global.html +='<input '+(f.id?('id="'+f.id+'"'):'')+' class="agiledev-text" name="'+f.field+'" element-id="'+random+'" '+(f.value==null?'':'value="'+f.value+'"')+'/>';
					global.texts = global.texts.add(f);
				}else if(f.type=="password"){
					global.html +='<input '+(f.id?('id="'+f.id+'"'):'')+' type="password" class="agiledev-text" name="'+f.field+'" element-id="'+random+'"/>';
					global.passwords = global.passwords.add(f);
				}else if(f.type=="textarea"){
					global.html +='<textarea '+(f.id?('id="'+f.id+'"'):'')+' class="agiledev-textarea" name="'+f.field+'" element-id="'+random+'" rows="'+(f.rows?f.rows:3)+'" cols="'+(f.cols?f.cols:20)+'"/>';
					global.textareas = global.textareas.add(f);
				}else if(f.type=='combotree'){
					global.html +='<select '+(f.id?('id="'+f.id+'"'):'')+' class="easyui-combotree" name="'+f.field+'" element-id="'+random+'"/>';
					global.combotree = global.combotree.add(f);
				}else if(f.type=="combobox"){
					global.html +='<select '+(f.id?('id="'+f.id+'"'):'')+' class="agiledev-combobox" name="'+f.field+'" element-id="'+random+'"/>';
					global.comboboxes = global.comboboxes.add(f);
				}else if(f.type=="select"){
					global.html +='<select '+(f.id?('id="'+f.id+'"'):'')+' class="agiledev-select" name="'+f.field+'" element-id="'+random+'"/>';
					global.selects = global.selects.add(f);
				}else if(f.type=="upload"){
					global.html +='<input '+(f.id?('id="'+f.id+'"'):'')+' class="agiledev-upload" readonly="true" name="'+f.field+'" element-id="'+random+'"/>';
					global.uploads = global.uploads.add(f);
				}else if(f.type == "datetime"){
					global.html +='<input '+(f.id?('id="'+f.id+'"'):'')+' readonly="true" class="agiledev-datetime" name="'+f.field+'" element-id="'+random+'"/>';
					global.datetimeboxes = global.datetimeboxes.add(f);
				}else if(f.type == "date"){
					global.html +='<input '+(f.id?('id="'+f.id+'"'):'')+' readonly="true" class="agiledev-date" name="'+f.field+'" element-id="'+random+'"/>';
					global.dateboxes = global.dateboxes.add(f);
				}else if(f.type == "radio"){
					var radioData = $([]);
					if(f.data != null){
						radioData = f.data;
					}else if(f.url!=null){
						$.ajax({
							url:f.url,
							dataType:'json',
							async:false,
							success:function(d){
								$(d).each(function(i,o){
									radioData = radioData.add({valueField:o[f.valueField],textField:o[f.textField]});  
								});
							}
						});
					}else{
						throw new Error("radio:\""+f.field+"\" need configuration url or a parameter:data!ex:[{valueField:'1',textField:'男'},{valueField:'0',textField:'女'}]");
					}
					global = 
					f.formatter ? f.formatter.call(self,global,radioData) : function(global,radioData){
						global.html += '<ol>';
						$(radioData).each(function(i, o){
							random = self.guid();
							f._id = random;
							global.html += '<ul><input ' + (f.id ? ('id="' + f.id + '"') : '') + ' serId=' + i + ' class="agiledev-radio" type="radio" element-id="' + random + '" name="' + f.field + '" value="' + o.valueField + '"/>' + o.textField + "</ul>";
						});
						global.html += '</ol>';
						return global;
					}(global,radioData);
				}else if(f.type === "checkbox" || f.type === "checkBox"){
					var checkboxData = $([]);
					if(f.data != null){
						checkboxData = f.data;
					}else if(f.url!=null){
						$.ajax({
							url:f.url,
							dataType:'json',
							async:false,
							success:function(d){
								$(d).each(function(i,o){
									checkboxData = checkboxData.add({valueField:o[f.valueField],textField:o[f.textField]});  
								});
							}
						});
					}else{
						throw new Error("checkbox:\""+f.field+"\" need configuration url or a parameter:data!ex:[{valueField:'1',textField:'男'},{valueField:'0',textField:'女'}]");
					}
					global.html+='<ol>';
					$(checkboxData).each(function(i,o){
						random = self.guid();
						f._id = random;
						global.html +='<ul><input '+(f.id?('id="'+f.id+'"'):'')+' serId='+i+' class="agiledev-checkbox" type="checkbox" element-id="'+random+'" name="'+f.field+'" value="'+o.valueField+'"/>'+o.textField+"</ul>";
					});					
					global.html+='</ol>';
					
				}
				//global.html += "</div></div></td></tr>";
				global.html += "</div></div>";
			}else{
				global.html += '<input '+(f.id?('id="'+f.id+'"'):'')+' class="agiledev-hidden" type="hidden" name="'+f.field+'" id="'+random+'" value="'+f.value+'"/>';
				global.hiddens = global.hiddens.add(f);
			}
		});
		global.html+='</div>';
		
		return global;
	},
	/**
	 * 渲染field类型到页面表单,调用initUrl初始化页面元素
	 */
	renderField:function(global){
		var self = this,htmls = global||self.globalHtml;
		//设置combobox
		if(htmls.comboboxes.length>0){
			htmls.comboboxes.each(function(i,f){
				var obj = $.extend({},f);
				if(f.url!=null){
					obj.url = f.url;
				}else if(f.data!=null){
					obj.data = f.data;
				}else{
					throw new Error("Combobox:\""+f.field+"\" need a url path or json array configuration parameter!");
				}
				if(f.valueField!=null){
					obj.valueField = f.valueField;
				}else{
					throw new Error("Combobox:\""+f.field+"\" need a valueField configuration parameter!");
				}
				if(f.textField!=null){
					obj.textField = f.textField;
				}else{
					throw new Error("Combobox:\""+f.field+"\" need a textField configuration parameter!");
				}
				
				$("[element-id='"+f._id+"']").data("field",f).combobox(obj);
			});
		}
		if(htmls.combotree.length>0){
			htmls.combotree.each(function(i,f){
				var obj = $.extend({},f);
				if(f.url!=null){
					obj.url = f.url;
				}else if(f.data!=null){
					obj.data = f.data;
				}else{
					throw new Error("Combotree:\""+f.field+"\" need a url path or json array configuration parameter!");
				}
				$("[element-id='"+f._id+"']").data("field",f).combotree(obj);
			});
		}
		if(htmls.selects.length>0){
			htmls.selects.each(function(i,f){
				if(f.data==null)
					throw new Error("select:\""+f.field+"\" need a json array configuration parameter!");
				if(f.valueField==null)
					throw new Error("select:\""+f.field+"\" need a valueField configuration parameter!");
				if(f.textField==null)
					throw new Error("select:\""+f.field+"\" need a textField configuration parameter!");
				$("<option value='null'>--请选择--</option>").appendTo($("[element-id='"+f._id+"']"));
				$.each(f.data,function(i,o){
					$("<option value='"+o[f.valueField]+"'>"+o[f.textField]+"</option>").appendTo($("[element-id='"+f._id+"']")).data("field",f);
				});
			});
		}
		//设置text
		if(htmls.texts.length>0){
			htmls.texts.each(function(i,f){
				$("[element-id='"+f._id+"']").data("field",f).val(f.value);
			});
		}
		//设置日期时间组件
		if(htmls.datetimeboxes.length>0){
			htmls.datetimeboxes.each(function(i,f){
				var obj = $.extend({showSeconds:false},f);
				$("[element-id='"+f._id+"']").data("field",f).datetimebox(obj);
			});
		}
		//设置日期组件
		if(htmls.dateboxes.length>0){
			htmls.dateboxes.each(function(i,f){
				var obj = $.extend({showSeconds:false},f);
				$("[element-id='"+f._id+"']").data("field",f).datebox(obj);
			});
		}
		//设置hidden
		if(htmls.hiddens.length>0){
			htmls.hiddens.each(function(i,f){
				$("[element-id='"+f._id+"']").data("field",f).val(f.value);
			});
		}
		//设置upload
		if(htmls.uploads.length>0){
			$.each(htmls.uploads,function(i,f){
				$("[element-id='"+f._id+"']")
					.data("field",f)
					.upload({
						field:f.field,
						url:f.url,
						title:f.title,
						callback:f.callback,
						path:f.path,
						filters:f.filters
					});
			});
		}
	},
	initForm:function(){
		var self = this;
		if(self.options.initUrl){
			$.ajax({
				url:self.options.initUrl,
				beforeSend:function(xhr){
					xhr.setRequestHeader("agiledev-ajax-request-type",'init-form');
				},
				type:'post',
				cache:false,
				dataType:'json',
				async:false,
				success:function(data){
					self.init = data;
					$.each(data.field,function(i,f){
//						{"field":
//								[
//									{"initValue":"init firstName","name":"firstName","validate":"legnth[1,10]",required:true},
//									{"initValue":"init lastName","name":"lastName","validate":"",required:true},
//									{"initValue":"女","name":"gender","validate":"",required:true},
//									{"initValue":null,"name":"email","validate":"email",required:false}
//								]
//						}
						var tag = self.uiForm.find("input[name='"+f.name+"']");
						if(tag.length>0){
							tag.val(f.initValue==null?"":f.initValue);
							tag.data("initValue",f.initValue);
							var remoteValid = $.extend({validType:f.validate,required:f.required},tag.data("field")||self.getFieldByName(f.name));
							//var remoteValid = {validType:f.validate,required:f.required};
							tag.validatebox(remoteValid);
						}
					});
				}
			});
		}
		
	},
	getFormHtml:function(){
		return this.uiForm;
	},
	/**
	 * 根据名称查找页面配置进来的field
	 */
	getFieldByName:function(name){
		if(name==null || name=="")
			return null;
		$.each(this.options.field,function(i,f){
			if(f.field == name)
				return f;
		});
	},
	/**
	 * 生成全局唯一的guid
	 */
	guid:function(){
		var guid = new Date().getTime(), i;
		for (i = 0; i < 5; i++) {
			guid += Math.floor(Math.random() * 65535);
		}
		return  guid ;
	},
	getSearchParameters:function(){
		var params = {},self = this; 
		$.each(self.options.field,function(i,o){
			if(!o.type || o.type==="text" || o.type=== "textarea"){
				if($("[element-id='"+o._id+"']").val()!='')
				 params[o.field] = $("[element-id='"+o._id+"']").val();
			}else if(o.type === "combobox" || o.type === "combotree" || o.type === "date" || o.type === "datetime"){
				var tag = $('.search-form .agiledev-form-item:eq('+i+')');
				if( tag.find(".combo-value").val()!=""){
					params[o.field] = tag.find(".combo-value").val();
				}else if( tag.find(".combo-text").val()!=""){
					params[o.field] = tag.find(".combo-text").val();
				}
			}else if(o.type==="radio" || o.type.toLowerCase() === "checkbox"){
				var temp = "";
				$(":"+o.type.toLowerCase()+"[name='"+o.field+"']:checked").each(function(j,k){
					temp+=($(this).val()+","); 
				});
				params[o.field] = temp;
			}
		});
		return params;
	},
	widget:function(){
		return this.uiForm;
	},
	createValidate:function(){
		$.each(this.options.field,function(i,f){
			$("[element-id='"+f._id+"']").validatebox({validType:f.validType,required:f.required});
		});
	}
	
});
})(jQuery);