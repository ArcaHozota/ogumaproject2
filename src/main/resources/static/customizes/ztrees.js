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
				let p1Text;
				let p2Text;
				let c1Text;
				let c2Text;
				let c3Text;
				let c4Text;
				let c5Text;
				let c6Text;
				$.each(authlist, (index, item) => {
					switch (index) {
						case 0:
							p1Text = item;
							break;
						case 1:
							c1Text = item;
							break;
						case 2:
							c2Text = item;
							break;
						case 3:
							c3Text = item;
							break;
						case 4:
							p2Text = item;
							break;
						case 5:
							c4Text = item;
							break;
						case 6:
							c5Text = item;
							break;
						default:
							c6Text = item;
							break;
					}
				});
				treeData = [
					{
						text: p1Text,
						icon: "bi bi-1-circle-fill",
						nodes: [
							{
								text: c1Text,
								icon: "bi bi-2-circle-fill"
							},
							{
								text: c2Text,
								icon: "bi bi-3-circle-fill"
							},
							{
								text: c3Text,
								icon: "bi bi-4-circle-fill"
							}
						]
					},
					{
						text: p2Text,
						icon: "bi bi-5-circle-fill",
						nodes: [
							{
								text: c4Text,
								icon: "bi bi-6-circle-fill"
							},
							{
								text: c5Text,
								icon: "bi bi-7-circle-fill"
							},
							{
								text: c6Text,
								icon: "bi bi-8-circle-fill"
							}
						]
					}
				];
				// Example: initializing the bstreeview
				$('#tree').bstreeview({
					data: treeData,
					expandIcon: 'fa fa-angle-down fa-fw',
					collapseIcon: 'fa fa-angle-right fa-fw',
					indent: 1.25,
					parentsMarginLeft: '1.25rem',
					openNodeLinkOnNewTab: true
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