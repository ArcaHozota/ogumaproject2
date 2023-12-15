let treeData;
let pageNum = $("#pageNumber").val();
$(document).ready(function() {
	$("#adminKanri").removeClass('collapsed');
	$("ul", $("#adminKanri")).show('fast');
	let treeData = [
		{
			text: "社員管理",
			icon: "bi bi-1-circle-fill",
			nodes: [
				{
					text: "社員情報追加",
					icon: "bi bi-2-circle-fill"
				},
				{
					text: "社員情報一覧",
					icon: "bi bi-3-circle-fill"
				}
			]
		},
		{
			text: "役割管理",
			icon: "bi bi-5-circle-fill",
			nodes: [
				{
					text: "役割情報一覧",
					icon: "bi bi-6-circle-fill"
				}
			]
		}
	];
	$('#tree').bstreeview({
		data: treeData,
		expandIcon: 'fa fa-angle-down fa-fw',
		collapseIcon: 'fa fa-angle-right fa-fw',
		indent: 1.25,
		parentsMarginLeft: '1.25rem',
		openNodeLinkOnNewTab: true
	});
});
