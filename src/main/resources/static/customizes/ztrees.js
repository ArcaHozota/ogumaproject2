let treeData;
let pageNum = $("#pageNumber").val();
$(document).ready(function() {
	$("#adminKanri").removeClass('collapsed');
	$("ul", $("#adminKanri")).show('fast');
	$.ajax({
		url: '/pgcrowd/role/authlists',
		type: 'GET',
		dataType: 'json',
		success: function(result) {
			if (result.status === 'SUCCESS') {
				let authlist = result.data;
				treeData = [
					{
						text: $(authlist).eq(0).title,
						icon: "bi bi-1-circle-fill",
						nodes: [
							{
								text: $(authlist).eq(1).title,
								icon: "bi bi-2-circle-fill"
							},
							{
								text: $(authlist).eq(2).title,
								icon: "bi bi-3-circle-fill"
							},
							{
								text: $(authlist).eq(3).title,
								icon: "bi bi-4-circle-fill"
							}
						]
					},
					{
						text: $(authlist).eq(4).title,
						icon: "bi bi-5-circle-fill",
						nodes: [
							{
								text: $(authlist).eq(5).title,
								icon: "bi bi-6-circle-fill"
							},
							{
								text: $(authlist).eq(6).title,
								icon: "bi bi-7-circle-fill"
							},
							{
								text: $(authlist).eq(7).title,
								icon: "bi bi-8-circle-fill"
							}
						]
					}
				];
				$("#tree").treeview({
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
			}
		}
	});
});
$("#toRolePages").on('click', function(e) {
	e.preventDefault();
	let userId = $("#userinfoId").text();
	window.location.replace('/pgcrowd/role/to/pages?pageNum=' + pageNum + '&userId=' + userId);
});