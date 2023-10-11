<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja-JP">
<head>
<title>pgcrowd</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keys" content="">
<meta name="author" content="">
<base
	href="https://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />
<link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="../static/css/font-awesome.min.css">
<link rel="stylesheet" href="../static/css/login.css">
<script type="text/javascript"
	src="../static/jquery/jquery-3.6.2.min.js"></script>
<script type="text/javascript"
	src="../static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../static/layer/layer.js"></script>
<script type="text/javascript">
	$(function() {
		$("button").click(function() {
			window.history.back();
		});
	});
</script>
</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<div>
					<a class="navbar-brand" href="index.html" style="font-size: 32px;">PGアプリケーション</a>
				</div>
			</div>
		</div>
	</nav>
	<div class="container" style="text-align: center;">
		<h3>システム情報</h3>
		<h4>${requestScope.exception.message}</h4>
		<button style="width: 300px; margin: 0px auto 0px auto;"
			class="btn btn-lg btn-success btn-block">戻る</button>
	</div>
</body>

</html>