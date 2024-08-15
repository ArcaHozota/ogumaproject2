$(document).ready(function() {
	let flag = 0;
	$("#eyeIcons2").on('click', function() {
		if (flag === 0) {
			$("#passwordIpt1").attr('type', 'text');
			$("#passwordIpt2").attr('type', 'text');
			$(this).attr('name', 'eye-off-outline');
			flag = 1;
		} else {
			$("#passwordIpt1").attr('type', 'password');
			$("#passwordIpt2").attr('type', 'password');
			$(this).attr('name', 'eye-outline');
			flag = 0;
		}
	});
	$("#eyeIcons").on('click', function() {
		if (flag === 0) {
			$("#passwordIpt").attr('type', 'text');
			$(this).attr('name', 'eye-off-outline');
			flag = 1;
		} else {
			$("#passwordIpt").attr('type', 'password');
			$(this).attr('name', 'eye-outline');
			flag = 0;
		}
	});
	let message1 = $("#errorMsg").text();
	if (message1 !== '') {
		layer.msg(message1);
	}
	let message2 = $("#torokuMsg").text();
	if (message2 !== '') {
		layer.msg(message2);
	}
	let message3 = $("#resetMsg").text();
	if (message3 !== '') {
		layer.msg(message3);
	}
});
$("#loginBtn").on('click', function(e) {
	e.preventDefault();
	let account = $("#accountIpt").val().trim();
	let password = $("#passwordIpt").val().trim();
	if (account === "" && password === "") {
		layer.msg('アカウントとパスワードを入力してください。');
	} else if (account === "") {
		layer.msg('アカウントを入力してください。');
	} else if (password === "") {
		layer.msg('パスワードを入力してください。');
	} else {
		$("#loginForm").submit();
	}
});
$("#torokuBtn").on('click', function(e) {
	e.preventDefault();
	let inputArrays = ["#emailIpt", "#passwordIpt1", "#passwordIpt2", "#dateOfBirthIpt"];
	for (const element of inputArrays) {
		if ($(element).val().trim() === "") {
			layer.msg('入力しなかった情報があります。');
			return;
		}
	}
	let password01 = $("#passwordIpt1").val();
	let password02 = $("#passwordIpt2").val();
	if (password01 !== password02) {
		layer.msg('入力したパスワードが不一致です。');
	} else {
		$("#torokuForm").submit();
	}
});
$("#resetBtn").on('click', function(e) {
	e.preventDefault();
	let inputArrays = ["#accountIpt2", "#emailIpt2", "#dateOfBirthIpt2"];
	for (const element of inputArrays) {
		if ($(element).val().trim() === "") {
			layer.msg('入力しなかった情報があります。');
			return;
		}
	}
	$("#resetForm").submit();
});
$("#detailsBtn").on('click', function(e) {
	e.preventDefault();
	let textDetail = '<div class="alert-divider"></div>' +
		'画面：　Bootstrap5 + SweetAlert2 + Thymeleaf' +
		'<br>データ転送：　Ajax + JSON' +
		'<br>フレームワーク：　SpringBoot + SpringMVC' +
		'<br>データ交互：　MyBatis' +
		'<br>データベース：　oracle' +
		'<br>セキュリティ：　SpringSecurity' +
		'<div class="alert-divider"></div>' +
		'<br>Powered by ArkamaHozota.' +
		'<br>All rights reserved.';
	swal.fire({
		title: 'プロジェクト粗筋',
		html: textDetail,
		showCloseButton: true,
		confirmButtonText: 'OK',
		confirmButtonColor: '#002FA7'
	});
});
$("#warningBtn").on('click', function(e) {
	e.preventDefault();
	swal.fire({
		title: '警告',
		html: '本社の王先生のご指示通りに使ってください。',
		confirmButtonText: '了解しました。',
		confirmButtonColor: '#7F0020'
	});
});
$("#login").on("click", function(e) {
	e.preventDefault();
	$("body").removeClass('signupFeature');
});
$("#signup").on("click", function(e) {
	e.preventDefault();
	$("body").addClass('signupFeature');
});
$("#resetLogin").on("click", function(){
	window.location.replace('/oguma/employee/login');
});