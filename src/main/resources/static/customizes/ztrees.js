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
								text: $(authlist).eq(1).get('title'),
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
				// Example: initializing the bstreeview
				$('#tree').bstreeview({
					data: data,
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