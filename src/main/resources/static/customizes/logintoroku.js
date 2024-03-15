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