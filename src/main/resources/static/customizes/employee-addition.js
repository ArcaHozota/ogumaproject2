let pageNum = $("#pageNumContainer").text();
let totalRecords;
$("#loginAccountInput").change(function() {
	$.ajax({
		url: '/pgcrowd/employee/check',
		data: 'loginAcct=' + this.value,
		type: 'GET',
		dataType: 'json',
		success: function(result) {
			if (result.status === 'SUCCESS') {
				showValidationMsg(this, "success", "√");
			} else {
				showValidationMsg(this, "error", result.message);
			}
		}
	});
});
$("#passwordInput").change(function() {
	let inputPassword = this.value;
	let regularPassword = /^[a-zA-Z\d]{8,23}$/;
	if (!regularPassword.test(inputPassword)) {
		showValidationMsg(this, "error", "入力したパスワードが8桁から23桁までの英数字にしなければなりません。");
	} else {
		showValidationMsg(this, "success", "√");
	}
});
$("#emailInput").change(function() {
	let inputEmail = this.value;
	let regularEmail = /^^[a-zA-Z\d._%+-]+@[a-zA-Z\d.-]+\.[a-zA-Z]{2,}$/;
	if (!regularEmail.test(inputEmail)) {
		showValidationMsg(this, "error", "入力したメールアドレスが正しくありません。");
	} else {
		showValidationMsg(this, "success", "√");
	}
});
$("#saveInfoBtn").on('click', function() {
	let inputArrays = ["#loginAccountInput", "#usernameInput", "#passwordInput", "#emailInput"];
	let listArray = pgcrowdInputContextGet(inputArrays);
	if (listArray.includes("")) {
		pgcrowdNullInputboxDiscern(inputArrays);
	} else if ($("#inputForm").find('*').hasClass('is-invalid')) {
		layer.msg('入力情報不正');
	} else {
		let postData = JSON.stringify({
			'loginAccount': $("#loginAccountInput").val().trim(),
			'username': $("#usernameInput").val().trim(),
			'password': $("#passwordInput").val().trim(),
			'email': $("#emailInput").val().trim(),
			'dateOfBirth': $("#dateInput").val(),
			'roleId': $("#roleInput").val()
		});
		pgcrowdAjaxModify('/pgcrowd/employee/infoSave', 'POST', postData, postSuccessFunction);
	}
});
$("#passwordEdit").change(function() {
	let editPassword = this.value;
	let regularPassword = /^[a-zA-Z\d]{8,23}$/;
	if (!regularPassword.test(editPassword)) {
		showValidationMsg(this, "error", "入力したパスワードが8桁から23桁までの英数字にしなければなりません。");
	} else {
		showValidationMsg(this, "success", "√");
	}
});
$("#emailEdit").change(function() {
	let editEmail = this.value;
	let regularEmail = /^^[a-zA-Z\d._%+-]+@[a-zA-Z\d.-]+\.[a-zA-Z]{2,}$/;
	if (!regularEmail.test(editEmail)) {
		showValidationMsg(this, "error", "入力したメールアドレスが正しくありません。");
	} else {
		showValidationMsg(this, "success", "√");
	}
});
$("#roleEdit").change(function() {
	let ajaxResult = $.ajax({
		url: '/pgcrowd/employee/infoDelete/0L',
		type: 'GET',
		async: false
	});
	if (ajaxResult.status !== 200) {
		showValidationMsg(this, "error", ajaxResult.responseJSON.message);
	} else {
		showValidationMsg(this, "success", "√");
	}
});
$("#editInfoBtn").on('click', function() {
	let inputArrays = ["#usernameEdit", "#passwordEdit", "#emailEdit"];
	let listArray = pgcrowdInputContextGet(inputArrays);
	if (listArray.includes("")) {
		pgcrowdNullInputboxDiscern(inputArrays);
	} else if ($("#editForm").find('*').hasClass('is-invalid')) {
		layer.msg('入力情報不正');
	} else {
		let rawPassword = $("#passwordEdit").val().trim();
		if (rawPassword === "---------------------------") {
			rawPassword = null;
		}
		let roleId = $("#roleEdit").attr('value');
		if (roleId === null || roleId === undefined) {
			roleId = $("#roleEdit option:selected").val();
		}
		let putData = JSON.stringify({
			'id': $("#editIdContainer").text(),
			'loginAccount': $("#loginAccountEdit").text(),
			'username': $("#usernameEdit").val().trim(),
			'password': rawPassword,
			'email': $("#emailEdit").val().trim(),
			'dateOfBirth': $("#dateEdit").val(),
			'roleId': roleId
		});
		pgcrowdAjaxModify('/pgcrowd/employee/infoUpdate', 'PUT', putData, putSuccessFunction);
	}
});
function postSuccessFunction() {
	window.location.replace('/pgcrowd/employee/toPages?pageNum=' + totalRecords);
}
function putSuccessFunction(result) {
	if (result.status === 'SUCCESS') {
		window.location.replace('/pgcrowd/employee/toPages?pageNum=' + pageNum);
	} else {
		layer.msg(result.message);
	}
}
$("#resetBtn").on('click', function() {
	formReset("#inputForm");
});
$("#restoreBtn").on('click', function() {
	formReset("#editForm");
});