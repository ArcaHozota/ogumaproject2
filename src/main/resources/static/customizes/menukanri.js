let pageNum = $("#pageNumber").val();
$(document).ready(function() {
	$("#adminKanri").removeClass('collapsed');
	$("ul", $("#adminKanri")).show('fast');
	let treeData = [
		{
			text: "社員管理",
			icon: "bi bi-person-circle",
			nodes: [
				{
					text: "社員情報追加",
					icon: "bi bi-person-fill-add"
				},
				{
					text: "社員情報一覧",
					icon: "bi bi-person-check"
				}
			]
		},
		{
			text: "役割管理",
			icon: "bi bi-person-badge-fill",
			nodes: [
				{
					text: "役割情報一覧",
					icon: "bi bi-person-vcard"
				}
			]
		}
	];
	$('#treeView').bstreeview({
		data: treeData,
		expandIcon: 'fa fa-angle-down fa-fw',
		collapseIcon: 'fa fa-angle-right fa-fw',
		indent: 1.25,
		parentsMarginLeft: '1.25rem',
		openNodeLinkOnNewTab: true
	});
});
