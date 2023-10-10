<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<div>
				<a class="navbar-brand" style="font-size: 32px;" href="#">PGアプリケーション</a>
			</div>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li style="padding-top: 8px;">
					<div class="btn-group">
						<button type="button"
							class="btn btn-default btn-success dropdown-toggle"
							data-toggle="dropdown">
							<i class="glyphicon glyphicon-user"></i>${sessionScope.employee.username}<span
								class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#"><i class="glyphicon glyphicon-cog"></i>
									個人設定</a></li>
							<li><a href="#"><i class="glyphicon glyphicon-comment"></i>
									メッセージ</a></li>
							<li class="divider"></li>
							<li><a href="admin/do/logout.html"><i
									class="glyphicon glyphicon-off"></i>ログアウト</a></li>
						</ul>
					</div>
				</li>
				<li style="margin-left: 10px; padding-top: 8px;">
					<button type="button" class="btn btn-default btn-danger">
						<span class="glyphicon glyphicon-question-sign"></span>ヘルプ
					</button>
				</li>
			</ul>
			<form class="navbar-form navbar-right">
				<input type="text" class="form-control" placeholder="検索">
			</form>
		</div>
	</div>
</nav>