<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<c:set var="base" scope="request"
	value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>全息互动管理系统</title>
<link rel="stylesheet" href="../css/index.css">
<script src="../js/jquery-3.3.1.js"></script>
<script src="../js/base64.js"></script>
</head>
<body>
	<div class="bg">
		<div class="logo">
			<a href=""><img src="../images/logo.png" alt=""></a>
		</div>
		<div class="left">
			<div class="list" onclick="window.location='${base}/admin/queryAll.action?page=0'">主播列表</div>
			<div class="add">添加主播</div>
			<div class="rizhi" >日志统计</div>
		</div>
		<div class="right">
			<div class="top">
				<div class="top1">
					<div class="topleft">
					<!-- onkeyup="xinX()" -->
						<input type="text" value="${data}"   id="textS"  placeholder="请输入姓名/手机号" >
						<button type="button" onclick="sousuo()">搜索</button>
						<ul class="mysou5" id="xinUl">
							
						    
						</ul>
					</div>

					<div class="biao">
						<ul>
							<li>ID</li>
							<li>真实姓名</li>
							<li>手机号</li>
							<li>微信号</li>
							<li>火山昵称</li>
							<li>火山号</li>
							<li>余额</li>
							<li>已提现</li>
							<li>操作</li>
						</ul>
					</div>
					<div class="biao1">
						<!-- 展示主播 -->
						<c:forEach items="${anchorMes}" var="a">
							<ul>
								<c:if test="${a.state eq '1' }">
										<img id='mysuo' src='../images/suo.png'/>
								 </c:if> 
								<li><b>${a.id}</b></li>
								<li>${a.realName}</li>
								<li>
									<input type="text" id="phone${a.id}"  disabled="disabled" value="${a.phone}">
								</li>
								<li>${a.vxNumber}</li>
								<li>
									<input type="text" id="hsNickname${a.id}"   disabled="disabled" value="${a.hsNickname}">
								</li>
								<li>${a.hsNumber}</li>
								<li>￥${a.money}</li>
								<li>￥${a.tiXian}</li>
								
								<li>
									<strong class='fa' id='${a.id}'>发工资</strong>
									<strong class='shan' id='${a.id}'>删除</strong>
									<c:if test="${a.state eq '0' }">
										<strong class='feng' id='${a.id}'>冻结</strong>
									</c:if>
									<c:if test="${a.state eq '1' }">
										<strong class='feng' id='${a.id}'>解封</strong>
									</c:if>
									<strong class='bianji' id='${a.id}'>编辑</strong>
								</li>
							</ul>
						</c:forEach>
					</div>
					<div class='myye'>
						<span onclick="window.location='${base}/admin/queryAll.action?page=0&id=0&data=${data}'">首页</span>
						<c:if test="${Page eq 1 }">
							<span class='disabled'>上一页</span> 
						</c:if>
						<c:if test="${Page gt 1 }">
						 <span  onclick="window.location='${base}/admin/queryAll.action?page=${Page-1}&id=0&data=${data}'">上一页</span> 
						</c:if>
						 <c:if test="${Page lt anchorMaxPage }">
							 <span onclick="window.location='${base}/admin/queryAll.action?page=${Page+1}&id=0&data=${data}'">下一页</span> 
						 </c:if>
						 <c:if test="${Page eq anchorMaxPage }">
							<span class='disabled'>下一页</span> 
						</c:if>
						 <span onclick="window.location='${base}/admin/queryAll.action?page=${anchorMaxPage}&id=0&data=${data}'">尾页</span>
						<strong>第${Page}/${anchorMaxPage}页</strong>
					</div>
					<div class="gongzi">
						<h4>按空白处取消</h4>
						<div class="money">
							<span>金额</span> <input id="money" type="text" onkeyup="num(this)" />
						</div>
						<input  type="hidden" id="uid"/>
						<div class="que">确定</div>
					</div>
				</div>
			</div>
			<div class="btn">
				<h2>添加</h2>
				<form action="">
					<label for=""> <span>手机号<b style="color: red;">
								*</b></span> <input id="phone" type="text" onkeyup="queryPhone()" placeholder="绑定微信的手机号" > <strong
						id="phoneSp" class='laystrong'></strong>
					</label> <label for=""> <span>真实姓名<b style="color: red;">
								*</b></span> <input id="name" type="text" onkeyup="queryNickName()" placeholder="请输入汉字" >
						<strong id="realSp" class='laystrong'></strong>
					</label> <label for=""> <span>微信号<b style="color: red;">
								*</b></span> <input id="weitel" type="text" onkeyup="queryVxNumber()" placeholder="英文字母，数字，下划线" >
						<strong id="vxNumSp" class='laystrong'></strong>
					</label> <label for=""> <span>火山昵称<b style="color: red;">
								*</b></span> <input id="weiname" type="text" onkeyup="queryHsNickname()" placeholder="英文字母，数字，下划线" >
						<strong id="hsNickSp" class='laystrong'></strong>
					</label> <label for=""> <span>火山号<b style="color: red;">
								*</b></span> <input id="huo" type="text" onkeyup="queryHsNumber()" placeholder="由0-9数字组成" >
						<strong id="hsNumSp" class='laystrong'></strong>
					</label>
					<div>
						<b style="color: red;">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</b> 为必填项
					</div>
					<div class="queding">确定</div>
				</form>
			</div>
			<div class='zhuborizhi'>
				<div class='zhutop'>
					<ul>
						<li>真实姓名</li>
						<li>手机号</li>
						<li>微信号</li>
						<li>火山昵称</li>
						<li>火山号</li>
						<li>剩余</li>
						<li>提现</li>
						<li>时间</li>
					</ul>
				</div>
				<div class='zhubtn'>
					<!-- 展示日志 -->
					<c:forEach items="${logs}" var="a">
						<ul>
							<li>${a.realName}</li>
							<li>${a.phone}</li>
							<li>${a.vxNumber}</li>
							<li>${a.hsNickname}</li>
							<li>${a.hsNumber}</li>
							<li>￥${a.money}</li>
							<li>￥${a.tiXian}</li>
							<li>${a.dealTime}</li>
						</ul>
					</c:forEach>
				</div>
				<div class='myye1'>
						<span onclick="window.location='${base}/admin/queryAll.action?page=0&id=2'">首页 </span>
						<c:if test="${Page eq 1 }">
							<span class='disabled'>上一页</span> 
						</c:if>
						<c:if test="${Page gt 1 }">
						 <span  onclick="window.location='${base}/admin/queryAll.action?page=${Page-1}&id=2'">上一页</span> 
						</c:if>
						 <c:if test="${Page lt logMaxPage }">
							 <span onclick="window.location='${base}/admin/queryAll.action?page=${Page+1}&id=2'">下一页</span> 
						 </c:if>
						 <c:if test="${Page eq logMaxPage }">
							<span class='disabled'>下一页</span> 
						</c:if>
						 <span onclick="window.location='${base}/admin/queryAll.action?page=${logMaxPage}&id=2'">尾页</span>
						<strong>第${Page}/${logMaxPage}页</strong>
				</div>
			</div>
		</div>
		<div class="yin"></div>
		<div class="tan"></div>
		<div class='tan1'>
			<span></span>
			<div class='yes'>确定</div>
			<div class='no'>取消</div>
		</div>
	</div>
	<script type="text/javascript">
		function tan(message) {
			$('.tan').css('display', 'block');
			$('.tan').html(message);
			setTimeout(function() {
				$('.tan').css('display', 'none');
				$('.tan').html('');
			}, 2000)
		}
		function GetRequest() {
			var url = location.search; //获取url中"?"符后的字串
			var theRequest = new Object();
			if (url.indexOf("?") != -1) {
				var str = url.substr(1);strs = str.split("&");
				for (var i = 0; i < strs.length; i++) {theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);}
			}
			return theRequest;
		}
		var request = new Object();
		request = GetRequest();
		setInterval(function(){
			if(!request['id']){
				$('.top').css('display', 'block');
				$('.btn').css('display', 'none');
				$('.zhuborizhi').css('display', 'none');
				$('.list').css({
					'color' : '#079fe8',
					'border-color' : '#079fe8'
				})
				$('.add').css({
					'color' : '#fff',
					'border-color' : '#fff'
				})
				$('.rizhi').css({
					'color' : '#fff',
					'border-color' : '#fff'
				})
			}
			
			if(request['id']==2){
				$('.zhuborizhi').css('display', 'block');
				$('.top').css('display', 'none');
				$('.btn').css('display', 'none');
				$('.rizhi').css({
					'color' : '#079fe8',
					'border-color' : '#079fe8'
				})
				$('.list').css({
					'color' : '#fff',
					'border-color' : '#fff'
				})
				$('.add').css({
					'color' : '#fff',
					'border-color' : '#fff'
				})
			}else if(request['id']==1){
				$('.btn').css('display', 'block');
				$('.top').css('display', 'none');
				$('.zhuborizhi').css('display', 'none');
				$('.add').css({
					'color' : '#079fe8',
					'border-color' : '#079fe8'
				})
				$('.list').css({
					'color' : '#fff',
					'border-color' : '#fff'
				})
				$('.rizhi').css({
					'color' : '#fff',
					'border-color' : '#fff'
				})
			}else if(request['id']==0){
				$('.top').css('display', 'block');
				$('.btn').css('display', 'none');
				$('.zhuborizhi').css('display', 'none');
				$('.list').css({
					'color' : '#079fe8',
					'border-color' : '#079fe8'
				})
				$('.add').css({
					'color' : '#fff',
					'border-color' : '#fff'
				})
				$('.rizhi').css({
					'color' : '#fff',
					'border-color' : '#fff'
				})
			};
		},1)
		var fa = document.querySelectorAll('.fa');
		for (var i = 0; i < fa.length; i++) {
			fa[i].index = i;
			fa[i].onclick = function() {
				document.querySelector('.que').id=this.id;
				$('.yin').css('display', 'block');
				$('.gongzi').css('display', 'block');
			}
		}
		$('.yin').click(function() {
			$(this).css('display', 'none')
			$('.gongzi').css('display', 'none')
			$('.tan1').css('display', 'none')
		})

		$('.que').click(function() {
			var id=this.id;
			if ($('#money').val() == '') {
				tan('请输入金额')
			} else {
				var money=$('#money').val();
				money=Base.encode(money);
				$.ajax({
					"url":"${base}/wallet/fa.action",
					"type":"POST",
					"data":{'money':money,'id':id},
					"success":function(obj){
						var mes=obj.mes;
						if(mes==1){
							window.location="${base}/admin/queryAll.action?page="+${Page};
						}
						if(mes==0){
							tan("系统异常！")
						}
						if(mes==2){
							var err=obj.err;
							tan(err)
						}
					}
				})
				
			}
		})
		/* 添加主播 */
		$('.queding').click(function() {
			var phone = $('#phone').val();
			var name = $('#name').val();
			var weitel = $('#weitel').val();
			var weiname = $('#weiname').val();
			var huo = $('#huo').val();
			var an=document.querySelectorAll('.laystrong');
			
				var an1=an[0].innerHTML;
				var an2=an[1].innerHTML;
				var an3=an[2].innerHTML;
				var an4=an[3].innerHTML;
				var an5=an[4].innerHTML;
				if (phone == '' || name == '' || weitel == '' || weiname == '') {
					tan('请输入完整信息')
				} else if(an1.length==1&&an2.length==1&&an3.length==1&&an4.length==1&&an5.length==1){
					$.ajax({
						"url" : "${base}/anchor/addAnchor.action",
						"type" : "POST",
						"data" : {
							'phone' : phone,
							'realName' : name,
							'vxNumber' : weitel,
							'hsNickname' : weiname,
							'hsNumber' : huo
						},
						"dataType" : "JSON",
						"success" : function(obj) {
							var mes = obj.mes;
							tan(mes);
							$('#phone').val("");
							$('#name').val("");
							$('#weitel').val("");
							$('#weiname').val("");
							$('#huo').val("");
							$("#phoneSp").html("");
							$("#realSp").html("");
							$("#vxNumSp").html("");
							$("#hsNickSp").html("");
							$("#hsNumSp").html("");
						}
					})
				
			}else{
				tan('查看添加是否有误')
			}
		})
		$('.list').click(function() {
			$('.top').css('display', 'block');
			$('.btn').css('display', 'none');
			$('.zhuborizhi').css('display', 'none');
			$(this).css({
				'color' : '#079fe8',
				'border-color' : '#079fe8'
			})
			$('.add').css({
				'color' : '#fff',
				'border-color' : '#fff'
			})
			$('.rizhi').css({
				'color' : '#fff',
				'border-color' : '#fff'
			})
			window.location='${base}/admin/queryAll.action?page=0&id=0'
		})
		$('.add').click(function() {
			$('.btn').css('display', 'block');
			$('.top').css('display', 'none');
			$('.zhuborizhi').css('display', 'none');
			$(this).css({
				'color' : '#079fe8',
				'border-color' : '#079fe8'
			})
			$('.list').css({
				'color' : '#fff',
				'border-color' : '#fff'
			})
			$('.rizhi').css({
				'color' : '#fff',
				'border-color' : '#fff'
			})
			window.location='${base}/admin/queryAll.action?page=0&id=1'
		})
		$('.rizhi').click(function() {
			$('.zhuborizhi').css('display', 'block');
			$('.top').css('display', 'none');
			$('.btn').css('display', 'none');
			$(this).css({
				'color' : '#079fe8',
				'border-color' : '#079fe8'
			})
			$('.list').css({
				'color' : '#fff',
				'border-color' : '#fff'
			})
			$('.add').css({
				'color' : '#fff',
				'border-color' : '#fff'
			})
			window.location='${base}/admin/queryAll.action?page=0&id=2'
		})
		var shan = document.querySelectorAll('.shan');
		for (var i = 0; i < shan.length; i++) {
			shan[i].index = i;
			shan[i].onclick = function() {
				document.querySelector('.yes').id=this.id;
				$('.yin').css('display', 'block');
				$('.tan1').css('display', 'block');
				$('.tan1 span').html('确定删除该主播吗？');
			}
		}
		var feng = document.querySelectorAll('.feng');
		for (var i = 0; i < shan.length; i++) {
			feng[i].index = i;
			feng[i].onclick = function() {
				if(this.innerHTML=='解封'){
					$('.tan1 span').html('确定解封该主播账户吗？');
				}
				if(this.innerHTML=='冻结'){
					$('.tan1 span').html('确定冻结该主播账户吗？');
				}
				document.querySelector('.yes').id=this.id;
				$('.yin').css('display', 'block');
				$('.tan1').css('display', 'block');
				
			}
		}
		
		/* 确定 操作*/
		$('.yes').click(function() {
			var id=this.id;
			var html= $('.tan1 span').html();
			if(html=='确定删除该主播吗？'){
				$.ajax({
					"url" : "${base}/admin/delete.action",
					"type" : "POST",
					"data" : {'id' : id},
					"dataType" : "JSON",
					"success" : function(obj) {
						var mes=obj.mes;
						if(mes==1){
							window.location="${base}/admin/queryAll.action?page="+${Page};
						}
						if(mes==0){
							tan("系统错误！")
						}
					}
				})
			}
			if(html=='确定解封该主播账户吗？'){
				$.ajax({
					"url" : "${base}/admin/jie.action",
					"type" : "POST",
					"data" : {'id' : id},
					"dataType" : "JSON",
					"success" : function(obj) {
						var mes=obj.mes;
						if(mes==1){
							window.location="${base}/admin/queryAll.action?page="+${Page};
						}
						if(mes==0){
							tan("系统错误！")
						}
						
					}
				})
			}
			if(html=='确定冻结该主播账户吗？'){
				$.ajax({
					"url" : "${base}/admin/feng.action",
					"type" : "POST",
					"data" : {'id' : id},
					"dataType" : "JSON",
					"success" : function(obj) {
						var mes=obj.mes;
						if(mes==1){
							window.location="${base}/admin/queryAll.action?page="+${Page};
						}
						if(mes==0){
							tan("系统错误！")
						}
					}
				})
			}
			$('.yin').css('display', 'none');
			$('.tan1').css('display', 'none');
			$('.tan1 span').html('');
		})
		
		/*  取消 操作*/
		$('.no').click(function() {
			$('.yin').css('display', 'none');
			$('.tan1').css('display', 'none');
			$('.tan1 span').html('');
		})
		var bianji = document.querySelectorAll('.bianji');
		for (var ii = 0; ii < bianji.length; ii++) {
			bianji[ii].index = ii;
			bianji[ii].onclick = function(event) {
				event.stopPropagation();
				var myid=this.id;
				if (this.innerHTML == '编辑') {
					var an = this.parentElement;
					var an1 = an.parentElement;
					var an2 = an1.querySelectorAll('input');
					for (var j = 0; j < an2.length; j++) {
						an2[j].disabled = '';
						an2[j].style.border = 'solid 1px #666';
					}
					this.innerHTML = '保存'
				} else {//保存信息
					var an = this.parentElement;
					var an1 = an.parentElement;
					var an2 = an1.querySelectorAll('input');
					var phone=$('#phone'+myid).val();
					var name=$('#hsNickname'+myid).val();
					var i1 = /^[1][3,4,5,7,8][0-9]{9}$/;//电话号码
					if(phone==''||!i1.test(phone)==1){
						tan("请输入正确的手机号！")
						
						//this.innerHTML = '编辑';
						//window.location="${base}/admin/queryAll.action";
						return;
					}
					if(name==''){
						tan("请输入火山昵称！")
						//this.innerHTML = '编辑';
						//window.location="${base}/admin/queryAll.action";
						return;
					}
					$.ajax({
						"url":"${base}/anchor/queryPhoneAndName.action",
						"type":"POST",
						"data":{'phone':phone,'name':name,'id':myid},
						"dataType":"JSON",
						"success":function(obj){
							var mes=obj.mes;
							if(mes=='phone'){
								tan("手机号重复！")
								return;
							}
							if(mes=='name'){
								tan("火山昵称重复！")
								return;
							}
							if(mes==0){
								$.ajax({
									"url":"${base}/anchor/updateAnchorMes.action",
									"type":"POST",
									"data":{'phone':phone,'name':name,'id':myid},
									"dataType":"JSON",
									"success":function(obj){
										var mes=obj.mes;
										if(mes==1){
											window.location="${base}/admin/queryAll.action?page="+${Page};
											tan("修改成功");
										}else{
											tan("系统异常");
										}
										for (var j = 0; j < an2.length; j++) {
											an2[j].disabled = 'disabled';
											an2[j].style.border = 'none';
										}
										this.innerHTML = '编辑';
									}
								})								
							}
						}
					})
				}
			}
		}
		$(document).click(function () {
			var an=document.querySelectorAll(".biao1 input");
			for(var i=0;i<an.length;i++){
				an[i].disabled = 'disabled';
				an[i].style.border = 'none';
				$('.bianji').html('编辑')
			}
			$('.topleft ul').css('display','none')
		});
		
		$('input').click(function(event){
			event.stopPropagation();
		})
		
		$('.topleft input').click(function () {
			$(".xinL").remove();
		    $('.topleft ul').css('display','block')
		})
		$('.topleft li').click(function () {
			event.stopPropagation();
		    var an=$(this).html();
		    $('.topleft input').val(an)
		    $('.topleft ul').css('display','none')
		})
		
	</script>
</body>
<script type="text/javascript">
	/* 验证手机号 */
	function queryPhone() {
		var i1 = /^[1][3,4,5,7,8][0-9]{9}$/;//电话号码
		var phone = $("#phone").val();
		if (phone == '') {
			$("#phoneSp").html("");
			return;
		}
		if (!i1.test(phone) == 1) {
			$("#phoneSp").html("手机号格式有误！");
			$("#phoneSp").css("color", "red");
			return;
		}
		$.ajax({
			"url" : "${base}/anchor/queryPhone.action",
			"type" : "POST",
			"data" : {
				'phone' : phone
			},
			"dataType" : "JSON",
			"success" : function(obj) {
				var mes = obj.mes;
				if (mes == "手机号已存在!") {
					$("#phone").val("");
					$("#phoneSp").html(mes);
					$("#phoneSp").css("color", "red");
				} else {
					$("#phoneSp").html(mes);
					$("#phoneSp").css("color", "green");
				}
			}
		})
	}
	/* 验证真实姓名 */
	function queryNickName() {
		var han = /^[\u4e00-\u9fa5]+$/;
		var name = $("#name").val();
		if (name == '') {
			$("#realSp").html("");
			return;
		}
		if (!han.test(name) == 1) {
			$("#name").val("");
			$("#realSp").html("请输入汉字！");
			$("#realSp").css("color", "red");
		} else {
			$("#realSp").html("√");
			$("#realSp").css("color", "green");
		}
	}
	/* 验证微信号 */
	function queryVxNumber() {
		var han = new RegExp("[\\u4E00-\\u9FFF]+", "g");
		var vxNumber = $("#weitel").val();
		if (vxNumber == '') {
			$("#vxNumSp").html("");
			return;
		}
		if (!han.test(vxNumber) == 1) {
			$.ajax({
				"url" : "${base}/anchor/queryVxNumber.action",
				"type" : "POST",
				"data" : {
					'vxNumber' : vxNumber
				},
				"dataType" : "JSON",
				"success" : function(obj) {
					var mes = obj.mes;
					if (mes == 1) {
						$("#weitel").val("");
						$("#vxNumSp").html("该微信号重复哦！");
						$("#vxNumSp").css("color", "red");
					} else {
						$("#vxNumSp").html("√");
						$("#vxNumSp").css("color", "green");
					}
				}
			})
		} else {
			$("#weitel").val("");
			$("#vxNumSp").html("请输入字母或数字！");
			$("#vxNumSp").css("color", "red");
		}
	}
	/* 验证火山昵称 */
	function queryHsNickname() {
		var nickname = $("#weiname").val();
		if (nickname == '') {
			$("#hsNickSp").html("");
			return;
		}
		$.ajax({
			"url" : "${base}/anchor/queryHsNickname.action",
			"type" : "POST",
			"data" : {
				'nickname' : nickname
			},
			"dataType" : "JSON",
			"success" : function(obj) {
				var mes = obj.mes;
				if (mes == 1) {
					$("#weiname").val("");
					$("#hsNickSp").html("该昵称重复哦！");
					$("#hsNickSp").css("color", "red");
				} else {
					$("#hsNickSp").html("√");
					$("#hsNickSp").css("color", "green");
				}
			}
		})
	}
	/*火山ID 查重  */
	function queryHsNumber() {
		var re = /^[0-9]+$/;
		var hsNumber = $("#huo").val();
		if (hsNumber == '') {
			$("#hsNumSp").html("");
			return;
		}
		if (re.test(hsNumber) == 1) {
			$.ajax({
				"url" : "${base}/anchor/queryHsNumber.action",
				"type" : "POST",
				"data" : {
					'hsNumber' : hsNumber
				},
				"dataType" : "JSON",
				"success" : function(obj) {
					var mes = obj.mes;
					if (mes == 1) {
						//$("#huo").val("");
						$("#hsNumSp").html("该火山号重复哦！");
						$("#hsNumSp").css("color", "red");
					} else {
						$("#hsNumSp").html("√");
						$("#hsNumSp").css("color", "green");
					}
				}
			})
		} else {
			$("#huo").val("");
			$("#hsNumSp").html("请输入正确的火山号！");
			$("#hsNumSp").css("color", "red");
		}
	}
	function num(obj) {
		if(obj.value=='00'){
			$("#money").val("");
			return;
		}
		if(obj.value=='0.00'){
			$("#money").val("");
			return;
		}
	    var temp = /^\d+\.?\d{0,2}$/;
	    if (temp.test(obj.value)) {
	    } else {
	        obj.value = obj.value.substr(0, obj.value.length - 1);
	        num(obj);
	        // return false;
	    }
	}
</script>

<script type="text/javascript">
function sousuo(){
	var text= $("#textS").val();
	console.log(text)
	$('.topleft ul').css('display','none');
	window.location="${base}/admin/queryAll.action?data="+text+"&page=0";
}

/* function xinX(){
	var txt=$("#textS").val();
	$.ajax({
		"url":"${base}/admin/xinX.action",
		"type":"POST",
		"data":{'data':txt},
		"dataType":"JSON",
		"success":function(obj){
			var data=obj.datas;
			$(".xinL").remove();
			for(var i=0;i<data.length;i++){
				var han = /^[\u4e00-\u9fa5]+$/;
				var str=data[i];
				var len=null;
				if(han.test(str)==1){
					len=str.length-txt.length;
				}else{
					len=11-txt.length;
				}
					str=str.substring(str.length-len)
				$("#xinUl").append("<li class='xinL'><span style='color:red;'>"+txt+"</span>"+str+"<span></span></li>");
			}
		}
	})
} */
</script>

<script> 
//定时器 异步运行 
function hello(){ 
	$.ajax({
		"url":"${base}/admin/delSession.action",
		"type":"post",
		"dataType":"JSON",
		"success":function(obj){
			var err=obj.err;
			if(err==0){
				window.location="${base}/anchor/login.jsp";
			}
		}
	})
} 

var t2 = window.setInterval("hello()",2000);


</script>
</html>