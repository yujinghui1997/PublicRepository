<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<c:set var="base" scope="request" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>全息互动管理系统</title>
 <meta name="viewport" content="width=device-width, initial-scale=1.0">

<script src="${base}/js/jquery-3.3.1.js"></script>
<link rel="stylesheet" href="${base}/css/logo.css">
<link rel="stylesheet" href="${base}/css/style.css">
<script type="text/javascript" src="${base}/js/style.js"></script>
</head>
<body>
<div id="slideBar"></div>
	<div class="bg">
		<div class="logo">
			<img src="${base}/images/logo.png" alt="">
		</div>
		<div class="center">
			<form action="">
				<label for=""> <span>账号</span> <input id="user" type="text">
				</label> <label for=""> <span>密码</span> <input id="pass"
					type="password">
				</label> 
				<div class="login">登陆</div>
			</form>
		</div>
		<div class="tan"></div>
		<div class='yin'></div>
	</div>
	
	<script>
		//弹框
		function tan(message) {
			$('.tan').css('display', 'block');
			$('.tan').html(message);
			setTimeout(function() {
				$('.tan').css('display', 'none');
				$('.tan').html('');
			}, 2000)
		}
		$(".login").click(function() {
			if ($('#user').val() == '') {
				tan('请填写账号')
			} else if ($('#pass').val() == '') {
				tan('请填写密码')
			} else {
				$('.yin').css('display','block');
				$('#slideBar').css('display','block');
			
			}
		})
	</script>
	 <script type="text/javascript">
        var dataList = ["0","1"];
        var options = {
            dataList: dataList,
            success:function(){  
            	var username= $("#user").val();
				var password=$("#pass").val();
				$.ajax({
					"url":"${base}/admin/login.action",
					"type":"POST",
					"data":{'username':username,'password':password},
					"success":function(obj){
						var mes=obj.mes;
						if(mes==1){
							window.location="${base}/admin/queryAll.action?page=0&id=0";
						}
						if(mes==0){
							$('.yin').css('display','none');
							$('#slideBar').css('display','none');
							tan('账号或密码错误');
						}
					}
				})
            },
            fail: function(){
                console.log("fail");  
            }
        };
        SliderBar("slideBar", options);
    </script>
<script> 
//定时器 异步运行 
function hello(){ 
	$.ajax({
		"url":"${base}/admin/delSession.action",
		"type":"post",
		"dataType":"JSON",
		"success":function(){
		
		}
	})
} 
var t2 = window.setInterval("hello()",2000)
//使用字符串执行方法 
//使用方法名字执行方法 
//var t1 = window.setTimeout(hello,1000); 
//window.clearTimeout(t1);//去掉定时器 
</script>
</body>
</html>