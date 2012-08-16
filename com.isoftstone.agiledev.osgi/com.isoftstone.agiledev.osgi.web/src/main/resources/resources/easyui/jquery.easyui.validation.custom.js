(function($){
  	$.extend($.fn.validatebox.defaults.rules, {
      	mobile: {
	          validator: function(value, param){   
	              return /^0{0,1}(13[4-9]|15[7-9]|15[0-2]|18[6-8])[0-9]{8}$/.test(value);   
	          },
	          message: '手机号码格式错误!'  
	      },
	      remotely:{
	    	  validator:function(value,param){
		    	  var postdata={},result = null,timer;
	              postdata[param[1]]=value;
	             //clearTimeout(timer); 
	              //timer = setTimeout(function(){
	            	  $.ajax({
	            		  url:param[0],
	            		  dataType:"json",
	            		  data:postdata,
	            		  async:false,
	            		  cache:false,
	            		  type:"post",
	            		  succses:function(data){
		            		  alert(data);
		            	  }
	            	  });
	             // },1500);
	              return result=="true";
	      	  },
	      	  message:'已存在相同的值，请选择其他值'
	      },
	      idCard:{
	    	  validator:function(value,param){
	    	  	var pattern = /^((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\d{3}(x|X))|(\d{4}))$/.test(value);  
	      	  },
	      	  message:'身份证格式错误'
		 },
		 numbers:{
			 validator:function(value,param){
			 	//var a = /^0{0,1}(13[4-9]|15[7-9]|15[0-2]|18[6-8])[0-9]{8}$/;
			 	 //return /^(/-?)(/d+)$/.test(value);
				 ;
		 	 },
		 	 message:'数字格式错误'
		 },
		 floats:{
			 validator:function(value,param){
			 	//return /^(/-?)(/d+)(.{1})(/d+)$/g.test(value);
				;
		 	 },
		 	 message:'数字格式错误'
		 },
		 dates:{
			 validator:function(value,param){
			 	//return /^(/d{4})-(/d{1,2})-(/d{1,2})$/.test(value);
				;
			 },
			 message:'日期格式错误'
		 }
	});
})(jQuery);
