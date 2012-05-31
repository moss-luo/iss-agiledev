<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path=request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN"><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提示页面</title>
<style rel="stylesheet">
/*top*/
body{margin:0 auto; background:#fff; font-size:12px; font-family:simsun; font-weight:normal;}
img { border:none;}
ul, ol, li, p, dl, dt, dd{margin:0; padding:0; list-style:none;}
.font_red{color:#f00; font-size:12px; }
.font_gray{color:#333;}
a.font_gray:link, a.font_gray:visited, a.font_gray:active{color:#333; text-decoration:underline; font-size:12px;}
a.font_gray:hover{color:#f00;}
.clear{clear:both;}


/*pub_footer*/
.pub_footerall{margin:15px auto;width:auto; overflow:hidden; padding:3px 6px;border-top:0px solid #CCC; font-size:12px; text-align:center; clear:both;}
.pub_footerall dl{padding:3px 0 3px; overflow:hidden; margin:0; line-height:1.5; font-family:simsun;}
.pub_footerall dd{margin:0;}
.pub_footerall dd a{padding:0 5px; margin:0;}
.pub_footerall dd a:link, .pub_footerall dd a:visited, .pub_footerall dd a:active{color:#000; text-decoration:none;}
.pub_footerall dd a:hover{color:#015FB6;}
.pub_footerall dd img{border:0;}
.pub_footerall dt {text-align:center; padding-top:5px; margin:0;}
.pub_footerall dt a{padding:0; margin:5px; display:inline;}
.pub_footerall dt a img{border:1px solid #ccc; width:115px; height:45px;}
/******/

/* content */
.full{width:560px; margin:0 auto;margin-top:100px}
/*.full dl.error{width:560px;  background:url(http://csdnimg.cn/www/images/pic_contbg.gif) 0 0 repeat-y; border-bottom:1px solid #ebebeb; margin:100px 0 10px;}*/
.full dl.error dt{height:44px; line-height:44px;  background:url(<%=path%>/resources/images/pic_bg.gif) -20px -18px no-repeat; text-align:left; padding:0 15px; vertical-align:center;}
.full dl.error dt img{padding-top:13px; width:167px; height:20px;  background:url(<%=path%>/resources/resources/images/pic_bg.gif) -20px -309px no-repeat;}
.full dl.error dd{text-align:center;}
.full dl.error dd span.error404{padding:50px 30px 40px 0; display:inline-block;}
.full dl.error dd span.error404 img{width:203px; height:76px;  background:url(<%=path%>/resources/images/pic_bg.gif) -176px -130px no-repeat;}
.full dl.error dd span.errortext{padding:85px 0 40px 0; display:inline-block;}
.full dl.error dd span.errortext img{width:122px; height:39px;  background:url(<%=path%>/resources/images/pic_bg.gif) -426px -130px no-repeat;}
.full dl.error dd span.btn_back{padding:0 0 50px 0; display:inline-block;}
.full dl.error dd span.btn_back img{width:122px; height:42px;  background:url(<%=path%>/resources/images/pic_bg.gif) -432px -179px no-repeat;}
/******/

.error_pop{width:360px; border:1px solid #bebebe; background:#f5f5f5; padding:20px; text-align:center; top:50%; }
.error_pop .left{width:120px; padding-right:20px; display:inline-block;}
.error_pop .left img.error{width:120px; height:119px;  background:url(<%=path%>/resources/images/pic_bg.gif) -20px -90px no-repeat;}
.error_pop .right{width:166px; padding:0; display:inline-block; vertical-align:top; padding-top:30px;}
.error_pop .right img.text{margin-bottom:20px; width:166px; height:18px;  background:url(<%=path%>/resources/images/pic_bg.gif) -20px -258px no-repeat;}
.error_pop .right img.close{cursor:pointer;  width:87px; height:32px;  background:url(<%=path%>/resources/images/pic_bg.gif) -222px -251px no-repeat;}

.mesWindow{width:402px; height:162px;}
</style><!--20101207 update-->

<script>
var isIe=(document.all)?true:false;
//设置select的可见状态
function setSelectState(state)
{
var objl=document.getElementsByTagName('select');
for(var i=0;i<objl.length;i++)
{
objl[i].style.visibility=state;
}
}
function mousePosition(ev)
{
if(ev.pageX || ev.pageY)
{
return {x:ev.pageX, y:ev.pageY};
}
return {
x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,y:ev.clientY + document.body.scrollTop - document.body.clientTop
};
}
//弹出方法
function showMessageBox(wTitle,content,pos,wWidth)
{
	closeWindow();
	var bWidth=parseInt(document.documentElement.scrollWidth);
	var bHeight=parseInt(document.documentElement.scrollHeight);

	if(isIe){
		setSelectState('hidden');
	}
	if(document.getElementById('back')!=null) //如果弹出层存在
	{
		document.getElementById('back').style.display = ""; //就让弹出层显示出来
	}
	else{
		var back=document.createElement("div");       //否则就创建新的层
		back.id="back";
		//var styleStr="top:0px;left:0px;position:absolute;background:#666;width:"+bWidth+"px;height:"+bHeight+"px;";
		var styleStr="top:0px;left:0px;position:fixed;background:#666;width:100%;height:100%;";
		styleStr+=(isIe)?"filter:alpha(opacity=0);":"opacity:0;";
		back.style.cssText=styleStr;
		document.body.appendChild(back);
		showBackground(back,50);
	}
	var mesW=document.createElement("div");
	mesW.id="mesWindow";
	mesW.className="mesWindow";
	mesW.innerHTML=content;
	var v_top=(document.body.clientHeight-mesW.clientHeight)/2;
	v_top+=document.documentElement.scrollTop;
	document.body.appendChild(mesW);
	styleStr="top:"+(document.body.clientHeight/2-mesW.clientHeight/2)+"px;left:"+(document.body.clientWidth/2-mesW.clientWidth/2)+"px;position:fixed;left:50%; top:50%; z-index:9999; margin-left:-"+(mesW.offsetWidth / 2)+"px; margin-top:-"+(mesW.offsetHeight / 2)+"px;";
	mesW.style.cssText=styleStr;


}
//让背景渐渐变暗
function showBackground(obj,endInt)
{
if(isIe)
{
obj.filters.alpha.opacity+=5;
if(obj.filters.alpha.opacity<endInt)
{
setTimeout(function(){showBackground(obj,endInt)},5);
}
}else{
var al=parseFloat(obj.style.opacity);al+=0.05;
obj.style.opacity=al;
if(al<(endInt/100))
{setTimeout(function(){showBackground(obj,endInt)},5);}
}
}
//关闭窗口
function closeWindow()
{

if(document.getElementById('back')!=null)
{
	document.getElementById('back').style.display="none";
	//document.getElementById('back').parentNode.removeChild(document.getElementById('back'));
}
if(document.getElementById('mesWindow')!=null)
{
document.getElementById('mesWindow').parentNode.removeChild(document.getElementById('mesWindow'));
}
if(isIe){
setSelectState('');}
}
//测试弹出
function testMessageBox(ev)
{
var objPos = mousePosition(ev);
messContent="<div class='error_pop'><span class='left'><img src='<%=path%>/resources/images/pic_dot.gif' alt='404' title='404' class='error' /></span><span class='right'><img src='<%=path%>/resources/images/pic_dot.gif' class='text' alt='您访问的地址不存在' title='您访问的地址不存在' /><img src='<%=path%>/resources/images/pic_dot.gif' alt='关闭' title='关闭' onclick='closeWindow();' class='close' /></span></div>";
showMessageBox('窗口标题',messContent,objPos,350);
}


</script></head>
<body>
	<!--头文件20101207 update -->
	<div class="full">
		
		<dl class="error">
			<dt></dt>
			<dd>
				<span class="error404"><img src="<%=path%>/resources/images/pic_dot.gif" alt="404" title="404"></span>
				<span class="errortext"><a href="#none" onclick="testMessageBox(event);"><img src="<%=path%>/resources/images/pic_dot.gif" alt="你懂的" title="你懂的"></a></span>
			</dd>
			<dd>
				<span class="btn_back"><a href="<%=path %>/login.html" target="_top"><img src="<%=path%>/resources/images/pic_dot.gif" alt="返回" title="返回"></a></span>
			</dd>
		</dl>
	</div>


</body></html>