let pageNum = $("#pageNumContainer").text();
let totalRecords, totalPages, keyword;
$(document).ready(function() {
	$("#toAdmin").css('color', '#7F0020');
	if (pageNum === '') {
		pageNum = undefined;
	}
	toSelectedPg(pageNum, keyword);
	$("#toAdmin").addClass('animate__animated animate__flipInY');
});
$("#searchBtn2").on('click', function() {
	keyword = $("#keywordInput").val();
	toSelectedPg(1, keyword);
});
function toSelectedPg(pageNum, keyword) {
	$.ajax({
		url: '/oguma/employee/pagination',
		data: {
			'pageNum': pageNum,
			'keyword': keyword,
			'userId': $("#toPersonal").find("p").text(),
			'authChkFlag': $("#securityPersonalAuths").text()
		},
		type: 'GET',
		success: function(result) {
			buildTableBody(result);
			buildPageInfos(result);
			buildPageNavi(result);
		},
		error: function(result) {
			layer.msg(result.responseJSON.message);
		}
	});
}
function buildTableBody(result) {
	$("#tableBody").empty();
	let index = result.data.records;
	$.each(index, (index, item) => {
		let idTd = $("<th scope='row' class='text-center table-light' style='width:150px;vertical-align:middle;'></th>").append(item.id);
		let usernameTd = $("<td scope='row' class='text-center table-light' style='width:70px;vertical-align:middle;'></td>").append(item.username);
		let emailTd = $("<td scope='row' class='text-center table-light' style='width:100px;vertical-align:middle;'></td>").append(item.email);
		let dateTd = $("<td scope='row' class='text-center table-light' style='width:70px;vertical-align:middle;'></td>").append(item.dateOfBirth);
		let editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit-btn")
			.append($("<i class='fa-solid fa-pencil'></i>")).append("編集");
		editBtn.attr("editId", item.id);
		let deleteBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete-btn")
			.append($("<i class='fa-solid fa-trash'></i>")).append("削除");
		deleteBtn.attr("deleteId", item.id);
		let btnTd = $("<td class='text-center table-light' style='width:100px;vertical-align:middle;'></td>").append(editBtn).append(" ").append(deleteBtn);
		$("<tr></tr>").append(idTd).append(usernameTd).append(emailTd).append(dateTd).append(btnTd).appendTo("#tableBody");
	});
}
$("#tableBody").on('click', '.delete-btn', function() {
	let ajaxResult = $.ajax({
		url: '/oguma/employee/infoDelete/0L',
		type: 'GET',
		async: false
	});
	if (ajaxResult.status !== 200) {
		layer.msg(ajaxResult.responseJSON.message);
		return;
	}
	let userName = $(this).parents("tr").find("td:eq(0)").text().trim();
	let userId = $(this).attr("deleteId");
	swal.fire({
		title: 'メッセージ',
		text: 'この「' + userName + '」という社員の情報を削除するとよろしいでしょうか。',
		icon: 'question',
		showCloseButton: true,
		confirmButtonText: 'はい',
		confirmButtonColor: '#7F0020'
	}).then((result) => {
		if (result.isConfirmed) {
			ogumaAjaxModify('/oguma/employee/infoDelete/' + userId, 'DELETE', null, normalDeleteSuccessFunction);
		} else {
			$(this).close();
		}
	});
});
$("#addInfoBtn").on('click', function(e) {
	e.preventDefault();
	let url = '/oguma/employee/toAddition';
	checkPermissionAndTransfer(url);
});
$("#tableBody").on('click', '.edit-btn', function() {
	let editId = $(this).attr("editId");
	let authChkFlag = $("#securityPersonalAuths").text();
	let url = '/oguma/employee/toEdition?editId=' + editId + '&pageNum=' + pageNum + '&authChkFlag=' + authChkFlag;
	checkPermissionAndTransfer(url);
});
