let pageNum = $("#pageNumber").val();
$(document).ready(function() {
	$("#adminKanri").removeClass('collapsed');
	$("ul", $("#adminKanri")).show('fast');
	let treeData = [
		{
			text: "社員管理",
			icon: "bi bi-person-circle",
			expanded: true,
			nodes: [
				{
					text: "社員情報追加",
					icon: "bi bi-person-fill-add"
				},
				{
					text: "社員情報一覧",
					icon: "bi bi-person-check-fill"
				}
			]
		},
		{
			text: "役割管理",
			icon: "bi bi-person-badge-fill",
			expanded: true,
			nodes: [
				{
					text: "役割情報一覧",
					icon: "bi bi-person-vcard-fill"
				}
			]
		}
	];
	$('#treeView').treeview({
		data: treeData, // 设置树形视图的数据源
		levels: 2, // 设置树形视图的默认展开层级
		color: "#000", // 设置树形视图的字体颜色
		backColor: "#fff", // 设置树形视图的背景颜色
		onhoverColor: "orange", // 设置树形视图的鼠标悬停颜色
		borderColor: "red", // 设置树形视图的边框颜色
		onNodeSelected: function(event, data) { // 设置树形视图的节点选中事件
			alert("你选择了" + data.text + "节点"); // 弹出提示框，显示选中的节点的文本
		}
	});
});
$("#treeView").on('nodeSelected', function(event, data) {
	let titleName = data.text;
	switch (titleName) {
		case "社員情報追加":
			window.location.replace("/pgcrowd/employee/to/addition");
			break;
		case "社員情報一覧":
			window.location.replace("/pgcrowd/employee/to/pages?pageNum=1");
			break;
		case "役割情報一覧":
			window.location.replace("/pgcrowd/role/to/pages?pageNum=1");
			break;
	}
});