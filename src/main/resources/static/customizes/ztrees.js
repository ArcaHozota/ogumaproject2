let treeData;
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
						icon: "glyphicon glyphicon-folder-open",
						nodes: [
							{
								text: $(authlist).eq(1).title,
								icon: "glyphicon glyphicon-file"
							},
							{
								text: $(authlist).eq(2).title,
								icon: "glyphicon glyphicon-file"
							},
							{
								text: $(authlist).eq(3).title,
								icon: "glyphicon glyphicon-file"
							}
						]
					},
					{
						text: $(authlist).eq(4).title,
						icon: "glyphicon glyphicon-folder-open",
						nodes: [
							{
								text: $(authlist).eq(5).title,
								icon: "glyphicon glyphicon-file"
							},
							{
								text: $(authlist).eq(6).title,
								icon: "glyphicon glyphicon-file"
							},
							{
								text: $(authlist).eq(7).title,
								icon: "glyphicon glyphicon-file"
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
					borderColor: "green", // 设置树形视图的边框颜色
					onNodeSelected: function(event, data) { // 设置树形视图的节点选中事件
						alert("你选择了" + data.text + "节点"); // 弹出提示框，显示选中的节点的文本
					}
				});
			}
		}
	});
});