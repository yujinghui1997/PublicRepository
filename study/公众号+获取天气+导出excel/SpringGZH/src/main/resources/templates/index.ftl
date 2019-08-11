<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
</head>
<body>
	你好
	
	<div onclick="getdata()">点击</div>
	<div id="div"></div>
</body>
<script type="text/javascript">
	function getdata(){
		 $.get({
			"url":"/getdata",
			"success":function(msg){
				$("#div").html(msg)
			}
		}) 
	}
</script>

</html>