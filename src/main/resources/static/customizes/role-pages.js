let pageNum, totalRecords, totalPages, keyword;
$(document).ready(function() {
	$("#toRole").css('color', '#7F0020');
	toSelectedPg(1, keyword);
	$("#toRole").addClass('animate__animated animate__flipInY');
});
$("#searchBtn2").on('click', function() {
	keyword = $("#keywordInput").val();
	toSelectedPg(1, keyword);
});
function toSelectedPg(pageNum, keyword) {
	$.ajax({
		url: '/oguma/role/pagination',
		data: {
			'pageNum': pageNum,
			'keyword': keyword
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
		let nameTd = $("<td scope='row' class='text-center table-light' style='width:100px;vertical-align:middle;'></td>").append(item.name);
		let fuyoBtn = $("<button></button>").addClass("btn btn-success btn-sm fuyo-btn")
			.append($("<i class='fa-solid fa-check-to-slot'></i>")).append("権限付与");
		fuyoBtn.attr("fuyoId", item.id);
		let editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit-btn")
			.append($("<i class='fa-solid fa-pencil'></i>")).append("編集");
		editBtn.attr("editId", item.id);
		let deleteBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete-btn")
			.append($("<i class='fa-solid fa-trash'></i>")).append("削除");
		deleteBtn.attr("deleteId", item.id);
		let btnTd = $("<td class='text-center table-light' style='width:100px;vertical-align:middle;'></td>").append(fuyoBtn).append(" ").append(editBtn).append(" ").append(deleteBtn);
		$("<tr></tr>").append(idTd).append(nameTd).append(btnTd).appendTo("#tableBody");
	});
}
$("#addRoleBtn").on('click', function() {
	let ajaxResult = $.ajax({
		url: '/oguma/role/checkEdition',
		type: 'GET',
		async: false
	});
	if (ajaxResult.status !== 200) {
		layer.msg(ajaxResult.responseJSON.message);
		return;
	}
	formReset("#roleAddModal form");
	let addModal = new bootstrap.Modal($("#roleAddModal"), {
		backdrop: 'static'
	});
	addModal.show();
});
$("#nameInput").on("change", function() {
	checkRoleName(this);
});
$("#roleInfoSaveBtn").on("click", function() {
	let inputArrays = ["#nameInput"];
	let listArray = ogumaInputContextGet(inputArrays);
	if (listArray.includes("")) {
		ogumaNullInputboxDiscern(inputArrays);
	} else if ($("#roleAddModal form").find('*').hasClass('is-invalid')) {
		layer.msg('入力情報不正');
	} else {
		let postData = JSON.stringify({
			'name': $("#nameInput").val().trim()
		});
		ogumaAjaxModify('/oguma/role/infoSave', 'POST', postData, postSuccessFunction);
	}
});
$("#tableBody").on("click", '.edit-btn', function() {
	let ajaxResult = $.ajax({
		url: '/oguma/role/checkEdition',
		type: 'GET',
		async: false
	});
	if (ajaxResult.status !== 200) {
		layer.msg(ajaxResult.responseJSON.message);
		return;
	}
	formReset("#roleEditModal form");
	let editId = $(this).attr("editId");
	$("#roleInfoChangeBtn").val(editId);
	$("#idEdit").text(editId);
	let nameVal = $(this).parent().parent().find("td:eq(0)").text();
	$("#nameEdit").val(nameVal);
	let editModal = new bootstrap.Modal($("#roleEditModal"), {
		backdrop: 'static'
	});
	editModal.show();
});
$("#nameEdit").on("change", function() {
	checkRoleName(this);
});
$("#roleInfoChangeBtn").on("click", function() {
	let inputArrays = ["#nameEdit"];
	let listArray = ogumaInputContextGet(inputArrays);
	if (listArray.includes("")) {
		ogumaNullInputboxDiscern(inputArrays);
	} else if ($("#roleEditModal form").find('*').hasClass('is-invalid')) {
		layer.msg('入力情報不正');
	} else {
		let putData = JSON.stringify({
			'id': this.value,
			'name': $("#nameEdit").val().trim()
		});
		ogumaAjaxModify('/oguma/role/infoUpdate', 'PUT', putData, putSuccessFunction);
	}
});
$("#tableBody").on("click", '.delete-btn', function() {
	let ajaxResult = $.ajax({
		url: '/oguma/role/checkDelete',
		type: 'GET',
		async: false
	});
	if (ajaxResult.status !== 200) {
		layer.msg(ajaxResult.responseJSON.message);
		return;
	}
	let roleName = $(this).parents("tr").find("td:eq(0)").text().trim();
	let roleId = $(this).attr("deleteId");
	swal.fire({
		title: 'メッセージ',
		text: 'この「' + roleName + '」という役割情報を削除する、よろしいでしょうか。',
		icon: 'question',
		showCloseButton: true,
		confirmButtonText: 'はい',
		confirmButtonColor: '#7F0020'
	}).then((result) => {
		if (result.isConfirmed) {
			ogumaAjaxModify('/oguma/role/infoDelete/' + roleId, 'DELETE', null, normalDeleteSuccessFunction);
		} else {
			$(this).close();
		}
	});
});
$("#tableBody").on("click", '.fuyo-btn', function() {
	let fuyoId = $(this).attr("fuyoId");
	$("#authChangeBtn").attr("fuyoId", fuyoId);
	let nameVal = $(this).parent().parent().find("td:eq(0)").text();
	$("#roleName").text(nameVal);
	let authModal = new bootstrap.Modal($("#authEditModal"), {
		backdrop: 'static'
	});
	let ajaxReturn = $.ajax({
		url: '/oguma/role/getAuthlist',
		type: 'GET',
		async: false
	});
	if (ajaxReturn.status !== 200) {
		layer.msg(ajaxResult.responseJSON.message);
		return;
	}
	authModal.show();
	let setting = {
		'data': {
			'simpleData': {
				'enable': true,
				'pIdKey': 'categoryId'
			},
			'key': {
				'name': 'title'
			}
		},
		'check': {
			'enable': true,
			'chkStyle': 'checkbox',
			'chkboxType': {
				'Y': 'ps',
				'N': 'ps'
			}
		}, callback: {
			'onNodeCreated': zTreeOnNodeCreated
		}
	};
	let authlist = ajaxReturn.responseJSON.data;
	$.fn.zTree.init($("#authTree"), setting, authlist);
	let zTreeObj = $.fn.zTree.getZTreeObj("authTree");
	zTreeObj.expandAll(true);
	ajaxReturn = $.ajax({
		url: '/oguma/role/getAssigned',
		data: 'fuyoId=' + fuyoId,
		type: 'GET',
		async: false
	});
	let authIdList = ajaxReturn.responseJSON.data;
	for (const element of authIdList) {
		let authId = element;
		let treeNode = zTreeObj.getNodeByParam('id', authId);
		zTreeObj.checkNode(treeNode, true, true);
	}
});
$("#authChangeBtn").on("click", function() {
	let fuyoId = $(this).attr("fuyoId");
	let authIdArray = [];
	let zTreeObj = $.fn.zTree.getZTreeObj("authTree");
	let checkedNodes = zTreeObj.getCheckedNodes();
	for (const element of checkedNodes) {
		let checkedNode = element;
		let authId = checkedNode.id;
		authIdArray.push(authId);
	}
	let putData = JSON.stringify({
		'authIds': authIdArray,
		'roleIds': [fuyoId]
	});
	ogumaAjaxModify('/oguma/role/doAssignment', 'PUT', putData, authPutSuccessFunction);
});
function putSuccessFunction(result) {
	if (result.status === 'SUCCESS') {
		$("#roleEditModal").modal('hide');
		layer.msg(result.message);
		toSelectedPg(pageNum, keyword);
	} else {
		layer.msg(result.message);
	}
}
function authPutSuccessFunction(result) {
	if (result.status === 'SUCCESS') {
		$("#authEditModal").modal('hide');
		layer.msg('権限付与成功！');
		toSelectedPg(pageNum, keyword);
	} else {
		layer.msg(result.message);
	}
}
function postSuccessFunction(result) {
	$("#roleAddModal").modal('hide');
	layer.msg(result.message);
	toSelectedPg(totalRecords, keyword);
}
function zTreeOnNodeCreated(event, treeId, treeNode) { // 设置节点创建时的回调函数
	let iconObj = $("#" + treeNode.tId + "_ico"); // 获取图标元素
	iconObj.removeClass("button ico_docu ico_open ico_close");
	iconObj.append("<i class='fa-brands'></i>"); // 添加bootstrap图标的基础类名
	let iconObjectId = Number(iconObj.attr("id").substring(9, 10));
	if ($.isNumeric(iconObj.attr("id").substring(10, 11))) {
		iconObjectId = Number(iconObj.attr("id").substring(9, 11));
	}
	let pIdArrays = [1, 5, 9, 12];
	let deleteIdArrays = [2, 6];
	let retrieveIdArrays = [3, 7, 10, 13];
	if (pIdArrays.includes(iconObjectId)) {
		iconObj.find("i").addClass("fa-twitter");
	} else if (deleteIdArrays.includes(iconObjectId)) {
		iconObj.find("i").addClass("fa-pinterest");
	} else if (retrieveIdArrays.includes(iconObjectId)) {
		iconObj.find("i").addClass("fa-amazon");
	} else {
		iconObj.find("i").addClass("fa-apple");
	}
}
function checkRoleName(roleName) {
	let nameVal = $(roleName).val().trim();
	if (nameVal === "") {
		showValidationMsg(roleName, "error", "名称を空になってはいけません。");
	} else {
		$.ajax({
			url: '/oguma/role/check',
			data: 'name=' + nameVal,
			type: 'GET',
			success: function(result) {
				if (result.status === 'SUCCESS') {
					showValidationMsg(roleName, "success", "√");
				} else {
					showValidationMsg(roleName, "error", result.message);
				}
			}
		});
	}
}