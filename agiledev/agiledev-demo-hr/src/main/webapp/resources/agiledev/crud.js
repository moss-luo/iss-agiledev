/**
 * @author xwpu
 */


(function($){
var options = {
			formInitUrl:'',//表单初始化地址
			saveUrl:"",//新增url
			updateUrl:"",//修改url
			removeUrl:"",//删除url
			uploadUrl:"",
			grid:{
				addable:true,//是否可新增
				updateable:true,//是否可修改
				deleteable:true,//是否可删除
				title:lang.grid.title,//grid标题
				sortName:null,//排序列
				sortOrder:'asc',//初始排序规则
				remoteSort: false,//服务器端排序(点击时)
				idField:null,//主键列
				dataUrl:null,//初始数据请求列
				collapsible:false,//是否可折叠的  
				singleSelect:true,//是否能多选 
				pagination:true,//开启分页
				rownumbers:true,//显示行号,
				toolbars:null,
				pageSize:5,
				pageList:[10,20,30,40,50],
				onLoadSuccess:function(){}
			},
			dialog:{
				//saveable:true,
				//deleteable:true,
				buttons:[]
			},
			search:{
				field:[],//field:[{title:'First Name' field:'firstName',type:'input'}]
				beforeSubmit:function(){return true;}
			},
			fields:[]//fields:[{title:'First Name',field:'firstName',class:'',show:true,frozen:false,type:'input',value:'',required,validateType:'',sortable:true}]  show:是否在grid里显示,frozen:是否在grid里冻结 
		},upload;
$.fn.crud = function(){
	var handle = new Handle(this),render = new Render(this),globalURL="";
	if(arguments.length == 1 && typeof arguments[0] == "object"){
		this.setting = $.extend(true,options,arguments[0]);
	}else if(arguments.length>=1 || typeof arguments[0] == "string"){
		var methodName = arguments[0];
		return !!eval(methodName)?eval(methodName).call(this,render,handle,arguments):this;
	}
	
	
	this.init = function(){
		render.createDatagrid();
		render.createSearch();
	}
	this.getRender = function(){
		return render;
	}
	this.getHandle = function(){
		return handle;
	}
	this.setUrl = function(url){
		this.globalURL = url;
	}
	this.getUrl = function(){
		return this.globalURL;
	}
	
	
	
	//对外接口,返回datagrid
	function getDatagrid(render,handle,args){
		//return $(this.selector).datagrid({});
		return options.globalDatagrid;
	}	
	//对外借口,返回dialog
	function getDialog(render,handle,args){
		return render.createDialog(args);
	}
	function getSearchform(){
		return options.globalSearchform;
	}
	function reloadDatagrid(render,handle,params){
		return render.reloadDatagrid(params[1]);
	}
	this.init();
	return this;
}
/**
 * 页面构造器,创建页面上各种组件
 */
function Render(plugin){
	this.plugin = plugin;
}
/**
 * 创建查询
 */
Render.prototype.createSearch = function(){
	if(options.search.field.length==0)return;
	var searchPanel= $(document.createElement("div"))
						.attr({'class':'search-form'})
						.prependTo($(this.plugin.selector).closest(".datagrid").parent()),
		$this = this,searchForm;
	
	if($.ui.agiledevForm){
		searchPanel.agiledevForm({
				field:options.search.field,
				className:'agiledev-search',
				submitUrl:options.grid.dataUrl,
				buttons:$.extend({},{'查询':{iconCls:'icon-search',handler:function(){this.submit();}}},options.search.buttons),
				beforeSubmit:function(){
					if(options.search.beforeSubmit){
						if(options.search.beforeSubmit.call(this.uiForm)){
							var queryParameters = this.getSearchParameters();
							$this.reloadDatagrid(queryParameters);
						}
					}
					return false;
				}
		});
	}
	options.globalSearchform = searchPanel;
	//searchPanel.append('<div class="search-button"><a onclick="return false" class="easyui-linkbutton l-btn search_button"><span class="l-btn-left"><span class="l-btn-text icon-search" style="padding-left: 20px;">查询</span></span></a></div>');
	//$(".search_button").click(function(){
	//	$this.plugin.getHandle().search();
	//});
}
/**
 * 创建datagrid
 */
Render.prototype.createDatagrid = function(){
	var buttons = $(this.plugin.setting.grid.toolbars),frozenColumns = $([]),columns = $([]),$this = this;
	if(options.grid.addable){
		buttons = buttons.add({
			id:'btnAdd',
			text:lang.btn.create,
			iconCls:'icon-add',
			handler:function(){
				$this.createDialog(lang.btn.create).dialog("open");
				$this.plugin.setUrl(options.saveUrl);
			}
		});
	}
	if(options.grid.updateable){
		buttons = buttons.add({
			id:'btnEdit',
			text:lang.btn.edit,
			iconCls:'icon-edit',
			handler:function(){
				if($($this.plugin.selector).datagrid('getSelections').length>1){
					$("#btnEdit").linkbutton("disable");
					$.messager.show({
						title:lang.prompt.title,
						msg:lang.prompt.selectedMuch,
						showType:'show',
						timeout:1500
					});
					return;
				}
				var row = $($this.plugin.selector).datagrid('getSelected');
				if (row){
					$this.plugin.setUrl(options.updateUrl+'?'+options.grid.idField+'='+row[options.grid.idField]);
					$this.createDialog(lang.btn.edit,row).dialog("open");
					$(options.fields).each(function(i,o){
						if(o.type=="upload"){
							if(typeof o.review == "function")
							o.review(row,row[o.field]);		
						}
					});
				}else{
					$.messager.show({
						title:lang.prompt.title,
						msg:lang.prompt.noSelected,
						showType:'show',
						timeout:1500
					});
				}
				
				
			}
		});
	}
	if(options.grid.deleteable){
		buttons = buttons.add({
			id:'btnRemove',
			text:lang.btn.remove,
			iconCls:'icon-remove',
			handler:function(){
				$this.plugin.getHandle().remove();
			}
		});
	}
	//fields:[{name:'First Name',field:'firstName',class:'',show:true,frozen:false,type:'input',required,validateType:'',sortable:true}]  show:是否在grid里显示,frozen:是否在grid里冻结
	$.each(options.fields,function(i,f){
		var c = $.extend({title:"",field:"",sortable:false,width:f.width?f.width:80,checkbox:false},f);
		if(f.frozen == true){ 
			frozenColumns = frozenColumns.add(c);
		}else if(f.gridShow != false){
			columns = columns.add(c);				
		}
		
	});
	options.globalDatagrid = $(this.plugin.selector).datagrid({
		title:options.grid.title,
		nowrap: false,
		striped: true,
		collapsible:options.grid.collapsible,
		sortName: options.grid.sortName,
		sortOrder: options.grid.sortOrder,
		remoteSort: false,
		url:options.grid.dataUrl,
		idField:options.grid.idField,
		fitColumns:true,
		singleSelect:options.grid.singleSelect,
		pagination:options.grid.pagination,
		rownumbers:options.grid.rownumbers,
		frozenColumns:[frozenColumns],
		columns:[columns],
		toolbar:buttons,
		pageSize:options.grid.pageSize,
		pageList:options.grid.pageList,
		onSelectAll:function(){
			if ($(".datagrid-row-selected").length == 0) {
				$("#btnRemove").linkbutton("disable").linkbutton({
					iconCls: 'icon-unremove'
				});
				$("#btnEdit").linkbutton("disable").linkbutton({
					iconCls: 'icon-unedit'
				});
			}
			else 
				if ($(".datagrid-row-selected").length == 1) {
					$("#btnRemove").linkbutton("enable").linkbutton({
						iconCls: 'icon-remove'
					});
					$("#btnEdit").linkbutton("enable").linkbutton({
						iconCls: 'icon-edit'
					});
				}
				else 
					if ($(".datagrid-row-selected").length > 1) {
						$("#btnRemove").linkbutton("enable").linkbutton({iconCls:'icon-remove'});	
						$("#btnEdit").linkbutton("disable").linkbutton({iconCls:'icon-unedit'});
					}
		},
		onUnselectAll:function(){
			$("#btnEdit,#btnRemove").linkbutton("disable");
			$("#btnEdit").linkbutton({iconCls:'icon-unedit'});
			$("#btnRemove").linkbutton({iconCls:'icon-unremove'});
		},
		onClickRow:function(){
			///$(".datagrid-cell-check > :checkbox").each(function(){
			$(".datagrid-body tr").each(function(){
				//if(this.checked=="checked" || this.checked == true){
				if($(this).hasClass("datagrid-row-selected")){
					if(options.globalDatagrid.datagrid("getSelections").length!=1){
						$("#btnEdit").linkbutton("disable").linkbutton({iconCls:'icon-unedit'});
					}else{
						$("#btnEdit").linkbutton("enable").linkbutton({iconCls:'icon-edit'});
					}
					$("#btnRemove").linkbutton("enable").linkbutton({iconCls:'icon-remove'});		
				}else if(options.globalDatagrid.datagrid("getSelections").length == 0){
					$("#btnEdit,#btnRemove").linkbutton("disable");
					$("#btnEdit").linkbutton({iconCls:'icon-unedit'});
					$("#btnRemove").linkbutton({iconCls:'icon-unremove'});
				}
			});
			
		},
		onSelect:function(){
			if(options.globalDatagrid.datagrid("getSelections").length!=1){
				$("#btnEdit").linkbutton("disable").linkbutton({iconCls:'icon-unedit'});
			}else{
				$("#btnEdit").linkbutton("enable").linkbutton({iconCls:'icon-edit'});
			}
			$("#btnRemove").linkbutton("enable").linkbutton({iconCls:'icon-remove'});
		},
		onUnselect:function(){
			if(options.globalDatagrid.datagrid("getSelections").length!=1){
				$("#btnEdit").linkbutton("disable").linkbutton({iconCls:'icon-unedit'});
			}else{
				$("#btnEdit").linkbutton("enable").linkbutton({iconCls:'icon-edit'});
			}
			if(options.globalDatagrid.datagrid("getSelections").length>=1){
				$("#btnRemove").linkbutton("enable").linkbutton({iconCls:'icon-remove'});
			}else{
				$("#btnRemove").linkbutton("disable").linkbutton({iconCls:'icon-unremove'});
			}
		},
		onLoadSuccess:options.grid.onLoadSuccess
	});
	$("#btnEdit,#btnRemove").linkbutton("disable");
	$("#btnEdit").linkbutton({iconCls:'icon-unedit'});
	$("#btnRemove").linkbutton({iconCls:'icon-unremove'});
}
Render.prototype.getRandom = function(){
	var guid = new Date().getTime(), i;

	for (i = 0; i < 5; i++) {
		guid += Math.floor(Math.random() * 65535);
	}
	
	return  guid ;

}

 /**
 * 创建缓存dialog
 */
Render.prototype.createDialog = function(title,data){
	var dialog = null,$this =this,dialogButtons = $([]);
	if(document.getElementById("_crudDialog")){
		dialog = $("#_crudDialog");
	}else{
		dialog = $('<div id="_crudDialog" style="padding:10px 20px;"></div>').appendTo("body");
		var form = $("body").agiledevForm({
				autoShow:false,
				field:$.map(options.fields,function(n){return n.checkbox?{}:n;}),
				initUrl:options.formInitUrl
			}).agiledevForm("getFormHtml");
		$(form).toggleClass("agiledev-hide").toggleClass("agiledev-show").addClass(data?"agiledev-edit":'agiledev-new');
		dialog.html(form);
	}
	
	dialogButtons = dialogButtons.add({
		text:lang.btn.save,
		iconCls:'icon-ok',
		handler:function(){
			$this.plugin.getHandle().save();
		}
	}); 
//	if(options.dialog.deleteable){
//		dialogButtons = dialogButtons.add({text:'删除',handler:function(){alert(123);}}); 
//	}
	$.each(options.dialog.buttons,function(i,b){
		dialogButtons = dialogButtons.add({text:b.text,iconCls:b.icon,handler:b.handler});
	});
	dialogButtons = dialogButtons.add({text:lang.btn.close,iconCls:'icon-cancel',handler:function(){$('#_crudDialog').dialog('close');}});
	
	return dialog
			.dialog({
						modal:true,
						buttons:dialogButtons,
						title:title,
						onOpen:function(){
							$("#_crudDialog").find(".agiledev-form").find("img").remove();
							if (data != null) {
								$("#_crudDialog").find(".agiledev-form").form("load", data);
							}
							else {
								$("#_crudDialog").find(".agiledev-form").form("clear");
								$("#_crudDialog").find(".agiledev-form").find(":input").each(function(){
									if($(this).data("initValue")!=null){
										if(typeof $(this).data("initValue") == "string"){
											$(this).val($(this).data("initValue"));
										}
									}
								});
							}
						}
					}).dialog("close");
	
}
 /**
 * 关闭dialog
 */
Render.prototype.closeDialog = function(){
	$("#_crudDialog").dialog("close");
}
 /**
 * 重新渲染datagrid
 */
Render.prototype.reloadDatagrid = function(params){
	if (params == "reload") {
		$(this.plugin.selector).datagrid("reload");
	}else if(params=="remake"){
		$(this.plugin.selector).datagrid();
	}else if (params != null && !$.isEmptyObject(params)) {
		$(this.plugin.selector).datagrid("load", params);
	}else {
		$(this.plugin.selector).datagrid({
			url: options.grid.dataUrl,
			queryParams: null
		});
	}
	
	
}
 /**
 * 负责在页面上执行crud操作
 */
function Handle(plugin){
	this.plugin = plugin;
}
 /**
 * 保存(新增或者修改)
 */
Handle.prototype.save = function(){
	var $this = this;
	$('.agiledev-dialog').closest(".agiledev-form").form('submit',{
		url: $this.plugin.getUrl(),
		onSubmit: function(){
			if($(this).find(":hidden[name='agiledev-ajax-request-type']").length!=0){
				$(this).find(":hidden[name='agiledev-ajax-request-type']").val('validate');
			}else{
				$(this).append("<input name='agiledev-ajax-request-type' type='hidden' value='validate'/>");
			}
			return $(this).form('validate');
		},
		success: function(result){
			var result = eval('('+result+')');
			if (result.success){
				$this.plugin.getRender().closeDialog();
				$this.plugin.getRender().reloadDatagrid("reload");
				
				$($this.plugin.selector).datagrid('clearSelections');
			} else {
				$.messager.show({
					title: lang.prompt.title,
					msg: result.msg
				});
			}
		}
	});
}
 /**
 * 删除
 */
Handle.prototype.remove = function(){
	var rows = $(this.plugin.selector).datagrid('getSelections'),$this = this;
	if (rows.length>0){
		$.messager.confirm(lang.prompt.title,lang.prompt.remove,function(r){
			if (r){
				$.ajax({
					url:options.removeUrl,
					data:{id:$.map(rows,function(n){return n[options.grid.idField];}).join(',')},
					dataType:'json',
					success:function(data){
						if(data.success){
							$this.plugin.getRender().reloadDatagrid("reload");
							$.messager.show({
								title:lang.prompt.title,
								msg:data.msg,
								showType:'show',
								timeout:1500
							});
							$($this.plugin.selector).datagrid('clearSelections');
						}else{
							$.messager.show({
								title:lang.prompt.title,
								msg:lang.prompt.error,
								showType:'show',
								timeout:2500
							});
						}
					}
				});
			}
		});
	}else{
		$.messager.show({
			title:lang.prompt.title,
			msg:lang.prompt.noSelected,
			showType:'show',
			timeout:1500
		});
	}
}
 /**
 * 查询
 */
Handle.prototype.search = function(){
	
	if(options.search.beforeSubmit){
		if(options.search.beforeSubmit.call($(".search-form"))){
			var params = $(".search-form").agiledevForm("getSearchParameters");
//			if($.isEmptyObject(params)){
//				this.plugin.getRender().reloadDatagrid();
//			}else{
//				this.plugin.getRender().reloadDatagrid(params);
//			}
			this.plugin.getRender().reloadDatagrid(params);
		}
	}
}
})(jQuery);

