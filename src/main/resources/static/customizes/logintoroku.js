$(document).ready(function() {
	let flag = 0;
	$("#eyeIcons2").on('click', function() {
		if (flag === 0) {
			$("#passwordIpt1").attr('type', 'text');
			$("#passwordIpt2").attr('type', 'text');
			$(this).removeClass('fa-eye');
			$(this).addClass('fa-eye-slash');
			flag = 1;
		} else {
			$("#passwordIpt1").attr('type', 'password');
			$("#passwordIpt2").attr('type', 'password');
			$(this).removeClass('fa-eye-slash');
			$(this).addClass('fa-eye');
			flag = 0;
		}
	});
	$("#eyeIcons").on('click', function() {
		if (flag === 0) {
			$("#passwordIpt").attr('type', 'text');
			$(this).removeClass('fa-eye');
			$(this).addClass('fa-eye-slash');
			flag = 1;
		} else {
			$("#passwordIpt").attr('type', 'password');
			$(this).removeClass('fa-eye-slash');
			$(this).addClass('fa-eye');
			flag = 0;
		}
	});
	let message = $("#errorMsg").text();
	if (message !== '') {
		layer.msg(message);
	}
	let email = $("#emailAddress").text();
	if (email !== '') {
		$("#accountIpt").text(email);
	}
});
$("#torokuBtn").on('click', function() {
	let signupData = JSON.stringify({
		'email': $("#emailIpt").val(),
		'password': $("#passwordIpt1").val(),
		'dateOfBirth': $("#dateOfBirthIpt").val()
	});
	let header = $('meta[name=_csrf_header]').attr('content');
	let token = $('meta[name=_csrf_token]').attr('content');
	$.ajax({
		url: '/pgcrowd/do/signup',
		type: 'POST',
		data: signupData,
		headers: {
			[header]: token
		},
		dataType: 'json',
		contentType: 'application/json;charset=UTF-8'
	});
});