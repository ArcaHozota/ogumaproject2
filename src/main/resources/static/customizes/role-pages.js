let pageNum = /*[[${pageNum}]]*/{};
let totalRecords, totalPages, keyword;
$(document).ready(function() {
	$("#adminKanri").removeClass('collapsed');
	$("ul", $("#adminKanri")).show('fast');
	toSelectedPg(pageNum, keyword);
});
$("#searchBtn2").on('click', function() {
	keyword = $("#keywordInput").val();
	toSelectedPg(1, keyword);
});
function toSelectedPg(pageNum, keyword) {
	$.ajax({
		url: '/pgcrowd/role/pagination',
		data: {
			'pageNum': pageNum,
			'keyword': keyword
		},
		type: 'GET',
		dataType: 'json',
		success: function(result) {
			buildCityTable(result);
			buildPageInfos(result);
			buildPageNavi(result);
		}
	})
}
function buildCityTable(result) {
	$("#tableBody").empty();
	let index = result.data.records;
	$.each(index, (index, item) => {
		let idTd = $("<th scope='row' class='text-center' style='width:70px;vertical-align:bottom;'></th>").append(item.id);
		let nameTd = $("<td scope='row' class='text-center' style='width:120px;vertical-align:bottom;'></td>").append(item.name);
		let editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn")
			.append($("<i class='bi bi-pencil-fill'></i>")).append("編集");
		editBtn.attr("editId", item.id);
		let deleteBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
			.append($("<i class='bi bi-trash'></i>")).append("削除");
		deleteBtn.attr("deleteId", item.id);
		let btnTd = $("<td class='text-center' style='width:120px;vertical-align:bottom;'></td>").append(editBtn).append(" ").append(deleteBtn);
		$("<tr></tr>").append(idTd).append(nameTd).append(btnTd).appendTo("#tableBody");
	});
}
function buildPageInfos(result) {
	let pageInfos = $("#pageInfos");
	pageInfos.empty();
	pageNum = result.data.pageNum;
	totalPages = result.data.totalPages;
	totalRecords = result.data.totalRecords;
	pageInfos.append(totalPages + "ページ中の" + pageNum + "ページ、" + totalRecords + "件のレコードが見つかりました。");
}
function buildPageNavi(result) {
	$("#pageNavi").empty();
	let ul = $("<ul></ul>").addClass("pagination");
	let firstPageLi = $("<li class='page-item'></li>").append(
		$("<a class='page-link'></a>").append("最初のページへ").attr("href", "#"));
	let previousPageLi = $("<li class='page-item'></li>").append(
		$("<a class='page-link'></a>").append("&laquo;").attr("href", "#"));
	if (!result.data.hasPreviousPage) {
		firstPageLi.addClass("disabled");
		previousPageLi.addClass("disabled");
	} else {
		firstPageLi.click(function() {
			toSelectedPg(1, searchName);
		});
		previousPageLi.click(function() {
			toSelectedPg(pageNum - 1, searchName);
		});
	}
	let nextPageLi = $("<li class='page-item'></li>").append(
		$("<a class='page-link'></a>").append("&raquo;").attr("href", "#"));
	let lastPageLi = $("<li class='page-item'></li>").append(
		$("<a class='page-link'></a>").append("最後のページへ").attr("href", "#"));
	if (!result.data.hasNextPage) {
		nextPageLi.addClass("disabled");
		lastPageLi.addClass("disabled");
	} else {
		lastPageLi.addClass("success");
		nextPageLi.click(function() {
			toSelectedPg(pageNum + 1, searchName);
		});
		lastPageLi.click(function() {
			toSelectedPg(totalPages, searchName);
		});
	}
	ul.append(firstPageLi).append(previousPageLi);
	$.each(result.data.navigatePageNums, (index, item) => {
		let numsLi = $("<li class='page-item'></li>").append(
			$("<a class='page-link'></a>").append(item).attr("href", "#"));
		if (pageNum === item) {
			numsLi.attr("href", "#").addClass("active");
		}
		numsLi.click(function() {
			toSelectedPg(item, searchName);
		});
		ul.append(numsLi);
	});
	ul.append(nextPageLi).append(lastPageLi);
	$("<nav></nav>").append(ul).appendTo("#pageNavi");
}
$("#addRoleBtn").on('click', function() {
	formReset("#roleAddModal form");
	let addModal = new bootstrap.Modal($("#roleAddModal"), {
		backdrop: 'static'
	});
	addModal.show();
});
$("#nameInput").on('change', function() {
	let nameVal = this.value;
	$.ajax({
		url: '/pgcrowd/role/checkname',
		data: 'name=' + nameVal,
		type: 'GET',
		success: function(result) {
			if (result.status === 'SUCCESS') {
				showValidationMsg("#nameInput", "success", "");
				$("#roleInfoSaveBtn").attr("ajax-va", "success");
			} else {
				showValidationMsg("#nameInput", "error", result.message);
				$("#roleInfoSaveBtn").attr("ajax-va", "error");
			}
		}
	});
});
$("#roleInfoSaveBtn").on('click', function() {
	let inputName = $("#nameInput").val().trim();
	if ($(this).attr("ajax-va") === "error") {
		return false;
	} else {
		$.ajax({
			url: '/pgcrowd/role/infosave',
			type: 'POST',
			dataType: 'json',
			data: JSON.stringify({
				'name': inputName
			}),
			contentType: 'application/json;charset=UTF-8',
			success: function() {
				$("#roleAddModal").modal('hide');
				toSelectedPg(pageNum, keyword);
			}
		});
	}
});
$(document).on('click', '.edit_btn', function() {
	formReset("#roleEditModal form");
	let editId = $(this).attr("editId");
	$("#roleInfoChangeBtn").attr("editId", editId);
	$("#idEdit").text(editId);
	let nameVal = $(this).parent().parent().find("td:eq(0)").text();
	$("#nameEdit").val(nameVal);
	let editModal = new bootstrap.Modal($("#roleEditModal"), {
		backdrop: 'static'
	});
	editModal.show();
});
$("#nameEdit").on('change', function() {
	let editName = this.value;
	if (editName === "") {
		showValidationMsg("#nameEdit", "error", "役割名称を空になってはいけません。");
		$("#roleInfoChangeBtn").attr("ajax-va", "error");
	} else {
		showValidationMsg("#nameEdit", "success", "");
		$("#roleInfoChangeBtn").attr("ajax-va", "success");
	}
});
$("#roleInfoChangeBtn").on('click', function() {
	let editName = $("#nameEdit").val().trim();
	if ($(this).attr("ajax-va") === "error") {
		return false;
	} else {
		$.ajax({
			url: '/pgcrowd/role/infoupd',
			type: 'PUT',
			dataType: 'json',
			data: JSON.stringify({
				'id': $(this).attr("editId"),
				'name': editName
			}),
			contentType: 'application/json;charset=UTF-8',
			success: function(result) {
				if (result.status === 'SUCCESS') {
					$("#roleEditModal").modal('hide');
					toSelectedPg(pageNum, keyword);
				} else {
					showValidationMsg("#nameEdit", "error", result.message);
					$(this).attr("ajax-va", "error");
				}
			}
		});
	}
});
$(document).on('click', '.delete_btn', function() {
	let roleName = $(this).parents("tr").find("td:eq(0)").text().trim();
	let roleId = $(this).attr("deleteId");
	if (confirm("この" + roleName + "という役割情報を削除する、よろしいでしょうか。")) {
		$.ajax({
			url: '/pgcrowd/role/remove',
			data: 'roleId=' + roleId,
			type: 'DELETE',
			dataType: 'json',
			success: function() {
				toSelectedPg(pageNum, keyword);
			}
		});
	}
});
function formReset(element) {
	$(element)[0].reset();
	$(element).find(".form-control").removeClass("is-valid is-invalid");
	$(element).find(".form-text").removeClass("valid-feedback invalid-feedback");
	$(element).find(".form-text").text("");
}
function showValidationMsg(element, status, msg) {
	$(element).removeClass("is-valid is-invalid");
	$(element).next("span").removeClass("valid-feedback invalid-feedback");
	$(element).next("span").text("");
	if (status === "success") {
		$(element).addClass("is-valid");
		$(element).next("span").addClass("valid-feedback");
	} else if (status === "error") {
		$(element).addClass("is-invalid");
		$(element).next("span").addClass("invalid-feedback").text(msg);
	}
}